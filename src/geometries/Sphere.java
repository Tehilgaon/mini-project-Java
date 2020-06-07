package geometries;
import java.util.ArrayList;
import java.util.List;
import primitives.*;


/**
 * Sphere Class
 * @author Odel & Tehila
 */
public class Sphere extends RadialGeometry implements Intersectable{
	
	
	/**
     * The center point of the sphere
     */
	Point3D _q;
	
	
	/**
	 * Constructor #1
	 * @param radius
	 * @param point
	 */
	public Sphere(double radius, Point3D point) 
	{
		super(radius);
		_q=new Point3D(point);
	}
	
	
	/**
	 * Constructor #2
	 * @param color
	 * @param radius
	 * @param point
	 */
	public Sphere(Color color,double radius, Point3D point)
	{
		this(radius, point);
		_emission=color;
	}
	
	
	/**
	 * Constructor #3
	 * @param color
	 * @param material
	 * @param radius
	 * @param point
	 */
	public Sphere(Color color,Material material,double radius, Point3D point)
	{
		this(color, radius, point);
		_material= material;
	}

	
	/**
	 * returns the normal of the sphere in a certain point
	 */
	public Vector getNormal(Point3D point)
	{
		 return point.subtract(_q).normalized();
	}

	
	@Override
	public List<GeoPoint> findIntersections(Ray ray,double max)
	{
		double d=0,tm=0;
		
		//To avoid creating a Zero vector, if the ray's point is the center   
		//the point in the radius distance is returned
		if(ray.getP0().equals(_q) && Util.alignZero(_radius -max)<=0)  
			return List.of(new GeoPoint(ray.getPoint(_radius),this));
		
		Vector u = _q.subtract(ray.getP0()); //the vector (u) between the center and the ray's starting point
		tm = ray.getDirection().dotProduct(u); //The shadow of the vector (u) on the ray's vector
		d = Math.sqrt(u.lengthSquared()-(tm*tm)); //The triangle's height  
		
		if (d>=_radius) //no intersection points
			return null;
		
		double th=Math.sqrt(_radius*_radius-d*d);
		
		List<GeoPoint> _list= new ArrayList<GeoPoint>();
		double t1=Util.alignZero(tm+th);
		if(t1>0 && Util.alignZero(t1-max)<=0)
			 _list.add(new GeoPoint(ray.getPoint(t1),this)); //adding the first point to the list
		
		double t2=Util.alignZero(tm-th);
		if(t2>0 &&Util.alignZero(t2-max)<=0)
			 _list.add(new GeoPoint(ray.getPoint(t2),this));//adding the second point to the list
		
		if(_list.size() == 0)
			return null;
		
		return _list;
	}

}
