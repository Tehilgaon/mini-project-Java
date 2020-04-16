package geometries;
import java.util.*;

import primitives.*;

public class Sphere extends RadialGeometry implements Intersectable{
	Point3D _q;
	public Sphere(double radius, Point3D point) {
		super(radius);
		_q=new Point3D(point);
 
	}

	public primitives.Vector getNormal(Point3D point)
	{
		 return point.subtract(_q);
	}

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		double d=0,tm=0;
		if(!ray.getP0().equals(_q)) //To avoid creating a Zero vector. If the ray's starting point is the center point, the 'd' remains 0
		{	
			primitives.Vector u= _q.subtract(ray.getP0()); //the vector (u) between the center and the ray's starting point
			tm=ray.getDirection().dotProduct(u); //The shadow of the vector (u) on the ray's vector
			d= Math.sqrt(u.lengthSquared()-(tm*tm)); //The triangle's height  
		}
		if (d>_radius)
			return null;
		double th=Math.sqrt(_radius*_radius-d*d);
		List<Point3D> _list= new ArrayList<Point3D>();
		double t1=tm+th;
		if(t1>0)
			_list.add(ray.getPoint(t1)); //adding the first point to the list
		double t2=tm-th;
		if(t2>0)
			_list.add(ray.getPoint(t2));//adding the second point to the list
		if(_list.size()==0)
			return null;
		return _list;
	}

}
