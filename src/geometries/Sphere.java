package geometries;
import java.util.*;

import primitives.*;

public class Sphere extends RadialGeometry implements Intersectable{
	
	Point3D _q;
	
	public Sphere(double radius, Point3D point) 
	{
		super(radius);
		_q=new Point3D(point);
	}
	
	public Sphere(Color color,double radius, Point3D point)
	{
		this(radius, point);
		_emission=color;
	}
	
	public Sphere(Color color,Material material,double radius, Point3D point)
	{
		this(color, radius, point);
		_material= material;
	}

	public primitives.Vector getNormal(Point3D point)
	{
		 return point.subtract(_q).normalized();
	}

	@Override
	public List<GeoPoint> findIntersections(Ray ray)
	{
		double d=0,tm=0;
		if(!ray.getP0().equals(_q)) //To avoid creating a Zero vector. If the ray's starting point is the center point, the 'd' remains 0
		{	
			primitives.Vector u= _q.subtract(ray.getP0()); //the vector (u) between the center and the ray's starting point
			tm=ray.getDirection().dotProduct(u); //The shadow of the vector (u) on the ray's vector
			d= Math.sqrt(u.lengthSquared()-(tm*tm)); //The triangle's height  
		}
		if (d>=_radius)
			return null;
		double th=Math.sqrt(_radius*_radius-d*d);
		List<GeoPoint> _list= new ArrayList<GeoPoint>();
		double t1=tm+th;
		if(t1>0)
			_list.add(new GeoPoint(ray.getPoint(t1),this)); //adding the first point to the list
		double t2=tm-th;
		if(t2>0)
			_list.add(new GeoPoint(ray.getPoint(t2),this));//adding the second point to the list
		if(_list.size()==0)
			return null;
		return _list;
	}

}
