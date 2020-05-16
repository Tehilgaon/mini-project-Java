package geometries;

import primitives.*;
 
 

public abstract class RadialGeometry extends Geometry{
	protected double _radius;
	
	public RadialGeometry(double radius) {
		_radius=radius;
	}
	
	public RadialGeometry(Color color,double radius) {
		super(color);
		_radius=radius;
	}
	
	public RadialGeometry(Material material,Color color,double radius) {
		super(color, material);
		_radius=radius;
	}
	
	
	public RadialGeometry(RadialGeometry other) {
		_radius=other._radius;
	}
	
	public double get() {
		return _radius;
	}
	
	 

}
