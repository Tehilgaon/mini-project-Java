package geometries;

import primitives.*;
 

public class Cylinder extends Tube {

	double _height;
	public Cylinder(double radius,Ray axis,double height) {
		super(radius, axis);
		_height=height;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Vector getNormal(Point3D point) {
	
		//n = normalize(P - O)
        // O is projection of P on cylinder's ray:
        // t = v (P – P0)
        // O = P0 + tv
		Point3D p0 = _axisRay.getP0();
		Vector v = _axisRay.getDirection();
		//t = v (P – P0)
		if(point.subtract(p0).dotProduct(v)==0)
			return v;
		if(point.subtract(p0.add(v.scale(_height))).dotProduct(v)==0)
			return v;
		return super.getNormal(point);
    
	}

}
