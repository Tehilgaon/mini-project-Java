package geometries;
import java.util.*;  
import primitives.*;


/**
 * Interface representing all classes that can calculate their 
 * intersection points with a ray. It contains the function "findIntersection".
 * @author Odel & Tehila
 */
public interface Intersectable {
	
	
	/**
	 * calculates all the intersection points of a ray with geometry
	 * @param ray
	 * @return a list of pairs, each pair being a point with the geometry it is on (list<GeoPoint>)
	 */
	default List<GeoPoint> findIntersections(Ray ray) {
    	return findIntersections(ray, Double.POSITIVE_INFINITY);
	}

	
	/**
	 * calculates the intersection points of a ray with geometry 
	 * that their distance from the ray's position is less then MAX
	 * @param ray
	 * @param max distance
	 * @return list of GeoPoints
	 */
	List<GeoPoint> findIntersections(Ray ray, double max);
	
	
	/**
	 * Static class contains a Point3D and Geometry. 
	 * It is designed to attribute a certain point to the geometry it is on
	 */
	public static class GeoPoint {
		
		
		/**
		 * Geometry 
		 */
	    public Geometry geometry;
	    
	    
	    /**
	     * Point3D
	     */
	    public Point3D point;
	    
	    
	    /**
	     * Constructor  
	     * @param Point  
	     * @param Geometry
	     */
	    public GeoPoint(Point3D Point, Geometry Geometry)
	    {
	    	geometry=Geometry;
	    	point= Point;
	    }
	    
	    
	    @Override
		public boolean equals(Object obj) {
			if(this==obj) return true;
			if(obj==null) return false;
			if(!(obj instanceof GeoPoint)) return false;
			GeoPoint oth=(GeoPoint)obj;
			return (point.equals(oth.point) && geometry==oth.geometry);
	    }
	}

	
}
