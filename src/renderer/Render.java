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
		Intersectable  geometries= _scene.getGeometries();
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
				List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
				if(intersectionPoints==null)
					_imageWriter.writePixel(j, i, background);
				else
				{
					GeoPoint closestPoint = getClosestPoint(intersectionPoints);
					_imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
				}
			}
		}
		
	}
	
	 
	private Color calcColor(GeoPoint p)
	{
		Color color=_scene.getAmbientLight().getIntensity();
		color = color.add(p.geometry.getEmission());
		Vector v=  p.point.subtract(_scene.getCamera().getP0()).normalized();
		Vector n= p.geometry.getNormal(p.point);
		Material material= p.geometry.getMaterial();
		int nShininess= material.getNShininess();
		double kd=material.getKD();
		double ks=material.getKS();
		for(int i=0; i<_scene.getLights().size();i++)
		{
			LightSource lightSource=_scene.getLights().get(i);
			Vector l=lightSource.getL(p.point);
			if(Math.signum(n.dotProduct(l))==Math.signum(n.dotProduct(v)))
			{
				Color lightIntensity = lightSource.getIntensity(p.point);
				color = color.add(calcDiffusive(kd, l, n, lightIntensity),
						calcSpecular(ks, l, n, v, nShininess, lightIntensity));			 
			}		
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
	
	private GeoPoint getClosestPoint(List<GeoPoint> points)
	{
		GeoPoint minPoint=points.get(0);
		double minDistance=_scene.getCamera().getP0().distance(minPoint.point);
		for(int i=1;i<points.size();i++)
		{
			double distance=_scene.getCamera().getP0().distance(points.get(i).point);
			if(distance<minDistance)
			{
				minDistance=distance;
				minPoint=points.get(i);
			}
		}
		return minPoint;
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
