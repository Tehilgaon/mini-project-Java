package primitives;


public class Vector {
	Point3D _head;
	public Vector(Vector other) {
		_head=new Point3D(other._head);
	}
	public Vector(Point3D point) {
		if (point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Can not create a Zero vector");
		_head=new Point3D(point);
	}
	public Vector(Coordinate x,Coordinate y,Coordinate z) {
		Point3D point=new Point3D(x,y,z);
		if (point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Can not create a Zero vector");
		_head=point;
	}
	public Vector(double x,double y,double z) {
		if(x==0&&y==0&&z==0)
			throw new IllegalArgumentException("Can not create a Zero vector");
		_head=new Point3D(x,y,z);
	}
	public Vector subtract(Vector vector) {
		return new Vector(_head.subtract(vector._head));
	}
	public Vector add(Vector vector) {
		return new Vector(_head.add(vector));
	}
	public Vector scale(double scalar) {
		return new Vector(_head.mult(scalar));
	}
	public double dotProduct(Vector vector) {
		return vector._head._x._coord*_head._x._coord+
				vector._head._y._coord*_head._y._coord+
				vector._head._z._coord*_head._z._coord;
	}
	public Vector crossProduct(Vector vector) {
		return new Vector(_head._y._coord*vector._head._z._coord-_head._z._coord*vector._head._y._coord,
				_head._z._coord*vector._head._x._coord-_head._x._coord*vector._head._z._coord,
				_head._x._coord*vector._head._y._coord-_head._y._coord*vector._head._x._coord);
	}
	public double lengthSquared() {
		return this.dotProduct(this);
	}
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	public Vector normalize() {
		double length=this.length();
		_head=new Point3D(_head._x._coord/length,_head._y._coord/length,_head._z._coord/length);
		return this;
	}
	public Vector normalized() {
		Vector vec=new Vector(this);
		return vec.normalize();
	}
	public Point3D get() {
		return _head;
	}
	@Override
	public String toString() {
		return ""+_head.toString();
	}
	@Override
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(obj==null) return false;
		if(!(obj instanceof Vector)) return false;
		Vector oth=(Vector)obj;
		return _head.equals(oth._head);
	}

}
