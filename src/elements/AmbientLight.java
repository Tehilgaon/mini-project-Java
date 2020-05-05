package elements;

import primitives.*;

/**
 * AmbientLight Class - The basic color of the scene
 * @author Odel @ Tehila
 *
 */
public class AmbientLight {
	
	/**
	 * The intensity of the AmbientLight
	 */
	Color _intensity;
	
	/**
	 * constructor
	 * @param IA The light's intensity 
	 * @param KA reduction factor
	 */
	public AmbientLight(Color IA, double KA)
	{
		_intensity=IA.scale(KA);
	}
	
	/**
	 * _intensity getter 
	 * @return _intensity
	 */
	public Color GetIntensity()
	{
		return _intensity;
	}
			

}
