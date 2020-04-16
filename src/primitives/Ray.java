package primitives;

public class Ray {
	Point3D _p0;
	Vector _dir;
	public Ray(Point3D point, Vector dir) {
		_p0=new Point3D(point);
		_dir=new Vector(dir.normalized());
	}
	public Point3D getP0() {
		return _p0;
	}
	public Vector getDirection() {
		return _dir;
	}
	public Point3D getPoint(double t)
	{
		return _p0.add(_dir.scale(t));
	}

}
