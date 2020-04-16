/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import geometries.*;
import primitives.*;
import java.util.List;
//import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * Testing Spheres
 * @author Odel Fhima& Tehila Gaon
 *
 */
class SphereTests {
 

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
		Sphere sp=new Sphere(1,new Point3D(0,0,0));
		 assertEquals("Bad normal to Sphere", new Vector(0,0,1), sp.getNormal(new Point3D(0, 0, 1)));
	}
	
	/**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                      sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        //TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0) ;
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX() > result.get(1).getX())
           result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        List<Point3D> r = sphere.findIntersections(new Ray(new Point3D(0.5, 0, 0),new Vector(1, 0, 0)));
        assertEquals("Wrong ray's starting point",1, r.size());
        
        // TC04: Ray starts after the sphere (0 points)
        assertEquals("Wrong ray's direction ",null, sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0))));
        

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result=sphere.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Wrong ray's direction ",new Point3D(2,0,0),result.get(0));
        
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertEquals("Wrong ray's direction ",null,sphere.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(-1, -1, 0))));
        
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        p1 = new Point3D(1,0,1);
        p2 = new Point3D(1,0,-1) ;
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 3),new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", 2, result.size());
        assertEquals("Ray crosses sphere", List.of(p2, p1), result);
        
        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 1),new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", new Point3D(1,0,-1), result.get(0));
        
        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 0.5),new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", new Point3D(1,0,-1), result.get(0));
        
        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 0),new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", new Point3D(1,0,-1), result.get(0));
        
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray crosses sphere", null, sphere.findIntersections(new Ray(new Point3D(1, 0, 1),new Vector(0, 0, 1))));
        // TC18: Ray starts after sphere (0 points)
        assertEquals("Ray crosses sphere", null, sphere.findIntersections(new Ray(new Point3D(1, 0, 2),new Vector(0, 0, 1))));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        //assertEquals("Ray crosses sphere", null, sphere.findIntersections(new Ray(new Point3D(0, 0, -1),new Vector(0, 0, 1))));??????
        
        // TC20: Ray starts at the tangent point
        assertEquals("Ray crosses sphere", null, sphere.findIntersections(new Ray(new Point3D(0, 0, 0),new Vector(0, 0, 1))));
        
        // TC21: Ray starts after the tangent point
        assertEquals("Ray crosses sphere", null, sphere.findIntersections(new Ray(new Point3D(1.5, 0, 1),new Vector(0, 0, 1))));

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertEquals("Ray crosses sphere", null, sphere.findIntersections(new Ray(new Point3D(0, 0, 0),new Vector(0, 0, 1))));


    }



}
