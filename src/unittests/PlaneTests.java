/**
 * 
 */
package unittests;
import static org.junit.Assert.*;

import java.util.List;
import geometries.Intersectable.GeoPoint;
import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

/**
 *  Testing Planes
 *  @author Odel Fhima& Tehila Gaon
 *
 */
class PlaneTests {

	/** 
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
	 */
	@Test
	void testGetNormalPoint3D() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
		Plane pl=new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
		double sqrt3 = Math.sqrt(1d/3);
        assertEquals("Bad normal to Plane", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }
	
	@Test
    public void testFindIntersections() {
		Plane plane=new Plane(new Point3D(0,0,0),new Vector(0,0,1));
		
		// ============ Equivalence Partitions Tests ==============

        // TC01:  Ray intersects the plane
		List<GeoPoint> result=plane.findIntersections(new Ray(new Point3D(0,0,-1),new Vector(1,1,1)));
		assertEquals("Wrong number of points",1, result.size());
		assertEquals("Wrong ray",new Point3D(1,1,0), result.get(0).point);
		
		// TC02:  Ray does not intersect the plane
        assertEquals("Wrong ray's direction",null,plane.findIntersections(new Ray(new Point3D(0,0,-1),new Vector(-1,-1,-1))));
        
        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC03: the ray is not included in the plane
        assertEquals("Wrong ray's direction",null,plane.findIntersections(new Ray(new Point3D(0,0,-1),new Vector(1,0,0))));
        // TC04: the ray is included in the plane
        assertEquals("Wrong ray's direction",null,plane.findIntersections(new Ray(new Point3D(0,0,0),new Vector(-1,0,0)))); //???
        
        // **** Group: Ray is orthogonal to the plane
        // TC05: Ray starts before the plane
        result=plane.findIntersections(new Ray(new Point3D(-1,-1,-1),new Vector(0,0,1)));
        assertEquals("Wrong number of points",1,result.size());
        assertEquals("Wrong ray's direction",new Point3D(-1,-1,0),result.get(0).point);
        
        // TC06: Ray starts before the plane
        assertEquals("Wrong ray's direction",null,plane.findIntersections(new Ray(new Point3D(-1,-1,0),new Vector(0,0,1))));
        
        // TC07: Ray starts before the plane
        assertEquals("Wrong ray's direction",null,plane.findIntersections(new Ray(new Point3D(-1,-1,1),new Vector(0,0,1))));
        
        // TC08: Ray is neither orthogonal nor parallel to and begins at the plane
        assertEquals("Wrong ray's direction",null,plane.findIntersections(new Ray(new Point3D(1,2,0),new Vector(1,2,3))));
        
        // TC09 Ray is neither orthogonal nor parallel to and begins at the plane
        assertEquals("Wrong ray's direction",null,plane.findIntersections(new Ray(new Point3D(0,-1,0),new Vector(1,2,3))));
         
        
	}

}	
