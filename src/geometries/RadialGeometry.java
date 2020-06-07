package geometries;

import primitives.*;
 
 
/**
 * Abstract class that defines all the radial geometries
 * @author Odel & Tehila
 */
public abstract class RadialGeometry extends Geometry
{
	
	/**
	 * radius
	 */
	protected double _radius;
	
	
	/**
	 * Constructor #1
	 * @param radius
	 */
	public RadialGeometry(double radius) {
		setRadius(radius);
	}
	
	
	/**
	 * Constructor #2
	 * @param color emission
	 * @param radius
	 */
	public RadialGeometry(Color color,double radius) {
		super(color);
		setRadius(radius);
	}
	
	
	/**
	 * Constructor #3
	 * @param material
	 * @param color emission
	 * @param radius
	 */
	public RadialGeometry(Material material,Color color,double radius) {
		super(color, material);
		setRadius(radius);
	}
	
	
	/**
	 * Constructor #4 - copy constructor
	 * @param other
	 */
	public RadialGeometry(RadialGeometry other) {
		super(other._emission, other._material);
		setRadius(other._radius);
	}
	
	
	/**
	 * _radius setter
	 * @param radius
	 * @throws exception if radius is not valid
	 */
	public void setRadius(double radius) {
		if(Util.isZero(radius) || radius<0)
			throw new IllegalArgumentException("unvalid radius");
		_radius = radius;
	}
	
	
	/**
	 * _radius getter
	 * @return radius
	 */
	public double getRadius() {
		return _radius;
	}
	
	 

}
