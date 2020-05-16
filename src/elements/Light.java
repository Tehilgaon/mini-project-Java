package elements;

import primitives.*;

abstract class Light 
{
	/**
	 * The intensity of the Light
	 */
	protected Color _intensity;
	
	/**
	 * constructor, it initializes the _intensity field
	 * @param intensity- The light's intensity
	 */
	public Light(Color intensity)
	{
		_intensity=intensity;
	}
	
	/**
	 * _intensity getter 
	 * @return _intensity
	 */
	public Color getIntensity()
	{
		return _intensity;
	}
	

}
