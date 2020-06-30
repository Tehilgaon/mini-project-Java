package renderer;

import scene.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import elements.*;
 


/**
 * The main class in the process of image creation.
 * Contains a scene instance and imageWriter instance. She runs all other classes to produce rays, 
 * calculate intersection points, find their color 
 * and finally she calls to imageWriter to write it into a image
 * @author Odel & Tehila
 */
public class Render {

	
	/**
	 * new ImageWrite object
	 */
	ImageWriter _imageWriter;
	
	
	/**
	 * new Scene object
	 */
	Scene _scene;
	
	
	/**
	 * Recursion depth 
	 */
	private static final int MAX_CALC_COLOR_LEVEL =10;
	
	
	/**
	 * Indicates the lowest light intensity. Once we cross it we will stop the calculation
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;
	
	
	/**
	 * The number of reflected/refracted rays we send in addition to the main ray
	 */
	private static final int numOfRays= 8;
	
	
	/**
	 * The scattering space of the rays around the main refracted ray
	 */
	private static final double Twideness = 0;
	
	
	/**
	 * The scattering space of the rays around the main reflected ray
	 */
	private static final double Rwideness = 50;
	
	
	
	private static final int divLevel = 3; 

	
	// THREAD variables //
	private int _threads = 1;
	private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
	private boolean _print = false; // printing progress percentage
	
	
	/**
	 * Constructor  
	 * @param imageWriter
	 * @param scene
	 */
	public Render(ImageWriter imageWriter, Scene scene) 
	{
		_imageWriter=imageWriter;
		_scene=scene;
	}
	
	
	/**
	 * The main function in image production.
	 * Operates the camera that produces rays, the geometry that finds the closest point with this ray
	 * and the calcColor function that calculates the point's color
	 */
	public void renderImage()
	{
		Camera camera=_scene.getCamera();
		 
		int nX = _imageWriter.getNx();
		int nY = _imageWriter.getNy();
		double distance=_scene.getDistance(); // distance between camera and view plane
		double width=_imageWriter.getWidth(); // in order to calculate the size of each pixel
		double height=_imageWriter.getHeight();
		final Pixel thePixel = new Pixel(nY, nX); // Main pixel management object , Threads
		Thread[] threads = new Thread[_threads];
		for (int i = _threads - 1; i >= 0; --i) 
		{ 
			// create all threads
			threads[i] = new Thread(() -> {
				Pixel pixel = new Pixel(); // Auxiliary thread’s pixel object
				while (thePixel.nextPixel(pixel)) {
					List<Point3D> corners = camera.constructCornersThroughPixel(nX, nY, pixel.col, pixel.row,
					distance, width, height);
					
					Color c= calcRec(corners.get(0),corners.get(1),corners.get(2),corners.get(3),camera.getP0(),divLevel);
					
					_imageWriter.writePixel(pixel.col, pixel.row,c.getColor());
							 
			}});
		}
		for (Thread thread : threads) thread.start(); // Start all the threads
		// Wait for all threads to finish
		for (Thread thread : threads) 
			try { thread.join(); } catch (Exception e) {}
		if (_print) System.out.printf("\r100%%\n"); // Print 100%
		
	}
	
	
	/**
	 * Calculates the color at a point. It's a shell function
	 * @param p GeoPoint
	 * @param inRay - intersection ray
	 * @return Color at the point
	 */
	private Color calcColor(GeoPoint p, Ray inRay )
	{
		return calcColor(p,inRay,MAX_CALC_COLOR_LEVEL,1.0, Twideness,Rwideness ,numOfRays)
				.add(_scene.getAmbientLight().getIntensity()); // calculate the calcolor of the recursia and add the ambient light
	}
	
	
	/**
	 * Recursion function that calculates the color at a point
	 * @param p GeoPoint (for the geometry's material)
	 * @param ray intersection ray
	 * @param level of recursion
	 * @param k
	 * @return color at the point
	 */
	private Color calcColor(GeoPoint p,Ray ray, int level, double k, double Twideness,double Rwideness, int numRays)
	{
		//Stop condition
		if (level == 1 || k<MIN_CALC_COLOR_K) 
			return Color.BLACK; 

		Color color = p.geometry.getEmission();
		Vector v=  p.point.subtract(_scene.getCamera().getP0()).normalized();
		Vector n= p.geometry.getNormal(p.point);
		double nv = Util.alignZero(n.dotProduct(v)); 
		
		
		//ray parallel to geometry surface and orthogonal to normal
        if (nv == 0)
        	return color; 
        
		Material material= p.geometry.getMaterial();
		int nShininess= material.getNShininess();
		double kd=material.getKD();
		double ks=material.getKS();
		
		double kr = p.geometry.getMaterial().getKR();
		double kkr= k*kr; 
		
		double kt = p.geometry.getMaterial().getKT();
		double kkt = k * kt;
		
		
		//Calculates specular and diffuse components for all light sources 
		for(int i=0; i<_scene.getLights().size();i++)
		{
			LightSource lightSource=_scene.getLights().get(i);
			Vector l=lightSource.getL(p.point);
			if(Util.alignZero(n.dotProduct(l))* Util.alignZero(n.dotProduct(v))>0) { //if they have the same sign it's bigger than 0
				double ktr= transparency(lightSource,l,n, p); // shadow-occluded 
				if(ktr*k>MIN_CALC_COLOR_K)
				{
					Color lightIntensity = lightSource.getIntensity(p.point).scale(ktr);
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),
						calcSpecular(ks, l, n, v, nShininess, lightIntensity));			 
				}
			}
		}
		
		
		//Recursive calling  with a reflection ray -mirror
		Color avg= Color.BLACK;
		if (kkr > MIN_CALC_COLOR_K)
		{
			Ray MainRay =constructReflectedRay(n,p.point,ray) ;
			List<Ray> reflectedRays = constructReflectedRefractedBeamRay(MainRay, Rwideness, numRays);
			if( reflectedRays != null) 
			{
				for(Ray reflectedRay: reflectedRays) {
					GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
					if (reflectedPoint != null)
						avg = avg.add(calcColor(reflectedPoint, reflectedRay,
								level-1, kkr,Rwideness*2,Twideness, numRays).scale(kr));
				}
				avg = avg.reduce(reflectedRays.size());
			}
			color= color.add(avg);
		}
		
		
		///Recursive calling  with a transparency ray
		avg= Color.BLACK;
		if (kkt > MIN_CALC_COLOR_K) {
			Ray MainRay = constructRefractedRay(n,p.point, ray) ;
			List<Ray> refractedRays=constructReflectedRefractedBeamRay(MainRay, Twideness, numRays) ;
			if( refractedRays != null)
			{
				for(Ray refractedRay : refractedRays ) {
					GeoPoint refractedPoint = findClosestIntersection(refractedRay);
					if (refractedPoint != null)
						avg = avg.add(calcColor(refractedPoint, refractedRay,
								level-1, kkt, Twideness*2,Rwideness, numRays).scale(kt));
				}
				avg = avg.reduce(refractedRays.size());
			}
			color= color.add(avg);
		}
		
		return color;
	}
	
	
	/**
	 * Calculates the diffusion component of light 
	 * @param kd diffusion factor
	 * @param l normalized direction vector from the light source to the point
	 * @param n normal
	 * @param lightIntensity
	 * @return the diffusion component on the point
	 */
	public Color calcDiffusive(double kd,Vector l, Vector n, Color lightIntensity)
	{
		return lightIntensity.scale(Math.abs(l.dotProduct(n))*kd);
	}
	
	
	/**
	 * Calculates specular component of light
	 * @param ks specular factor
	 * @param l normalized direction vector from the light source to the point
	 * @param n normal
	 * @param v direction vector from point of view to point
	 * @param nShininess shininess level
	 * @param lightIntensity
	 * @return the specular component on the point
	 */
	public Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity)
	{
		Vector r= l.subtract(n.scale(2*l.dotProduct(n)));
		double factor= Math.max(0, -v.dotProduct(r));
		return lightIntensity.scale(ks*Math.pow(factor, nShininess));
	}
	
	
	/**
	 * Returns how much the point is shadowed   
	 * @param lightSource
	 * @param l normalized direction vector from the light source to the point
	 * @param n the normal
	 * @param gp
	 * @return double (between 0 and 1)
	 */
	private double transparency(LightSource lightSource, Vector l, Vector n, GeoPoint gp)
	{
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay, lightSource.getDistance(gp.point));
		if (intersections == null) return 1.0;
		double ktr=1.0;
		for (GeoPoint g : intersections) { 
			ktr*= g.geometry.getMaterial().getKT();
			if (ktr<MIN_CALC_COLOR_K)
				return 0.0;
		}
		return ktr;
	}
	
	
	/**
	 * Returns the closest intersection point with the ray 
	 * @param points
	 * @param ray
	 * @return GeoPoint
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> points, Ray ray)
	{
		GeoPoint minPoint=null;
		double minDistance=Double.MAX_VALUE; // infinity
		for(GeoPoint point : points)
		{
			double distance=ray.getP0().distance(point.point);
			if(distance<minDistance)
			{
				minDistance=distance;
				minPoint=point;
			}
		}
		return minPoint;
	}
	
	
	/**
	 * Returns the reflected ray 
	 * @param n
	 * @param point
	 * @param ray
	 * @return
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Ray ray)
	{
		//r=v-2*(v*n)*n
        Vector v = ray.getDirection();
        double vn = v.dotProduct(n);
        if (vn == 0) {
            return null;
        }
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(point, r, n); // send also the normal to calculate with delta
	}
	
	
	/**
	 * Returns the refracted ray
	 * @param n
	 * @param point
	 * @param ray
	 * @return
	 */
	private Ray constructRefractedRay(Vector n,Point3D point, Ray ray)
	{
		return new Ray(point,ray.getDirection(),n);
	}
	
	
	/**
	 * Calls the findIntersections and getClosestPoint functions and returns the GeoPoint
	 * @param ray
	 * @return GeoPoint
	 */
	private GeoPoint findClosestIntersection(Ray ray)
	{
		List<GeoPoint> getClosestPoint = _scene.getGeometries().findIntersections(ray);
		if (getClosestPoint==null)
			return null;
		return getClosestPoint(getClosestPoint, ray);
	}
	
	
	/**
	 * Calling to imageWriter's writeToImage() 
	 */
	public void writeToImage()
	{
		_imageWriter.writeToImage();
	}

	
	/**
	 * Returns list of reflected rays
	 * @param mainRay - the ray that is just reflected at a 90 degree angle
	 * @param wideness - The scattering space of the rays around the main ray
	 * @param numRays - the number of rays we send in addition to the main ray
	 * @return list of rays
	 */
	public List<Ray> constructReflectedRefractedBeamRay(Ray mainRay,double wideness, int numRays)
	{
		
		List<Ray> RaysList=new ArrayList<Ray>();
		
		Point3D p0=mainRay.getP0();
		GeoPoint p= findClosestIntersection(mainRay);
		if(p == null || wideness==0)
			return null;
		
		RaysList.add(mainRay);
		
		double x=mainRay.getDirection().get().getX();
		double y=mainRay.getDirection().get().getY();
		Vector normal1 = new Vector(-y,x,0);
		Vector normal2=normal1.crossProduct(mainRay.getDirection());
		
		Random rand=new Random();
		for(int i=0;i<numRays;i++)
		{
			double randX=  (2*wideness)*rand.nextDouble() - (wideness);
		 	double randY=  (2*wideness)*rand.nextDouble() - (wideness);
		 	Vector delVector=(normal1.scale(randX)).add(normal2.scale(randY));
		 	Point3D delPoint=(p.point).add(delVector);
		 	
		 	Vector v= delPoint.subtract(p0).normalized();
		 	RaysList.add(new Ray(p0,v));
		}
		
		return RaysList;				
	}
	 
	
	
	//-------ADAPTIVE SUPERSAMPLING-------//
	
	public Color calcRec(Point3D p0,Point3D p1,Point3D p2,Point3D p3,Point3D cameraP0, int divLevel)	{
		 
		List<Point3D> edgeList= List.of(p0,p1,p2,p3);
		List <Color> colors = new ArrayList<Color>();
		
		for(int i=0;i<4;i++) {
			Ray ray= new Ray(cameraP0, edgeList.get(i).subtract(cameraP0).normalized());
			GeoPoint gp= findClosestIntersection(ray);
			if(gp != null)
				colors.add(calcColor(gp,ray));
			else
				colors.add(_scene.getBackground());
		}
		
		if((colors.get(0).equals(colors.get(1))&& colors.get(0).equals(colors.get(2))&&
				colors.get(0).equals(colors.get(3))) || divLevel == 0)
			return colors.get(0); 
		
	 
		Point3D midUp= findMiddlePoint(edgeList.get(0),edgeList.get(1));
		Point3D midDown= findMiddlePoint(edgeList.get(2),edgeList.get(3));
		Point3D midRight= findMiddlePoint(edgeList.get(1),edgeList.get(3));
		Point3D midLeft= findMiddlePoint(edgeList.get(0),edgeList.get(2));
		Point3D center= findMiddlePoint(midRight,midLeft);
		
		
		//Recursive call 
		return calcRec(edgeList.get(0),midUp,midLeft,center,cameraP0,divLevel-1).scale(0.25)
				.add(calcRec(midUp,edgeList.get(1),center,midRight,cameraP0, divLevel-1).scale(0.25))
				.add(calcRec(midLeft,center,edgeList.get(2),midDown,cameraP0, divLevel-1).scale(0.25))
				.add(calcRec(center,midRight,midDown,edgeList.get(3),cameraP0, divLevel-1).scale(0.25));
	 
	}
	
	
	public Point3D findMiddlePoint(Point3D p1, Point3D p2)
	{
		double x=(p1.getX()+p2.getX())/2;
		double y=(p1.getY()+p2.getY())/2;
		double z=(p1.getZ()+p2.getZ())/2;
		return new Point3D(x, y, z);
	}
	
	
	
	//------------------------------------THREADS----------------------------------------------//

	/**
	 * The function prints a grid on the image
	 * @param interval the distance between the grid's lines
	 * @param color of the grid
	 */
	public void printGrid(int interval, java.awt.Color color)
	{
		double nx= _imageWriter.getNx();
		double ny= _imageWriter.getNy();
		for(int i=0;i<ny; i++)
			for(int j=0;j<nx;j++)
				if(i % interval == 0 || j % interval == 0)
					_imageWriter.writePixel(j, i, color);
		
	}
	
	
	/**
	* Set multithreading <br>
	* - if the parameter is 0 - number of coress less SPARE (2) is taken
	* @param threads number of threads
	* @return the Render object itself
	*/
	public Render setMultithreading(int threads) {
		if (threads < 0) 
			throw new IllegalArgumentException("Multithreading must be 0 or higher");
		if (threads != 0) _threads = threads;
		else 
		{
			int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			_threads = cores <= 2 ? 1 : cores;
		}
		return this;
	}
	
	
	/**
	* Set debug printing on
	* @return the Render object itself
	*/
	public Render setDebugPrint() { _print = true; return this; }

	
	/**
	* Pixel is an internal helper class whose objects are associated with a Render object that
	* they are generated in scope of. It is used for multithreading in the Renderer and for follow up
	* its progress.<br/>
	* There is a main follow up object and several secondary objects - one in each thread.
	*/
	private class Pixel {
		private long _maxRows = 0; // Ny
		private long _maxCols = 0; // Nx
		private long _pixels = 0; // Total number of pixels: Nx*Ny
		public volatile int row = 0; // Last processed row
		public volatile int col = -1; // Last processed column
		private long _counter = 0; // Total number of pixels processed
		private int _percents = 0; // Percent of pixels processed
		private long _nextCounter = 0; // Next amount of processed pixels for percent progress
		
		
		/**
		* The constructor for initializing the main follow up Pixel object
		* @param maxRows the amount of pixel rows
		* @param maxCols the amount of pixel columns
		*/
		public Pixel(int maxRows, int maxCols) {
			_maxRows = maxRows;_maxCols = maxCols; _pixels = maxRows * maxCols;
			_nextCounter = _pixels / 100;
			if (Render.this._print) System.out.printf("\r %02d%%", _percents);
		}
		
		
		/**
		 * Default constructor for secondary Pixel objects
		 */
		public Pixel() {}
		
		
		/**
		* Public function for getting next pixel number into secondary Pixel object.
		* The function prints also progress percentage in the console window.
		* @param target target secondary Pixel object to copy the row/column of the next pixel
		* @return true if the work still in progress, -1 if it's done
		*/
		public boolean nextPixel(Pixel target) {
			int percents = nextP(target);
			if (_print && percents > 0) System.out.printf("\r %02d%%", percents);
			if (percents >= 0) return true;
			if (_print) System.out.printf("\r %02d%%", 100);
			return false;
		}
		
		
		/**
		* Internal function for thread-safe manipulating of main follow up Pixel object - this function is
		* critical section for all the threads, and main Pixel object data is the shared data of this critical
		* section.<br/>
		* The function provides next pixel number each call.
		* @param target target secondary Pixel object to copy the row/column of the next pixel
		* @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
		* finished, any other value - the progress percentage (only when it changes)
		*/
		private synchronized int nextP(Pixel target) {
			++col; ++_counter;
			
			if (col < _maxCols) {
				target.row = this.row; target.col = this.col;
				if (_print && _counter == _nextCounter) {
					++_percents;_nextCounter = _pixels * (_percents + 1) / 100; return _percents;
				}
				return 0;
			}
			
			++row;
			
			if (row < _maxRows) {
				col = 0;
				if (_print && _counter == _nextCounter) {
					++_percents; _nextCounter = _pixels * (_percents + 1) / 100; return _percents;
				}
				return 0;
			}
			return -1;
			}
		}
	
 
}
