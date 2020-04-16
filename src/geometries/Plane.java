package geometries;

import java.util.List;
import java.util.ArrayList;

import primitives.*;

public class Plane implements Geometry, Intersectable
{
	Point3D _p;
	Vector _normal;
	public Plane(Point3D point,Vector normal)
	{
		_p=new Point3D(point);
		_normal=new Vector(normal.normalized());
	}
	public Plane(Point3D p1,Point3D p2,Point3D p3)
	{
		Vector vec=p1.subtract(p2);
		_normal=new Vector(vec.crossProduct(p2.subtract(p3))).normalized();
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
	
	@Override
	public List<Point3D> findIntersections(Ray ray) {
		
		double Nx=_normal.get().getX();
		double Ny=_normal.get().getY();
		double Nz=_normal.get().getZ();
		double _x=0, _y=0,_z=0;
		if(Math.abs(Nx)<=Math.abs(Ny)&&Math.abs(Nx)<=Math.abs(Nz)) //Finding the component of n that has the smallest absolute value
			_x=1;
		else if(Math.abs(Ny)<=Math.abs(Nx)&&Math.abs(Ny)<=Math.abs(Nz))
			_y=1;
		else
			_z=1;
		Vector v=new Vector(_x,_y,_z);//taking a vector (v) with all components zero except that one on the place of the smallest component 
		Vector PQ=_normal.crossProduct(v);//Compute PQ=v×n. PQ is orthogonal to n
		Point3D _q=new Point3D(_p.getX()-PQ.get().getX(),_p.getY()-PQ.get().getY(),_p.getZ()-PQ.get().getZ());//getting the point _q from the PQ vector	
		if(ray.getP0().equals(_q)) //When 𝑄0 = 𝑃0, there is no intersection point
			return null;
		//Calculating t=(n*(p0-q0))/n*v0
		double numerator=_normal.dotProduct(_q.subtract(ray.getP0()));
		double denominator=_normal.dotProduct(ray.getDirection());
		if(Util.isZero(numerator)||Util.isZero(denominator)) //When the ray is parallel to the plane and 't' is infinity OR when P0 is included in the plane  
			return null;
		double t=Util.alignZero(numerator/denominator);
		if (t<0)
			return null;
		List<Point3D> list=new ArrayList<Point3D>();
		list.add(ray.getPoint(t));
		return list;
	}

}
