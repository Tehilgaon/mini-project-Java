/**
 * 
 */
package unittests;
 
import static org.junit.Assert.*;

import org.junit.Test;

import primitives.*;

import static primitives.Util.isZero;
 

 

/**
 * Testing Vectors
 * @author Odel Fhima& Tehila Gaon
 *
 */
public class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	public void testSubtract() {
		Point3D p1 = new Point3D(1, 2, 3); 
		assertEquals("ERROR: Point - Point does not work correctly",new Vector(1, 1, 1), new Point3D(2, 3, 4).subtract(p1));
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	public void testAdd() {
		 Point3D p1 = new Point3D(1, 2, 3); 
		 assertEquals("ERROR: Point + Vector does not work correctly", Point3D.ZERO, p1.add(new Vector(-1, -2, -3)));
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() {
		 assertEquals("crossProduct() wrong result length", new Vector(1,0,-1).scale(5), new Vector(5,0,-5));

	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	public void testDotProduct() {
		Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
		assertTrue("ERROR: dotProduct() for orthogonal vectors is not zero", isZero(v1.dotProduct(v3)));
		assertTrue("ERROR: dotProduct() wrong value", isZero(v1.dotProduct(v2) + 28));
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-productof co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() {
		Vector v1 = new Vector(1, 2, 3);
		 assertTrue("ERROR: lengthSquared() wrong value", isZero(v1.lengthSquared() - 14));
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	public void testLength() {
		 assertTrue("ERROR: length() wrong value", isZero(new Vector(0, 3, 4).length() - 5));
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	public void testNormalize() {
		Vector v = new Vector(1, 2, 3);
        Vector vCopy = new Vector(v);
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals("ERROR: normalize() function creates a new vector", vCopy, vCopyNormalize);
        assertTrue("ERROR: normalize() result is not a unit vector", isZero(vCopyNormalize.length() - 1));
	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() {
		Vector v = new Vector(1, 2, 3);
		Vector u = v.normalized();
		assertTrue("ERROR: normalizated() function does not create a new vector", u != v);
	}

}
