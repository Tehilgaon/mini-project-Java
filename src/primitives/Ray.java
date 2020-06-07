package primitives;

/**
 * Ray class represents a vector with a location point
 * it contains a Vector and a Point3D
 * @author Odel & Tehila 
 */
public class Ray {
	
	/**
	 * Delta scalar, for moving the ray position
	 */
	private static final double DELTA = 0.1;

	
	/**
	 * Point3D
	 */
	Point3D _p0;
	
	
	/**
	 * Vector
	 */
	Vector _dir;
	
	
	/**
	 * Constructor #1
	 * @param point (Point3D)
	 * @param dir (Vector)
	 */
	public Ray(Point3D point, Vector dir) {
		_p0=new Point3D(point);
		_dir=new Vector(dir.normalized());
	}
	
	
	/**
	 * constructor #2 rayPosition+Delta
	 * @param point (Point3D)
	 * @param dir (Vector)
	 * @param n normal (Vector)
	 */
	public Ray(Point3D point, Vector dir,Vector n)
	{
		//point + normal.scale(±DELTA)
		_p0 = point.add(n.scale(n.dotProduct(dir) > 0 ? DELTA : - DELTA));
		
		_dir=dir.normalize();
	}
	
	
	/**
	 * _p0 getter
	 * @return Point3D
	 */
	public Point3D getP0() {
		return _p0;
	}
	
	
	/**
	 * _dir getter
	 * @return
	 */
	public Vector getDirection() {
		return _dir;
	}
	
	
	/**
	 * Calculates the point reached after multiplying the ray length by t
	 * @param t scalar, represents the length to reach the point 
	 * @return new Point 
	 */
	public Point3D getPoint(double t)
	{
		//p =point.add(direction*length)
		try {
			Point3D p= _p0.add(_dir.scale(t));
			return p;
		}
		//return the same point if the _dir's scaling creates a Zero vector
		catch(Exception e) { 
			return _p0;
		}
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(obj==null) return false;
		if(!(obj instanceof Ray)) return false;
		Ray oth=(Ray)obj;
		return (_p0.equals(oth.getP0())&&_dir.equals(oth.getDirection()));
		
		
	}
	
	
	@Override
    public String toString() {
        return "point: " + _p0 + ", direction: " + _dir;
    }

}
