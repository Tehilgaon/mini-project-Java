package elements;

import primitives.*;
/**
 * Camera's class contains the camera's location(Point3D) and three Vectors
 * @author Odel & Tehila
 *
 */
public class Camera {
	Point3D _p0;
	Vector _vUp;
	Vector _vTo;
	Vector _vRight;
	
	
	/**
	 * get Point3D p0, the camera's center point
	 * @return Point3D
	 */
	public Point3D getP0()
	{
		return _p0;
	}
	
	
	/**
	 * get Vector vUp
	 * @return Vector
	 */
	public Vector getvUp()
	{
		return _vUp;
	}
	
	
	/**
	 * get Vector vRight
	 * @return Vector
	 */
	public Vector getvRight()
	{
		return _vRight;
	}
	
	/**
	 * get Vector vTo, vTo aimed at the center of the view Plane
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
	 * 
	 * @param nX
	 * @param nY
	 * @param j
	 * @param i
	 * @param screenDistance
	 * @param screenWidth
	 * @param screenHeight
	 * @return
	 */
	public Ray constructRayThroughPixel (int nX, int nY,int j, int i, double screenDistance,
            double screenWidth, double screenHeight)
	{
		Point3D pc=_p0.add(_vTo.scale(screenDistance));
		double Ry=screenHeight/nY;
		double Rx=screenWidth/nX;
		double Yi=((i-((nY-1)/2.0)))*Ry;
		double Xj=((j-((nX-1)/2.0)))*Rx;
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
