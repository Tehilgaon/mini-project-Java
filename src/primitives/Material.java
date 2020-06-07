package primitives;


/**
 * Material class represents the material type of geometry -
 * contains parameters such as factor of transparency, reflection, gloss, color and so on.
 * @author Odel & Tehila
 */
public class Material 
{
	
	/**
	 * Factor of diffusion- the light scattering
	 */
	double _kD;
	
	
	/**
	 * Factor of specular
	 */
	double _kS;
	
	
	/**
	 * Factor of transparency
	 */
	double _kT;
	
	
	/**
	 * Factor of reflection
	 */
	double _kR;
	
	
	/**
	 * Factor of shininess
	 */
	int _nShininess;
	
	
	/**
	 * constructor #1
	 * @param kD
	 * @param kS
	 * @param n
	 * @param kT
	 * @param kR
	 */
	public Material(double kD, double kS, int n, double kT, double kR)
	{
		_kD=kD;
		_kS=kS;
		_nShininess=n;
		_kT=kT;
		_kR=kR;
	}
	
	
	/**
	 * constructor #2
	 * Initializes kT and kR with 0
	 * @param kD  
	 * @param kS
	 * @param n
	 */
	public Material(double kD, double kS, int n)
	{
		this(kD,kS,n,0,0);	
	}
	
	
	/**
	 * _kD getter
	 * @return
	 */
	public double getKD()
	{
		return _kD;
	}
	
	
	/**
	 * _kS getter
	 * @return
	 */
	public double getKS()
	{
		return _kS;
	}
	
	
	/**
	 * _kT getter
	 * @return
	 */
	public double getKT()
	{
		return _kT;
	}
	
	
	/**
	 * _kR getter
	 * @return
	 */
	public double getKR()
	{
		return _kR;
	}
	
	
	/**
	 * _nShininess getter
	 * @return
	 */
	public int getNShininess()
	{
		return _nShininess;
	}
	
	
	

}
