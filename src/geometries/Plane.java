package geometries;

import java.util.List;
import primitives.*;


/**
 * Plane class 
 * Is represented by a point and normal
 * @author Odel & Tehila
 */
public class Plane extends Geometry implements Intersectable
{
	
	
	/**
	 * Point3D
	 */
	Point3D _p;
	
	
	/**
	 * Vector normal to the plane
	 */
	Vector _normal;
	
	
	/**
	 * Constructor #1
	 * @param p1 Point3D
	 * @param p2 Point3D
	 * @param p3 Point3D
	 */
	public Plane(Point3D p1,Point3D p2,Point3D p3)
	{
		Vector v = p1.subtract(p2);
		Vector u = p2.subtract(p3);
		
		_normal=new Vector(v.crossProduct(u)).normalized();
		_p=new Point3D(p1);
	}
	
	
	/**
	 * Constructor #2
	 * @param point Point3D
	 * @param normal Vector
	 */
	public Plane(Point3D point,Vector normal)
	{
		_p=new Point3D(point);
		_normal=new Vector(normal.normalized());
	}
	
	
	/**
	 * Constructor #3
	 * @param color
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Plane(Color color, Point3D p1,Point3D p2,Point3D p3)
	{
		this(p1,p2,p3);
		_emission= color;
	}
	
	
	/**
	 * Constructor #4
	 * @param color
	 * @param point
	 * @param normal
	 */
	public Plane(Color color,Point3D point,Vector normal)
	{
		this(point, normal);
		_emission= color;
	}
	
	
	/**
	 * Constructor #5
	 * @param material
	 * @param color
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Plane(Material material, Color color, Point3D p1,Point3D p2,Point3D p3)
	{
		this(color,p1,p2,p3);
		_material = material;
	}
	
	
	/**
	 * Constructor #6
	 * @param material
	 * @param color
	 * @param point
	 * @param normal
	 */
	public Plane(Material material,Color color,Point3D point,Vector normal)
	{
		this(color,point, normal);
		_material = material;
	}
	
	
	/**
	 * _normal getter
	 * @return
	 */
	public Vector getNormal()
	{
		return _normal;
	}
	
	
	/**
	 * _p getter
	 * @return
	 */
	public Point3D getPoint()
	{
		return _p;
	}
	
	
	/**
	 * Returns the normal to the plane
	 */
	@Override
	public Vector getNormal(Point3D point) {
		return _normal;
	}
	
	
	@Override
	public List<GeoPoint> findIntersections(Ray ray, double max) {
		
		//finding the point q
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
		Vector v = new Vector(_x,_y,_z);//taking a vector (v) with all components zero except that one on the place of the smallest component 
		Vector PQ = _normal.crossProduct(v);//Compute PQ=v×n. PQ is orthogonal to n
		Point3D _q = new Point3D(_p.getX()-PQ.get().getX(),_p.getY()-PQ.get().getY(),_p.getZ()-PQ.get().getZ());//getting the point _q from the PQ vector	
		
		
		if(ray.getP0().equals(_q)) //When Q0 = p0, there is no intersection point
			return null;
		
		//Calculating t = n*(q - p0) / n*v0
		double numerator=_normal.dotProduct(_q.subtract(ray.getP0()));
		double denominator=_normal.dotProduct(ray.getDirection());
		
		if(Util.isZero(numerator)||Util.isZero(denominator)) //When the ray is parallel to the plane and 't' is infinity OR when P0 is included in the plane  
			return null;
		
		double t = Util.alignZero(numerator/denominator);
		if (t<0 || Util.alignZero(t-max)>0)
			return null;
		
		return List.of(new GeoPoint(ray.getPoint(t),this));	 
	}

}
