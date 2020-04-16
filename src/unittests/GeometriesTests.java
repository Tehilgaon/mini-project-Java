package unittests;

 
import geometries.*;
import primitives.*;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

class GeometriesTests {

	@Test
	void testFindIntersections() {
		
		// TC01:  List does not contain any geometry
		Geometries geometries=new Geometries();
		assertEquals("Wrong number of points",null, geometries.findIntersections(new Ray(new Point3D(0,0,5),new Vector(0,0,1))));

		geometries.add(new Plane(new Point3D(0,0,-2),new Vector(0,0,1)),new Plane(new Point3D(0,-2,0),new Vector(0,1,0)),
				new Sphere(1d,new Point3D(0,0,0)),new Triangle(new Point3D(1,-1,2),new Point3D(-1,-1,2),new Point3D(0,1,2)));
		
		// TC02:  Ray does not intersect any geometry
		assertEquals("Wrong number of points",null, geometries.findIntersections(new Ray(new Point3D(0,0,5),new Vector(0,0,1))));
		
		// TC03:  Ray intersect one geometry
		List<Point3D>result=geometries.findIntersections(new Ray(new Point3D(0,0,1.5),new Vector(0,0,1)));
		assertEquals("Wrong number of points",1, result.size());
		
		// ============ Equivalence Partitions Tests ==============

        // TC04:  Ray intersects few geometries, but not all of them
		result=geometries.findIntersections(new Ray(new Point3D(0,0,-4),new Vector(0,0,1)));
		assertEquals("Wrong number of points",4, result.size());
		
		result=geometries.findIntersections(new Ray(new Point3D(0,4,0),new Vector(0,-1,0)));
		assertEquals("Wrong number of points",3, result.size());
		
		result=geometries.findIntersections(new Ray(new Point3D(0,0,0),new Vector(0,0,1)));
		assertEquals("Wrong number of points",2, result.size());
		 
		

	}

}
