package primitives;

public class Ray {
	Point3D _p0;
	Vector _dir;
	public Ray(Point3D point, Vector dir) {
		_p0=new Point3D(point);
		_dir=new Vector(dir);
	}
	public Point3D getP0() {
		return _p0;
	}
	public Vector getDirection() {
		return _dir;
	}

}
