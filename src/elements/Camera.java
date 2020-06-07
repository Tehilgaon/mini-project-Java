package elements;

import primitives.*;


/**
 * Camera's class contains the camera's location(Point3D) and three Vectors
 * @author Odel & Tehila
 *
 */
public class Camera {
	
	
	/**
	 * camera's location(Point3D)
	 */
	Point3D _p0;
	
	
	/**
	 * Up vector 
	 */
	Vector _vUp;
	
	
	/**
	 * Vector toward the scene, aimed at the center of the view Plane
	 */
	Vector _vTo;
	
	
	/**
	 * Right vector 
	 */
	Vector _vRight;
	
	
	/**
	 * p0 getter
	 * @return Point3D
	 */
	public Point3D getP0()
	{
		return _p0;
	}
	
	
	/**
	 * vUp getter
	 * @return Vector
	 */
	public Vector getvUp()
	{
		return _vUp;
	}
	
	
	/**
	 * vRight getter
	 * @return Vector
	 */
	public Vector getvRight()
	{
		return _vRight;
	}
	
	
	/**
	 * vTo getter
	 * @return Vector
	 */
	public Vector getvTo()
	{
		return _vTo;
	}
	
	
	/**
	 * Constructor, Normalizes both vectors and calculates the third one
	 * @param p0 - the Camera's location
	 * @param vTo- Vector
	 * @param vUp- Vector
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp)
	{
		if(Util.isZero(vTo.dotProduct(vUp))) //checking the vectors are orthogonal to each other
		{
			_p0=p0;
			_vUp=vUp.normalized();
			_vTo=vTo.normalized();
			_vRight=vTo.crossProduct(vUp);	//the third vector, who orthogonal to both of them
		}		
	}
	
	
	/**
	 * Calculates a ray through each of the pixels on the view plane
	 * @param nX number of pixels in X axis
	 * @param nY number of pixels in Y axis
	 * @param j the column index (With i define a specific pixel)
	 * @param i the row index (With j define a specific pixel)
	 * @param screenDistance between the camera and the viewPlane
	 * @param screenWidth
	 * @param screenHeight
	 * @return new Ray object which is the ray through a specific index  
	 */
	public Ray constructRayThroughPixel (int nX, int nY,int j, int i, double screenDistance,
            double screenWidth, double screenHeight)
	{
		Point3D pc=_p0.add(_vTo.scale(screenDistance));
		
		double Ry=screenHeight/nY;
		double Rx=screenWidth/nX;
		
		double Yi=((i - ((nY - 1) / 2.0)))*Ry;
		double Xj=((j - ((nX - 1) / 2.0)))*Rx;
		
		Point3D Pij=pc; 
		
		if(Yi!=0 && Xj==0)
			Pij=pc.add(_vUp.scale(-1*Yi));
		
		if(Yi==0&& Xj!=0)
			Pij=pc.add(_vRight.scale(Xj));
		
		if(Yi!=0 && Xj!=0)
			Pij=pc.add(_vRight.scale(Xj).subtract(_vUp.scale(Yi)));
		
		Vector Vij=Pij.subtract(_p0);
		
		Ray ray=new Ray(_p0,Vij);
		return ray;
	}

}
