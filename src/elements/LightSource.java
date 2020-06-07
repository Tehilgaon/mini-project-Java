package elements;

import primitives.*;


/**
 * Interface for methods of all the light sources in the scene
 * @author Odel & Tehila
 */
public interface LightSource 
{
	
	
	/**
	 * Returns the Light's intensity on a certain point
	 * @param p Point3D
	 * @return Color intensity
	 */
	public Color getIntensity(Point3D p);
	
	
	/**
	 * Returns the direction vector from the light source to the point
	 * @param p Point3D
	 * @return Vector direction
	 */
	public Vector getL(Point3D p);
	
	
	/**
	 * Returns the distance between the light source and the point
	 * @param point point3D
	 * @return distance (double) 
	 */
	public double getDistance(Point3D point);
}
