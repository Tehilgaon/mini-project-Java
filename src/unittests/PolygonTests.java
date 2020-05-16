/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.util.List;
import geometries.Intersectable.GeoPoint;
import org.junit.Test;
import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 * @author Dan
 *
 */
public class PolygonTests {

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertix on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d/3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }
    
    @Test
    public void testFindIntersections() {
		Polygon polygon=new Polygon(new Point3D(1,1,0),new Point3D(1,-1,0),new Point3D(-1,-1,0),new Point3D(-1,1,0));
		
		// ============ Equivalence Partitions Tests ==============

        // TC01:  Ray intersects the Polygon
		List<GeoPoint> result=polygon.findIntersections(new Ray(new Point3D(0.5,0.5,-1),new Vector(0,0,1)));
		assertEquals("Wrong number of points",1, result.size());
		assertEquals("Wrong ray",new Point3D(0.5,0.5,0), result.get(0).point);
		
		// TC02:  Ray does not intersect the Plane that the Polygon is part of
		assertEquals("Wrong ray's direction",null, polygon.findIntersections(new Ray(new Point3D(0.5,0.5,-1),new Vector(-1,-1,-1))));
				
		// TC03:  Ray intersects the Plane but does not intersect the Polygon  
		result=polygon.findIntersections(new Ray(new Point3D(0.5,0.5,-1),new Vector(3,5,7)));
		assertEquals("Wrong ray's direction",null, result);		
		
		// =============== Boundary Values Tests ==================
   
        // TC04: Ray intersect the polygon's edge (no intersection points)
		assertEquals("Wrong ray",null,polygon.findIntersections(new Ray(new Point3D(-1,-1,-1),new Vector(0,0,1))));		
		
    }

}
