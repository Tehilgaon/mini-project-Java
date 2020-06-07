package geometries;

import java.util.List;
import java.util.ArrayList;

import primitives.*;
 

/**
 * Geometries class
 * Contains a list of geometries that implement the Intersectable interface
 * 
 * @author Odel & Tehila
 */
public class Geometries implements Intersectable {

	
	/**
	 * List of geometries that implement the Intersectable interface
	 */
	List<Intersectable> _geometries;
	
	
	/**
	 * Constructor #1 - Default constructor
	 * Initializes the list
	 */
	public Geometries()
	{
		_geometries= new ArrayList<Intersectable>();
	}
	
	
	/**
	 * Constructor #2
	 * @param geometries
	 */
	public Geometries(Intersectable... geometries)
	{
		_geometries=List.of(geometries);
	}
	
	
	/**
	 * Adding geometries to the list
	 * @param geometries
	 */
	public void add(Intersectable... geometries)
	{
		_geometries.addAll(List.of(geometries));
	}
	
	
	/**
	 * removing geometries from the list
	 * @param geometries
	 */
	public void remove(Intersectable... geometries) {
        for (Intersectable geo :  geometries) {
            _geometries.remove(geo);
        }
    }
	 

	@Override
	public List<GeoPoint> findIntersections(Ray ray, double max) {
		
		List<GeoPoint> list=new ArrayList<GeoPoint>();
		
		for(Intersectable geo : _geometries)
		{
			List<GeoPoint> geometryList=geo.findIntersections(ray, max);
			if(geometryList != null)
				list.addAll(geometryList);
		
		}
		if(list.size() == 0)
			return null;
		
		return list;
	}

}
