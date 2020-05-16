package geometries;
import java.util.List;

import primitives.*;
/**
 * Triangle class represents two-dimensional Triangle in 3D Cartesian coordinate system
 * @author user
 *
 */
public class Triangle extends Polygon {
	
	public Triangle(Point3D p1,Point3D p2,Point3D p3)
	{
		super(p1,p2,p3);
	}
	
	public Triangle(Color color,Point3D p1,Point3D p2,Point3D p3)
	{
		super(color,p1,p2,p3);	
	}
	
	public Triangle(Color color,Material material,Point3D p1,Point3D p2,Point3D p3)
	{
		super(material,color,p1,p2,p3);	
	}
	
	@Override
	public Vector getNormal(Point3D point) {
		return super.getNormal(point);
	}
	
	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		List<GeoPoint> list = super.findIntersections(ray);
		if(list!=null)
			list.get(0).geometry=this;
		return list; 
	}
}
