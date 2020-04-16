/**
 * 
 */
package unittests;

 
import static org.junit.Assert.*;

import java.util.List;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Testing Triangles
 * @author Odel Fhima& Tehila Gaon
 *
 */
class TriangleTests {

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
		Triangle tr=new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
		double sqrt3 = Math.sqrt(1d/3);
        assertEquals("Bad normal to Triangle", new Vector(sqrt3, sqrt3, sqrt3), tr.getNormal(new Point3D(0, 0, 1)));
	}
	
	@Test
    public void testFindIntersections() {
		Triangle triangle=new Triangle(new Point3D(1,1,0),new Point3D(2,1,0),new Point3D(2,0,0));
		
		// ============ Equivalence Partitions Tests ==============

        // TC01:  Ray Inside triangle
		List<Point3D> result=triangle.findIntersections(new Ray(new Point3D(1.5,0.75,-1),new Vector(0,0,1)));
		assertEquals("Wrong number of points",1,result.size());
		assertEquals("Wrong ray's direction",new Point3D(1.5,0.75,0),result.get(0));
		
		// TC02:  Ray outside against edge
		assertEquals("Wrong ray's direction",null,triangle.findIntersections(new Ray(new Point3D(0,2,0),new Vector(1,0,0))));
		assertEquals("Wrong ray's direction",null,triangle.findIntersections(new Ray(new Point3D(1.5,2,-1),new Vector(0,0,1))));

		
		// TC03:  Ray outside against vertex
		assertEquals("Wrong ray's direction",null,triangle.findIntersections(new Ray(new Point3D(2,-1,-1),new Vector(1,0,0))));
		
		// =============== Boundary Values Tests ==================
  
        // TC04: Ray intersect the traingle's edge (?)
		assertEquals("Wrong ray",null, triangle.findIntersections(new Ray(new Point3D(1.5,1,-1),new Vector(0,0,1))));	
		
		// TC05: Ray intersect the traingle's vertex (?)
		assertEquals("Wrong ray",null, triangle.findIntersections(new Ray(new Point3D(2,0,-1),new Vector(0,0,1))));	
		
		// TC06: Ray intersect the edge's continuation
		result=triangle.findIntersections(new Ray(new Point3D(0,1,-1),new Vector(0,0,1)));
		assertEquals("Wrong ray",null, result);	
		
		
	}	

}
