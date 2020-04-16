package geometries;
import java.util.*;  
import primitives.*;


public interface Intersectable {
	List<Point3D> findIntersections(Ray ray);
}
