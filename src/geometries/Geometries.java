package geometries;

import java.util.List;
import java.util.ArrayList;

import primitives.*;
 

public class Geometries implements Intersectable {

	List<Intersectable> _geometries;
	
	public Geometries()
	{
		_geometries= new ArrayList<Intersectable>();
	}
	
	public Geometries(Intersectable... geometries)
	{
		_geometries=List.of(geometries);
	}
	
	public void add(Intersectable... geometries)
	{
		_geometries.addAll(List.of(geometries));
	}
	 

	@Override
	public List<Point3D> findIntersections(Ray ray) {
		List<Point3D> list=new ArrayList<Point3D>();
		for(int i=0;i<_geometries.size();i++)
		{
			List<Point3D> geometryList=_geometries.get(i).findIntersections(ray);
			if(geometryList!=null)
				list.addAll(geometryList);
		}
		if(list.size()!=0)
			return list;
		return null;
	}

}
