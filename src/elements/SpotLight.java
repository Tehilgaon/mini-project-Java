package elements;

import primitives.*;


/**
 * SpotLight class - Light source with position and direction, like a flashlight or spot
 * @author Odel & Tehila
 */
public class SpotLight extends PointLight 
{
	
	/**
	 * The light's direction
	 */
	Vector _direction;
	
	
	/**
	 * the Concentration of the light, how narrow the light is 
	 */
	double _narrow;
	
	
	/**
	 * Constructor #1
	 * @param intensity
	 * @param position
	 * @param direction
	 * @param kC
	 * @param kL
	 * @param kQ
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction,double kC, double kL, double kQ) 
	{
		super(intensity, position, kC, kL, kQ);
		_direction = direction.normalized();
		_narrow =1;
	}
	
	
	/**
	 * Constructor #2
	 * @param intensity
	 * @param position
	 * @param direction
	 * @param kC
	 * @param kL
	 * @param kQ
	 * @param narrow
	 */
	public SpotLight(Color intensity, Point3D position, Vector direction,double kC, double kL, double kQ, double narrow) 
	{
		this(intensity, position,direction, kC, kL, kQ);
		_narrow = narrow;	
	}
	
	
	@Override
	public Color getIntensity(Point3D p) {
		double result = getL(p).dotProduct(_direction);
		if (Util.isZero(result))
			return Color.BLACK;
		
		result = Math.pow(result, _narrow);
		
		double factor = Math.max(0 ,result);
		
		return super.getIntensity(p).scale(factor);
	}


}
