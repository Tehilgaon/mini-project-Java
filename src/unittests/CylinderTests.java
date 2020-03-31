/**
 * 
 */
package unittests;

 
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

/**
 * @author Odel Fhima& Tehila Gaon
 *
 */
class CylinderTests {

	 
	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point3D)}.
	 */
	@Test
	void testGetNormal() {
		Cylinder cy=new Cylinder(4,new Ray(new Point3D(0,0,0),new Vector(0,0,1)),4);
		// ============ Equivalence Partitions Tests ==============
        //   There are three tests here
		
		//TC01: Test for the points on the cylinder's side
		assertEquals("Bad normal to Cylinder", new Vector(1,0,0), cy.getNormal(new Point3D(1, 0, 1)));
		
		//TC02: Test for the points that are on the top of the cylinder . The normal is the axis's vector  
		assertEquals("Bad normal to Cylinder", new Vector(0,0,1), cy.getNormal(new Point3D(1, 0, 4)));
		
		//TC03: Test for the points that are on the bottom of the cylinder . The normal is the axis's vector  
		assertEquals("Bad normal to Cylinder", new Vector(0,0,1), cy.getNormal(new Point3D(1, 0, 0)));
		
		// =============== Boundary Values Tests ==================
        //TC04: Test for the points in the seam between the top base and the cylinder side-----
		assertEquals("Bad normal to Cylinder", new Vector(0,0,1), cy.getNormal(new Point3D(4, 0, 4)));
		//TC05: Test for the points in the seam between the bottom base and the cylinder side
		assertEquals("Bad normal to Cylinder", new Vector(0,0,1), cy.getNormal(new Point3D(4, 0, 0)));
		
	}
}