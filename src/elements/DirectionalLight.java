package elements;


import primitives.*;


/**
 * DirectionalLight class - Directional light source, like sun
 * @author Odel & Tehila
 *
 */
public class DirectionalLight extends Light implements LightSource {

	
	/**
	 * The light's direction
	 */
	Vector _direction;

	
	/**
	 * Constructor
	 * @param intensity
	 * @param direction
	 */
	public DirectionalLight(Color intensity,Vector direction)
	{
		super(intensity);
		_direction= direction.normalized();
	}
	
	
	@Override
	public Color getIntensity(Point3D p) {
		return super._intensity;
	}

	@Override
	public Vector getL(Point3D p) {
		return _direction;
	}

	
	/**
	 * the function returns the distance between the lightSource and the Point.
	 * In this case, the distance is so big that we consider it infinite
	 */
	@Override
	public double getDistance(Point3D point) {
		return Double.POSITIVE_INFINITY;
	}
	

}
