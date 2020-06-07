package primitives;

/**
 * Class Point3D contains 3 coordinates in 3D Cartesian coordinate
 * @author Odel & Tehila 
 *
 */
public class Point3D {
	
	Coordinate _x;
	Coordinate _y;
	Coordinate _z;
	
	/**
	 * Static field representing the zero vector
	 */
	public static Point3D ZERO=new Point3D(0,0,0);
	
	/**
	 * constructor #1
	 * @param x Coordinate value
	 * @param y Coordinate value
	 * @param z Coordinate value
	 */
	public Point3D(Coordinate x,Coordinate y,Coordinate z) 
	{
		_x=x;
		_y=y;
		_z=z;
	}
	
	/**
	 * constructor #2
	 * @param x _coord, double 
	 * @param y _coord, double
	 * @param z _coord, double
	 */
	public Point3D(double x,double y,double z) {
		_x=new Coordinate(x);
		_y=new Coordinate(y);
		_z=new Coordinate(z);
	}
	
	/**
	 * constructor #3 
	 * @param other
	 */
	public Point3D(Point3D other) {
		_x=new Coordinate(other._x);
		_y=new Coordinate(other._y);
		_z=new Coordinate(other._z);
	}
	
	/**
	 * subtract function subtracts a point from another point
	 * @param point Point3D
	 * @return Vector, the subtraction result
	 */
	public Vector subtract(Point3D point){
		return new Vector(_x._coord-point._x._coord,_y._coord-point._y._coord,_z._coord-point._z._coord);
	}
	
	/**
	 * add function adds a vector to a point
	 * @param Vector
	 * @return Point3D, the result of the point+vector  
	 */
	public Point3D add(Vector vector) {
		return new Point3D(_x._coord+vector._head._x._coord,
				_y._coord+vector._head._y._coord,_z._coord+vector._head._z._coord);
	}
	
	/**
	 * distanceSquared function calculates (the distance between the two points)^2
	 * @param point
	 * @return double 
	 */
	public double distanceSquared(Point3D point) {
		return (_x._coord-point._x._coord)*(_x._coord-point._x._coord)+
				(_y._coord-point._y._coord)*(_y._coord-point._y._coord)+
				(_z._coord-point._z._coord)*(_z._coord-point._z._coord);
	}
	
	/**
	 * distance function calculates the distance between two points, it uses the distanceSquared function
	 * @param Point3D point
	 * @return double, the distance
	 */
	public double distance(Point3D point) {
		return Math.sqrt(this.distanceSquared(point));
	}
	
	/**
	 * 
	 * @param double scalar
	 * @return Point3D
	 */
	public Point3D mult(double scalar) {
		return new Point3D(_x._coord*scalar,_y._coord*scalar,_z._coord*scalar);
	}
	
	/**
	 * Getters for the three coordinates
	 * @return Coordinate
	 */
	public double getX() {
		return _x.get();
	}
	
	
	public double getY() {
		return _y.get();
	}
	
	
	public double getZ() {
		return _z.get();
	}
	
	
	@Override
	public String toString() {
		return "( "+_x.toString()+","+_y.toString()+","+_z.toString()+" )";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(obj==null) return false;
		if(!(obj instanceof Point3D)) return false;
		Point3D oth=(Point3D)obj;
		return _x.equals(oth._x)&&_y.equals(oth._y)&&_z.equals(oth._z);
	}
}
		
	
	
	
