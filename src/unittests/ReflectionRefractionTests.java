/**
 * 
 */
package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
 
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 *
 */
public class ReflectionRefractionTests {

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	//@Test
	public void twoSpheres() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
						new Point3D(0, 0, 50)),
				new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

		scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
				0.0004, 0.0000006));
		ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	//@Test
	public void twoSpheresOnMirrors() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(10000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.addGeometries(
				new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), 400, new Point3D(-950, 900, 1000)),
				new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
				new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
						new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

		scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(-750, 750, 150), 
				   new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

		ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
		Render render = new Render(imageWriter, scene).setMultithreading(4).setDebugPrint();

		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
	 *  producing partial shadow
	 */
	//@Test
	public void trianglesTransparentSphere() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
				new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
						new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)) //
				,new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // 
						30, new Point3D(60, -50, 50))
		);

		scene.addLights(new SpotLight(new Color(700, 400, 400), //
				new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);//.setMultithreading(3).setDebugPrint();

		render.renderImage();
		render.writeToImage();
	}
	
	
	
	@Test
	public void pyramid() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( //
				new Triangle(Color.BLACK, new Material(0.2, 0.2, 30, 0.4 , 0.4), //
						new Point3D(0, 0, 100), new Point3D(50, 50, 250), new Point3D(0, -50, 250)), //
				new Triangle(Color.BLACK, new Material(0.2, 0.2, 30, 0.4, 0.4), //
						new Point3D(0, 0, 100), new Point3D(50, 50, 250), new Point3D(-50, 50, 250)) //
				,new Triangle(Color.BLACK, new Material(0.2, 0.2, 30, 0.4, 0.4), //
						new Point3D(0, 0, 100), new Point3D(-50, 50, 250), new Point3D(0, -50, 250)) 
				,new Triangle(Color.BLACK, new Material(0.2, 0.2, 30, 0, 0.4), //
						new Point3D(50, 50, 250), new Point3D(-50, 50, 250), new Point3D(0, -50, 250)) 
				,new Plane(new Material(0.5, 0.6, 60,0,0.5),new Color(java.awt.Color.BLACK),//
					new Point3D(50, 50, 260), new Point3D(-50, 50,260), new Point3D(0, -50, 260))//
				,new Sphere(new Color(java.awt.Color.GREEN), new Material(0.2, 0.2, 30, 0, 0), // 
						12, new Point3D(0 , -100, 10))
				 
		);

		scene.addLights(new SpotLight(new Color(200, 150, 150), //
				new Point3D(100, -40, 40), new Vector(-1, 0, 0), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("sphere inside a pyramid", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
	
	
	//@Test
	public void reflectedSphere() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -5000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries( 
				new Sphere(new Color(java.awt.Color.RED),new Material(0.2, 0.2, 30, 0.6, 0),25,new Point3D(20,20,60)),
			    new Plane(new Material(0.5, 0.6, 60,0.5,0),Color.BLACK,new Point3D(0,0, 120), new Point3D(2,3, 120),new Point3D(7,8, 120 )),
				new Sphere(new Color(java.awt.Color.ORANGE),new Material(0.2, 0.2, 30, 0.6, 0),50,new Point3D(-20,-20,500)),
				new Plane(new Material(0.5, 0.6, 60,0.5,0),Color.BLACK,new Point3D(0,0, 600), new Point3D(2,3, 600),new Point3D(7,8, 600 ))
				 
		);

		scene.addLights(new SpotLight(new Color(200, 150, 150), 
				new Point3D(-10, -10, -20), new Vector(1, 2, 1), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("reflectedSphere", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
	
	 
}
