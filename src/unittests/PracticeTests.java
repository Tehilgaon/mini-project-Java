package unittests;

 

 

import org.junit.jupiter.api.Test;
import elements.*;
 import geometries.*;
import primitives.*;
import scene.*;
import renderer.*;

class PracticeTests {

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	//@Test
	public void twoSpheresOnMirrors() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(Color.BLACK, 0.2));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.blue), new Material(0.4, 0.3, 100,1, 0), 25, new Point3D(30, -15, 50)),
				new Sphere(new Color(java.awt.Color.blue), new Material(0.4, 0.3, 100,1, 0), 25, new Point3D(-30, -15, 50)),
				new Sphere(new Color(java.awt.Color.white), new Material(0.5, 0.5, 100, 0.1,0.8), 15, new Point3D(30, -15, 50)),
				new Sphere(new Color(java.awt.Color.white), new Material(0.5, 0.5, 100,0.1,0.8), 15, new Point3D(-30, -15, 50))
				 
				);

		scene.addLights(new SpotLight(new Color(100, 600, 0),  new Point3D(-10, 20, -200), 
				   new Vector(0, 0, -1), 1, 0.0004, 0.0000006,5));

		ImageWriter imageWriter = new ImageWriter("stam", 2500, 2500, 500, 500);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
	 
	
	@Test
	public void demo() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, -30, -900), new Vector(0, 0, 1), new Vector(0, -1, 0),25));
		scene.setDistance(1000);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(0,0,0), 0.2));

		scene.addGeometries(
				new Sphere(new Color(java.awt.Color.white), new Material(0.4, 0.3, 100,0, 0), 2.5, new Point3D(-100, -1200, 300)),
				new Sphere(new Color(java.awt.Color.white), new Material(0.4, 0.3, 100,0, 0), 2.5, new Point3D(70, -1000, 250)),
				new Sphere(new Color(java.awt.Color.white), new Material(0.4, 0.3, 100,0, 0), 2.5, new Point3D(-800, -1100, 500)),
				new Sphere(new Color(java.awt.Color.white), new Material(0.4, 0.3, 100,0, 0), 3.5, new Point3D(-250, -1500, 300)),
				new Sphere(new Color(java.awt.Color.white), new Material(0.4, 0.3, 100,0, 0), 2.5, new Point3D(200, -1700, 260)),
				new Sphere(new Color(java.awt.Color.white), new Material(0.4, 0.3, 100,0, 0), 2.5, new Point3D(800, -1600, 700)),
				
				new Sphere(new Color(java.awt.Color.white), new Material(0.4, 0.3, 100,0, 0), 3.5, new Point3D(-950, -450, 400)),
				new Sphere(new Color(java.awt.Color.white), new Material(0.4, 0.3, 100,0, 0), 3.5, new Point3D(-350, -700, 400)),
				new Sphere(new Color(java.awt.Color.white), new Material(0.4, 0.3, 100,0, 0), 3.5, new Point3D(700, -800, 400)),
				
				new Sphere(new Color(255,255,255), new Material(0.4, 0.8, 100,1, 0), 450, new Point3D(0, -50, 200))
				
				,new Plane(new Material(0.8, 0.4, 200,0,0.25),new Color(2,4,43),new Point3D(600,-50, 200), new Point3D(-600,-50, 200),new Point3D(-600,-40, 0 ))
				,new Plane(new Material(0.8, 0.4, 200,0,0.25),new Color(2,4,43),new Point3D(600,-25, 200), new Point3D(-600,-25, 200),new Point3D(-600,-25, 0 ))
				
				,new Triangle(Color.BLACK, new Material(0.2, 0.2, 30,  0 , 0), //
						new Point3D(-100, -50, -670), new Point3D(-75, -50, -670), new Point3D(-75, 0, -670)) //
				,new Polygon(new Material(0.2, 0.2, 30,  0 , 0),Color.BLACK,  //
						new Point3D(-75, -50, -670), new Point3D(-75, 0, -670),new Point3D(50, 0, -670),new Point3D(50, -50, -670))
				,new Triangle(Color.BLACK, new Material(0.2, 0.2, 30,  0 , 0), //
						new Point3D(50, 0, -670),new Point3D(50, -50, -670), new Point3D(75, -50, -670)), //
				
				new Sphere(Color.BLACK, new Material(0.2, 0.2, 30,  0 , 0), 5, new Point3D(50, -70, -670)),
				new Polygon(new Material(0.2, 0.2, 30,  0 , 0),Color.BLACK,  
						new Point3D(53, -65, -670), new Point3D(47, -65, -670),new Point3D(44, -50, -670),new Point3D(56 ,-50, -670)),
				new Polygon(new Material(0.2, 0.2, 30,  0 , 0),Color.BLACK,  
						new Point3D(44, -50, -620), new Point3D(44, -49, -620),new Point3D(-75, -100, -620),new Point3D(-75 ,-101, -620)),
				new Polygon(new Material(0.2, 0.2, 30,  0 , 0),new Color(java.awt.Color.GRAY),  
						new Point3D(-75 ,-101, -620), new Point3D(-74 ,-101, -620),new Point3D(-74 ,-50, -620),new Point3D(-75 ,-50, -620))
				);

		scene.addLights(new PointLight(new Color(150, 100, 350),  new Point3D(0, -650, 200), 1, 0.0004, 0.0000006),
				new SpotLight(new Color(500, 300, 0),  new Point3D(0, -100, 0), 
				  new Vector(0, 0.1, -1), 1, 0.0004, 0.0000006,11)
				,new SpotLight(new Color(600, 300, 0),  new Point3D(25, -100, -700), 
						  new Vector(0, 1, 0), 1, 0.0004, 0.0000006,5)
				);

		ImageWriter imageWriter = new ImageWriter("stam", 2500, 2500, 500, 500);
		Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

		render.renderImage();
		render.writeToImage();
	}
	
	
	//@Test
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
				,new Sphere(new Color(java.awt.Color.GREEN), new Material(0.2, 0.2, 30, 0.6, 0), // 
						12, new Point3D(0, 0, 200))
				 
		);

		scene.addLights(new SpotLight(new Color(200, 150, 150), //
				new Point3D(100, -40, 40), new Vector(-1, 0, 0), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("sphere inside a pyramid", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}
	
	//@Test
	public void mp1() {
		Scene scene = new Scene("Test scene");
		scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
		scene.setDistance(1100);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.addGeometries(
				new Sphere(new Color(68,0,0), new Material(0.2, 0.2, 30, 0.2, 0), // 
						40, new Point3D(0, -10, 150)),
				
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(0, -51, 150)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(0, -57, 150)),
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(-5, 31, 150)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(-7, 37, 150)),
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(42, -10, 150)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(47, -10, 150)),
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(-42, -10, 150)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(-47, -10, 150)),
				
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(0, -10, 110)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(0, -10, 104)),
				
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(22, -15, 104)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(23, -15, 102)),
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(-22, 0, 104)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(-22, 0, 99)),
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(22, 15, 104)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(22, 16, 99)),
				
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(-22, -35, 160)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(-22, -35, 120)),
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(32, -31, 130)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(37, -34, 128)),
				
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(-10, -15, 191)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
						4.5, new Point3D(-13, -18, 196)),
				new Sphere(new Color(java.awt.Color.green), new Material(0.2, 0.2, 30, 0, 0), // 
						2, new Point3D(27, 10,296)),
				new Sphere(new Color(20,0,0), new Material(0.2, 0.2, 30, 0, 0), // 
					4.5, new Point3D(32, 14, 202)),
				new Plane(new Material(0.5, 0.6, 60,0,0.8),new Color(java.awt.Color.BLACK),//
						new Point3D(20, 80, 350), new Point3D(-20, 80,350), new Point3D(-20, 50, 350))
				 
				
				 
		);

		scene.addLights(new SpotLight(new Color(200, 150, 150), //
				new Point3D(100, -40, 40), new Vector(-1, 0, 0), 1, 4E-5, 2E-7));

		ImageWriter imageWriter = new ImageWriter("mp1", 200, 200, 600, 600);
		Render render = new Render(imageWriter, scene);

		render.renderImage();
		render.writeToImage();
	}

	
	 
}