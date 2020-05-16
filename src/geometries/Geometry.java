package geometries;
import primitives.*;
import primitives.Point3D;

/**
 * abstract class Geometry. All the geometries in the scene inherit from it. 
 * It contains Amission Light (Color) field
 * @author Odel & Tehila
 *
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
	 * Default constructor, initializes the field to black color
	 */
	public Geometry()
	{
		this(Color.BLACK);
	}
	
	/**
	 * constructor, initializes the _emission field
	 * @param emission the geometry's color 
	 */
	public Geometry(Color emission)
	{
		this(emission,new Material(0, 0, 0));
	}
	
	/**
	 * constructor, initializes the _material field and calls the second constructor
	 * @param emission -the geometry's color 
	 * @param material -the geometry's material
	 */
	public Geometry(Color emission,Material material)
	{
		_emission= emission;
		_material=material;
	}
	
	/**
	 * An abstract function that calculates normal to a certain point on the geometry
	 * @param point3D. the function calculates normal to this point
	 * @return the normal to the point on the geometry (vector)
	 */
	public abstract Vector getNormal(Point3D point); 

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
}
