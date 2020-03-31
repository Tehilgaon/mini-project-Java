/**
 * 
 */
package unittests;

import static org.junit.Assert.*;
import geometries.*;
import primitives.*;
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

}
