package primitives;

public class Material 
{
	double _kD;
	
	double _kS;
	
	double _kT;
	
	double _kR;
	
	int _nShininess;
	
	public Material(double kD, double kS, int n)
	{
		this(kD,kS,n,0,0);	
	}
	
	public Material(double kD, double kS, int n, double kT, double kR)
	{
		_kD=kD;
		_kS=kS;
		_nShininess=n;
		_kT=kT;
		_kR=kR;
	}
	
	public double getKD()
	{
		return _kD;
	}
	
	public double getKS()
	{
		return _kS;
	}
	
	public double getKT()
	{
		return _kT;
	}
	
	public double getKR()
	{
		return _kR;
	}
	
	public int getNShininess()
	{
		return _nShininess;
	}
	
	
	

}
