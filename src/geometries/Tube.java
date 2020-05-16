package geometries;

import java.util.List;
import primitives.*;
 

public class Tube extends RadialGeometry implements Intersectable{

	Ray _axisRay;
	
	public Tube(double radius,Ray axis) 
	{
		super(radius);
		_axisRay=axis;
	}
	
	public Tube(Color color,double radius,Ray axis) 
	{
		super(color,radius);
		_axisRay=axis;
	}
	
	public Tube(Material material,Color color,double radius,Ray axis) 
	{
		super(material,color,radius);
		_axisRay=axis;
	}


	 
	public Vector getNormal(Point3D point) {
		// TODO Auto-generated method stub
		//n = normalize(P - O)
        // O is projection of P on cylinder's ray:
        // t = v (P – P0)
        // O = P0 + tv
		Point3D p0 = _axisRay.getP0();
		Vector v = _axisRay.getDirection();
		//t = v (P – P0)
		double t = point.subtract(p0).dotProduct(v);
		// O = P0 + tv
        Point3D o=null;
        if (!Util.isZero(t))// if it's close to 0, we'll get ZERO vector exception
        	o = p0.add(v.scale(t));
        Vector n = point.subtract(o).normalize();
        return n;

        }


	@Override
	public List<GeoPoint> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
	 
	
	

}
