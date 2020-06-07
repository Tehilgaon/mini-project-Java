package renderer;

import scene.*;
import java.util.List;
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
		java.awt.Color background = _scene.getBackground().getColor();
		int nX = _imageWriter.getNx();
		int nY = _imageWriter.getNy();
		double distance=_scene.getDistance();
		double width=_imageWriter.getWidth();
		double height=_imageWriter.getHeight();
		
		for(int i=0;i<nY;i++)
		{
			for(int j=0;j<nX;j++)
			{
				Ray ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
				GeoPoint closestPoint =  findClosestIntersection(ray);
				_imageWriter.writePixel(j, i, closestPoint == null ? background
						: calcColor(closestPoint,ray).getColor());
			}
		}
		
	}
	
	
	/**
	 * Calculates the color at a point. It's a shell function
	 * @param p GeoPoint
	 * @param inRay - intersection ray
	 * @return Color at the point
	 */
	private Color calcColor(GeoPoint p, Ray inRay)
	{
		return calcColor(p,inRay,MAX_CALC_COLOR_LEVEL,1.0)
				.add(_scene.getAmbientLight().getIntensity());
	}
	
	
	/**
	 * Recursion function that calculates the color at a point
	 * @param p GeoPoint
	 * @param ray intersection ray
	 * @param level of recursion
	 * @param k
	 * @return color at the point
	 */
	private Color calcColor(GeoPoint p,Ray ray, int level, double k)
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
			if(Util.alignZero(n.dotProduct(l))* Util.alignZero(n.dotProduct(v))>0) { //if they have the same sign
				double ktr= transparency(lightSource,l,n, p);
				if(ktr*k>MIN_CALC_COLOR_K)
				{
					Color lightIntensity = lightSource.getIntensity(p.point).scale(ktr);
					color = color.add(calcDiffusive(kd, l, n, lightIntensity),
						calcSpecular(ks, l, n, v, nShininess, lightIntensity));			 
				}
			}
		}
		
		//Recursive calling  with a reflection ray
		if (kkr > MIN_CALC_COLOR_K)
		{
			Ray reflectedRay = constructReflectedRay(n, p.point, ray);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null)
				color = color.add(calcColor(reflectedPoint, reflectedRay,
						level-1, kkr).scale(kr));
		}
		
		///Recursive calling  with a transparency ray
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n,p.point, ray) ;
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null)
				color = color.add(calcColor(refractedPoint, refractedRay,
						level-1, kkt).scale(kt));
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
	
	
	/*private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource)
	{
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay, lightSource.getDistance(gp.point));
		if (intersections == null) return true;
		for (GeoPoint g : intersections) 
			if (g.geometry.getMaterial().getKT() == 0)
				return false;
		return true;
	}*/
	
	
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
		double minDistance=Double.MAX_VALUE;
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
        return new Ray(point, r, n);
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

}
