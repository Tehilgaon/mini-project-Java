package renderer;

import scene.*;
import java.util.List;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import elements.*;
import geometries.*;

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
	
	
	private static final int MAX_CALC_COLOR_LEVEL =10;
	
	private static final double MIN_CALC_COLOR_K = 0.001;

	
	/**
	 * constructor initializes the Scene object and the imageWriter object
	 * @param imageWriter
	 * @param scene
	 */
	public Render(ImageWriter imageWriter, Scene scene) 
	{
		_imageWriter=imageWriter;
		_scene=scene;
	}
	
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
	
	private Color calcColor(GeoPoint p, Ray inRay)
	{
		return calcColor(p,inRay,MAX_CALC_COLOR_LEVEL,1.0)
				.add(_scene.getAmbientLight().getIntensity());
	}
	
	
	private Color calcColor(GeoPoint p,Ray ray, int level, double k)
	{
		if (level == 1||k<MIN_CALC_COLOR_K) 
			return Color.BLACK; 

		Color color = p.geometry.getEmission();
		Vector v=  p.point.subtract(_scene.getCamera().getP0()).normalized();
		Vector n= p.geometry.getNormal(p.point);
		double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0) { //?
            //ray parallel to geometry surface and orthogonal to normal
            return color;
        }
		Material material= p.geometry.getMaterial();
		int nShininess= material.getNShininess();
		double kd=material.getKD();
		double ks=material.getKS();
		
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
		double kr = p.geometry.getMaterial().getKR();
		double kkr= k*kr;
		if (kkr > MIN_CALC_COLOR_K)
		{
			Ray reflectedRay = constructReflectedRay(n, p.point, ray);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null)
				color = color.add(calcColor(reflectedPoint, reflectedRay,
						level-1, kkr).scale(kr));
		}
		double kt = p.geometry.getMaterial().getKT();
		double kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(n,p.point, ray) ;
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null)
				color = color.add(calcColor(refractedPoint, refractedRay,
						level-1, kkt).scale(kt));
		}
		
		return color;
	}
	
	public Color calcDiffusive(double kd,Vector l, Vector n, Color lightIntensity)
	{
		return lightIntensity.scale(Math.abs(l.dotProduct(n))*kd);
	}
	
	public Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity)
	{
		Vector r= l.subtract(n.scale(2*l.dotProduct(n)));
		double factor= Math.max(0, -v.dotProduct(r));
		return lightIntensity.scale(ks*Math.pow(factor, nShininess));
	}
	
	private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource)
	{
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay, lightSource.getDistance(gp.point));
		if (intersections == null) return true;
		for (GeoPoint g : intersections) 
			if (g.geometry.getMaterial().getKT() == 0)
				return false;
		return true;
	}
	
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
	
	private Ray constructRefractedRay(Vector n,Point3D point, Ray ray)
	{
		return new Ray(point,ray.getDirection(),n);
	}
	
	private GeoPoint findClosestIntersection(Ray ray)
	{
		List<GeoPoint> getClosestPoint = _scene.getGeometries().findIntersections(ray);
		if (getClosestPoint==null)
			return null;
		return getClosestPoint(getClosestPoint, ray);
	}
	
	/**
	 * caling to imageWriter's writeToImage() 
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
				if(i%interval==0 || j%interval==0)
					_imageWriter.writePixel(j, i, color);
		
	}

}
