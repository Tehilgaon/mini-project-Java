package renderer;

import scene.*;

import java.util.List;

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
				List<Point3D> intersectionPoints = geometries.findIntersections(ray);
				if(intersectionPoints==null)
					_imageWriter.writePixel(j, i, background);
				else
				{
					Point3D closestPoint = getClosestPoint(intersectionPoints);
					_imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
				}
			}
		}
		
	}
	
	 
	private Color calcColor(Point3D p)
	{
		return _scene.getAmbientLight().GetIntensity();
	}
	
	
	private Point3D getClosestPoint(List<Point3D> points)
	{
		Point3D minPoint=points.get(0);
		double minDistance=_scene.getCamera().getP0().distance(minPoint);
		for(int i=1;i<points.size();i++)
		{
			double distance=_scene.getCamera().getP0().distance(points.get(i));
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
