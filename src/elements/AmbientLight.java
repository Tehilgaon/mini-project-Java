package elements;

import primitives.*;

/**
 * AmbientLight Class - The basic color of the scene
 * @author Odel @ Tehila
 *
 */
public class AmbientLight extends Light {
 
	
	/**
	 * constructor, calls the base constructor and sends him the intensity * the reduction factor 
	 * @param IA The light's intensity 
	 * @param KA reduction factor
	 */
	public AmbientLight(Color IA, double KA)
	{
		super(IA.scale(KA));
	}
		

}
