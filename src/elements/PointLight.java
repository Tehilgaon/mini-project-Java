package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;


/**
 * PointLight class - Light source with position but no direction, like a lamp
 * @author Odel & Tehila
 */
public class PointLight extends Light implements LightSource
{

	
	/**
	 * the light's position
	 */
	protected Point3D _position;
	
	
	/**
	 * Attenuation factors
	 */
	protected double _kC, _kL, _kQ;
	
	
	/**
	 * Constructor
	 * @param intensity
	 * @param position
	 * @param kC
	 * @param kL
	 * @param kQ
	 */
	public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ)
	{
		super(intensity);
		_position= position;
		_kC= kC;
		_kL= kL;
		_kQ= kQ;
	}
	
	
	@Override
	public Color getIntensity(Point3D p) {
		double d= p.distance(_position);
		return super._intensity.reduce(_kC + _kL*d + _kQ*d*d);
	}

	
	@Override
	public Vector getL(Point3D p) {
		if(p.equals(_position)) 
			return null;
		return p.subtract(_position).normalized(); 
	}

	
	@Override
	public double getDistance(Point3D point) { 
		return _position.distance(point);
	}
	

}
