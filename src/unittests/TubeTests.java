/**
 * 
 */
package unittests;

 
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Testing Tubes
 * @author Odel Fhima& Tehila Gaon
 *
 */
class TubeTests {

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
		 Tube tb=new Tube(1.0,new Ray(new Point3D(0,0,1), new Vector(0,1,0)));
		 assertEquals("Bad normal to Tube", new Vector(0,0,1), tb.getNormal(new Point3D(0, 0.5, 2)));
	}

}
