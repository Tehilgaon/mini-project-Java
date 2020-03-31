package geometries;
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
	
	@Override
	public Vector getNormal(Point3D point) {
		return super.getNormal(point);
	}

}
