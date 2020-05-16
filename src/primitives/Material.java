package primitives;

public class Material 
{
	double _kD;
	
	double _kS;
	
	int _nShininess;
	
	public Material(double kD, double kS, int n)
	{
		_kD=kD;
		_kS=kS;
		_nShininess=n;	
	}
	
	public double getKD()
	{
		return _kD;
	}
	
	public double getKS()
	{
		return _kS;
	}
	
	public int getNShininess()
	{
		return _nShininess;
	}
	
	
	

}
