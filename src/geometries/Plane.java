package geometries;

import primitives.*;

public class Plane implements Geometry
{
	Point3D _p;
	Vector _normal;
	public Plane(Point3D point,Vector normal)
	{
		_p=new Point3D(point);
		_normal=new Vector(normal);
	}
	public Plane(Point3D p1,Point3D p2,Point3D p3)
	{
		Vector vec=p1.subtract(p2);
		_normal=new Vector(vec.crossProduct(p2.subtract(p3)));
		_p=new Point3D(p1);
	}
	public Vector getNormal()
	{
		return _normal;
	}
	public Point3D getPoint()
	{
		return _p;
	}
	public Vector getNormal(Point3D point) {
		return _normal;
	}

}
