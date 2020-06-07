package primitives;

/**
 * class Vector contains a Point3D  and it represents the distance and direction of
 *  that point from the point (0,0,0)
 * @author Odel & Tehila
 *
 */
public class Vector {
	
	/**
	 * Point3D 
	 */
	Point3D _head;
	
	
	/**
	 * Constructor #1 - copy constructor
	 * @param other Vector
	 */
	public Vector(Vector other) {
		this(other._head);
	}
	
	
	/**
	 * Constructor #2 
	 * @param point Point3D
	 * @throws IllegalArgumentException if trying to create a Zero vector
	 */
	public Vector(Point3D point) {
		if (point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Can not create a Zero vector");
		_head=new Point3D(point);
	}
	
	
	/**
	 * Constructor #3
	 * @param x Coordinate
	 * @param y Coordinate
	 * @param z Coordinate
	 */
	public Vector(Coordinate x,Coordinate y,Coordinate z) {
		this(new Point3D(x,y,z));
	}
	
	
	/**
	 * Constructor #4
	 * @param x double
	 * @param y double
	 * @param z double
	 */
	public Vector(double x,double y,double z) {
		this(new Point3D(x,y,z));
	}
	
	
	/**
	 * Subtracts two vectors and returns a vector
	 * @param vector 
	 * @return new vector, the result of subtraction
	 */
	public Vector subtract(Vector vector) {
		return new Vector(_head.subtract(vector._head));
	}
	
	
	/**
	 * Adds two vectors and returns the result
	 * @param vector
	 * @return new vector, the result of the adding
	 */
	public Vector add(Vector vector) {
		return new Vector(_head.add(vector));
	} 
	
	
	/**
	 * Multiplies the vector by scalar
	 * @param scalar
	 * @return new vector
	 */
	public Vector scale(double scalar) {
		return new Vector(_head.mult(scalar));
	}
	
	
	/**
	 * Calculates the dotProduct between two vectors x*x+y*y+z*z
	 * @param vector
	 * @return a scalar x*x+y*y+z*z (double)
	 */
	public double dotProduct(Vector vector) {
		return vector._head._x._coord*_head._x._coord+
				vector._head._y._coord*_head._y._coord+
				vector._head._z._coord*_head._z._coord;
	}
	
	
	/**
	 * Calculates the crossProduct between two vectors 
	 * @param the second vector
	 * @return new vector (y*z-z*y,z*x-x*z,x*y-y*x)
	 */
	public Vector crossProduct(Vector vector) {
		return new Vector(_head._y._coord*vector._head._z._coord-_head._z._coord*vector._head._y._coord,
				_head._z._coord*vector._head._x._coord-_head._x._coord*vector._head._z._coord,
				_head._x._coord*vector._head._y._coord-_head._y._coord*vector._head._x._coord);
	}
	
	
	/**
	 * Calculates the square of the vector length
	 * @return the length^2 (double)
	 */
	public double lengthSquared() {
		return this.dotProduct(this);
	}
	
	
	/**
	 * Calculates the vector length
	 * @return the length (double)
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}
	
	
	/**
	 * Changes the vector to a length1 vector
	 * @throws an ArithmeticException if length == 0
	 * @return the same vector after normalization
	 */
	public Vector normalize() {
		double length=this.length();
		if(length == 0)
			throw new ArithmeticException("Divide by Zero");
		_head=new Point3D(_head._x._coord/length,_head._y._coord/length,_head._z._coord/length);
		return this;
	}
	
	
	/**
	 * Calculates a new vector in the same direction of the given vector but of length1
	 * @return new normalized vector
	 */
	public Vector normalized() {
		Vector vec=new Vector(this);
		return vec.normalize();
	}
	
	
	/**
	 * _head getter
	 * @return
	 */
	public Point3D get() {
		return _head;
	}
	
	
	
	@Override
	public String toString() {
		return _head.toString();
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
