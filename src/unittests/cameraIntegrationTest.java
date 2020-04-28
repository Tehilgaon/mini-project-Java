package unittests;

import static org.junit.Assert.*;
import primitives.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

import elements.Camera;

/**
 * Testing the integration between the camera function of finding the rays 
 * and the geometries' function of finding the intersection points
 * @author Tehila@ Odel
 *
 */
class cameraIntegrationTest {

	@Test
	void testCameraIntegrationWithSphere() {
		
		Camera camera=new Camera(new Point3D(15,0,0),new Vector(-1,0,0),new Vector(0,0,1));
		Sphere sphere=new Sphere(10d,new Point3D(0,0,0));
		assertEquals("Wrong number of points", 18, findIntersection(camera,sphere,10).size());
		
		sphere=new Sphere(1d,new Point3D(0,0,0));
		assertEquals("Wrong number of points",2, findIntersection(camera,sphere,10).size());
		
		camera=new Camera(new Point3D(-0.5,0,0),new Vector(1,0,0),new Vector(0,0,1));
		sphere=new Sphere(2d,new Point3D(2,0,0));
		assertEquals("Wrong number of points",10, findIntersection(camera,sphere,1).size());
		
		sphere=new Sphere(4d,new Point3D(2,0,0));
		assertEquals("Wrong number of points",9, findIntersection(camera,sphere,1).size());
		
		sphere=new Sphere(0.5d,new Point3D(-3,0,0));
		assertEquals("Wrong number of points",0, findIntersection(camera,sphere,1).size());

		


	}
	
	@Test
	void testCameraIntegrationWithPlane() {
		Plane plane=new Plane(new Point3D(0,0,0),new Vector(1,0,0));
		Camera camera=new Camera(new Point3D(15,0,0),new Vector(-1,0,0),new Vector(0,0,1));
		assertEquals("bad point's number", 9, findIntersection(camera,plane,0.5).size());
		
		camera=new Camera(new Point3D(-15,0,0),new Vector(1,0,0),new Vector(0,0,1));
		plane=new Plane(new Point3D(1,0,0),new Point3D(0,0,3),new Point3D(3,7,0));
		assertEquals("bad point's number", 9, findIntersection(camera,plane,1).size());
		
		camera=new Camera(new Point3D(-15,0,0),new Vector(1,0,0),new Vector(0,0,1));
		plane=new Plane(new Point3D(3,0,0),new Point3D(0,0,3),new Point3D(3,7,0));
		assertEquals("bad point's number", 6, findIntersection(camera,plane,1).size());

	}
	
	@Test
	void testCameraIntegrationWithTriangle() {
		Triangle triangle= new Triangle(new Point3D(1,0,0.5),new Point3D(1,-0.5,-0.5),new Point3D(1,0.5,-0.5));
		Camera camera=new Camera(new Point3D(-1,0,0),new Vector(1,0,0),new Vector(0,0,1));
		assertEquals("bad point's number", 1, findIntersection(camera,triangle,1).size());
		
		triangle= new Triangle(new Point3D(1,0,3),new Point3D(1,-0.75,-0.5),new Point3D(1,0.75,-0.5));
		assertEquals("bad point's number", 2, findIntersection(camera,triangle,1).size());
	}
	
	
	 /**
	  * The function, for each pixel, calls the camera's function to get a ray and finds the intersection points of this ray with the geometry 
	  * @param camera
	  * @param geometry
	  * @param sd- screen Distance
	  * @return List of Point3D , contains all the intersection points between all the rays and the geometry
	  */
	List<Point3D> findIntersection(Camera camera, Intersectable geometry, double sd)
	{
		List<Point3D> pointsList=new ArrayList<Point3D>();
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				Ray ray=camera.constructRayThroughPixel(3, 3, j, i,sd,3,3);
				List<Point3D> result=geometry.findIntersections(ray);
				if(result!=null)
					pointsList.addAll(result);
			}
		return pointsList;	
	}
	
	
	

}
