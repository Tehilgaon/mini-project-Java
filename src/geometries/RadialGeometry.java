package geometries;

public abstract class RadialGeometry implements Geometry{
	protected double _radius;
	
	public RadialGeometry(double radius) {
		_radius=radius;
	}
	
	public RadialGeometry(RadialGeometry other) {
		_radius=other._radius;
	}
	
	public double get() {
		return _radius;
	}

}
