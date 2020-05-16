package scene;
import primitives.*;
import elements.*;
import geometries.*;
import java.util.List;
import java.util.LinkedList;

 
/** 
 * The class holds variables for the scene, the background color,
 * the geometries involved (a Geometries object), a Camera object,
 * and the ambient light of the scene
 * @author Odel & Tehila
 *
 */
public class Scene {
	
	/**
	 * The scene's name
	 */
	String _name;
	
	/**
	 * new Color object that is the background color of the scene
	 */
	Color _background;
	
	/**
	 * new AmbientLight object, the AmbientLight's intensity of this scene
	 */
	AmbientLight _ambientLight;
	
	/**
	 * new Geometries object which contains a list of geometries
	 */
	Geometries _geometries;
	
	/**
	 * new Camera object for the scene
	 */
	Camera _camera;
	
	/**
	 * List of light sources
	 */
	List<LightSource> _lights;
	
	/**
	 * The distance between the camera ang the viewPlane (double)
	 */
	double _distance;
	
	/**
	 * constructor. It initializes the Geometries object and the lights List
	 * @param name scene's name
	 */
	public Scene(String name)
	{
		_name=name;
		_geometries=new Geometries();
		_lights= new LinkedList<LightSource>();
	}
	
	/**
	 * _name's getter
	 * @return scene nama (string)
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * _background getter
	 * @return background color (Color)
	 */
	public Color getBackground()
	{
		return _background;
	}
	
	/**
	 * _ambientLight getter
	 * @return the ambientLight (AmbientLight)
	 */
	public AmbientLight getAmbientLight()
	{
		return _ambientLight;
	}
	
	/**
	 * _geometries getter
	 * @return list of geometries (Geometries)
	 */
	public  Geometries getGeometries()
	{
		return _geometries;
	}
	
	/**
	 * _camera getter
	 * @return the camera (Camera)
	 */
	public Camera getCamera()
	{
		return _camera;
	}
	
	/**
	 * _distance getter
	 * @return the distance between the camera and the viewPlane (double)
	 */
	public double getDistance()
	{
		return _distance;
	}
	
	/**
	 * _lights getter
	 * @return the list of the light sources in the scene 
	 */
	public List<LightSource> getLights()
	{
		return _lights;
	}
	
	/**
	 * background setter
	 * @param background color
	 */
	public void setBackground(Color background)
	{
		_background=background;
	}
	
	/**
	 * _ambientLight setter
	 * @param ambientLight
	 */
	public void setAmbientLight(AmbientLight ambientLight)
	{
		_ambientLight=ambientLight;
	}
	
	/**
	 * _camera setter
	 * @param camera
	 */
	public void setCamera(Camera camera)
	{
		_camera=camera;
	}
	
	/**
	 * _distance setter
	 * @param distance between the camera and the viewPlane
	 */
	public void setDistance(double distance)
	{
		_distance=distance;
	}
	
	/**
	 * Adding geometries to the geometries's list by calling to Geometries's add()
	 * @param geometries a Geometries object which contains a list of geometries
	 */
	public void addGeometries(Intersectable... geometries)
	{
		_geometries.add(geometries);
	}
	
	/**
	 * adding light sources to the _light list
	 * @param lights- light Sources 
	 */
	public void addLights(LightSource... lights)
	{
		_lights.addAll(List.of(lights));
	}
	
	
	
	

}
