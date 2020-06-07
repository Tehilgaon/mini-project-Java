package geometries;
import primitives.*;
import primitives.Point3D;


/**
 * Abstract class Geometry
 * All the geometries in the scene inherit from it and implement getNormal method
 * @author Odel & Tehila
 */
public abstract class Geometry {
	
	
	/**
	 * The geometry's color
	 */
	protected Color _emission;
	
	
	/**
	 * The geometry's material
	 */
	protected Material _material;
	
	
	/**
	 * Constructor #1- Default constructor
	 * initializes the field to black color
	 */
	public Geometry()
	{
		this(Color.BLACK);
	}
	
	
	/**
	 * Constructor #2 - initializes the _material with (0,0,0)
	 * @param emission the geometry's color 
	 */
	public Geometry(Color emission)
	{
		this(emission,new Material(0, 0, 0));
	}
	
	
	/**
	 * constructor #3
	 * @param emission color 
	 * @param material
	 */
	public Geometry(Color emission,Material material)
	{
		_emission= emission;
		_material=material;
	}
	
	
	/**
	 * The _emission's getter
	 * @return the _emissiom's value (Color)
	 */
	public Color getEmission()
	{
		return _emission;
	}
	
	
	/**
	 * _material getter
	 * @return the material of the geometry
	 */
	public Material getMaterial()
	{
		return _material;
	}
	
	
	/**
	 * An abstract function that calculates normal to a certain point on the geometry
	 * @param point3D. the function calculates normal to this point
	 * @return the normal to the point on the geometry (vector)
	 */
	public abstract Vector getNormal(Point3D point); 

	
}
