package elements;

import primitives.*;

public class SpotLight extends PointLight 
{
	
	Vector _direction;
	double _narrow;
	
	public SpotLight(Color intensity, Point3D position, Vector direction,double kC, double kL, double kQ) 
	{
		super(intensity, position, kC, kL, kQ);
		_direction= direction.normalized();
		_narrow=1;
	}
	
	public SpotLight(Color intensity, Point3D position, Vector direction,double kC, double kL, double kQ, double narrow) 
	{
		this(intensity, position,direction, kC, kL, kQ);
		_narrow = narrow;
		
	}
	
	@Override
	public Color getIntensity(Point3D p) {
		Vector l=p.subtract(_position).normalized();
		double result = Math.pow(l.dotProduct(_direction), _narrow);
		return super.getIntensity(p).scale(Math.max(0,result));
	}

	@Override
	public Vector getL(Point3D p) {
		return _direction;
	}

}
