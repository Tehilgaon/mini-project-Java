package elements;

import primitives.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;



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
	 * 
	 */
	int _numRays;
	
	
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
			_numRays=1;
		}		
	}
	
	
	public Camera(Point3D p0, Vector vTo, Vector vUp, int numRays)
	{
		this(p0, vTo, vUp);
		_numRays= numRays;
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
		
		Vector Vij=Pij.subtract(_p0).normalized();
		
		Ray ray=new Ray(_p0,Vij);
		return ray;
	}
	
	
	/**
	 * super sampling, make sure the geometries look smoother and less pixelated
	 * Returns a list of rays from the camera through one pixel
	 * @param nX
	 * @param nY
	 * @param j
	 * @param i
	 * @param screenDistance
	 * @param screenWidth
	 * @param screenHeight
	 * @return list of rays
	 */
	public List<Ray> constructBeamRayThroughPixel(int nX, int nY,int j, int i, double screenDistance,
            double screenWidth, double screenHeight)
	{
		Ray mainRay = constructRayThroughPixel(nX,nY, j, i, screenDistance, screenWidth, screenHeight);
		List<Ray> listOfRays= new ArrayList<Ray>();
		listOfRays.add(mainRay);
		if(_numRays == 1)
			return listOfRays;
		
		Point3D mainPoint = mainRay.getPoint(screenDistance);
		double Ry=screenHeight/nY;
		double Rx=screenWidth/nX;
		
		for(int k=0;k<_numRays;k++)
		{
			Random rand=new Random();
			double randX =  (Rx)*rand.nextDouble() - (Rx/2);
		 	double randY =  (Ry)*rand.nextDouble() - (Ry/2);
		 	Vector d = _vRight.scale(randX).add(_vUp.scale(randY));	 	
			Point3D point= mainPoint.add(d);
			Vector v = point.subtract(_p0).normalized();
			listOfRays.add(new Ray(_p0,v));
		}
		
		return listOfRays;
		
	}

	
	public List<Point3D> constructCornersThroughPixel (int nX, int nY,int j, int i, double screenDistance,
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
		
		Point3D ul= Pij.add(_vUp.scale(Ry/2).add(_vRight.scale(-Rx/2)));
		Point3D ur= Pij.add(_vUp.scale(Ry/2).add(_vRight.scale(Rx/2)));
		Point3D dl= Pij.add(_vUp.scale(-Ry/2).add(_vRight.scale(-Rx/2)));
		Point3D dr= Pij.add(_vUp.scale(-Ry/2).add(_vRight.scale(Rx/2)));
		
		return List.of(ul,ur,dl,dr);
	}
}
