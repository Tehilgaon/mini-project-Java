package geometries;
import java.util.List;

import primitives.*;


/**
 * Triangle class 
 * represents two-dimensional Triangle in 3D Cartesian coordinate system
 * @author user
 *
 */
public class Triangle extends Polygon {
	
	
	/**
	 * Constructor #1
	 * @param p1 Point3D
	 * @param p2 Point3D
	 * @param p3 Point3D
	 */
	public Triangle(Point3D p1,Point3D p2,Point3D p3)
	{
		super(p1,p2,p3);
	}
	
	
	/**
	 * Constructor #2
	 * @param color emission
	 * @param p1 Point3D
	 * @param p2 Point3D
	 * @param p3 Point3D
	 */
	public Triangle(Color color,Point3D p1,Point3D p2,Point3D p3)
	{
		super(color,p1,p2,p3);	
	}
	
	
	/**
	 * Constructor #3
	 * @param color emission
	 * @param material
	 * @param p1 Point3D
	 * @param p2 Point3D
	 * @param p3 Point3D
	 */
	public Triangle(Color color,Material material,Point3D p1,Point3D p2,Point3D p3)
	{
		super(material,color,p1,p2,p3);	
	}
	
	
	@Override
	public List<GeoPoint> findIntersections(Ray ray, double max) {
		List<GeoPoint> list = super.findIntersections(ray, max);
		if(list == null)
			return null;
		
		for (GeoPoint gp : list) 
			gp.geometry = this;
		
		return list; 
	}
}
