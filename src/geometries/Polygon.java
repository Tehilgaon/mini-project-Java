 
package geometries;

import java.util.List;
import java.util.ArrayList;
import primitives.*;
import static primitives.Util.*;


/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry implements Intersectable {
	
	
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    
    
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    
    /**
     * Constructor #1
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * 
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }
    
    
    /**
     * Constructor #2
     * @param color emission
     * @param vertices
     */
    public Polygon(Color color, Point3D... vertices ) 
    {
    	this(vertices);
    	_emission= color;
    }
    
    
    /**
     * Constructor #3
     * @param material
     * @param color emission
     * @param vertices
     */
    public Polygon(Material material,Color color, Point3D... vertices ) 
    {
    	this(color,vertices);
    	_material= material; 
    }
    
    
    @Override
    public String toString() {
        String string = "";
        for (Point3D p : _vertices) 
        	string += p.toString();

        return string;
    }
    
    
    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal(point);
    }

    
	@Override
	public List<GeoPoint> findIntersections(Ray ray, double max) {
		 
		List<GeoPoint> list=_plane.findIntersections(ray, max); //calling the plane's findIntersections method
		
		if(list == null)
			return null;
			
		
		list.get(0).geometry = this;
			
		//vectorList contains the results of subtracting P0 from the polygon vertexes
		List<Vector> vectorList=new ArrayList<Vector>(); 
		for(int i=0;i<_vertices.size();i++) 
			vectorList.add(_vertices.get(i).subtract(ray.getP0()));
			
		//resultList contains the results of dotProduct between the normal of each 'wall' and the ray's vector
		List<Double> resultList=new ArrayList<Double>(); 
		for(int i=0;i<_vertices.size();i++)
		{
			if(i+1==_vertices.size())
				resultList.add((vectorList.get(0).crossProduct(vectorList.get(i))).dotProduct(ray.getDirection()));
			else
				resultList.add(vectorList.get(i+1).crossProduct(vectorList.get(i)).dotProduct(ray.getDirection()));
		}
		
		//checking whether all elements have the same sign
		double Plus=0, Minus=0;
		for(int i=0;i<resultList.size();i++)
		{
			if(resultList.get(i)>0)
				Plus++;
			if(resultList.get(i)<0)
				Minus++;
		}
		if (Plus != resultList.size() && Minus != resultList.size())
			return null; 
		
		return list;
	}
}
