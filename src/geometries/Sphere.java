package geometries;
import primitives.*;

public class Sphere extends RadialGeometry{
	Point3D _q;
	public Sphere(double radius, Point3D point) {
		super(radius);
		_q=new Point3D(point);
		// TODO Auto-generated constructor stub
	}

	public Vector getNormal(Point3D point)
	{
		 return point.subtract(_q);
	}

}
