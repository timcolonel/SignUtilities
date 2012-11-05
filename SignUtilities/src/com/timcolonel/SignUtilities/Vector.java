package com.timcolonel.SignUtilities;

public class Vector {

	public double x;
	public double y;
	public double z;
	public Vector()
	{
		x = 0;
		y = 0;
		z = 0;

	}
	
	public Vector(double px, double py, double pz)
	{
		x = px;
		y = py;
		z = pz;
	}
	
	public void extractVector(String s)
	{
		String[] valueStr = s.split(";");
		try
		{
			x = Integer.parseInt(valueStr[0]);
			y = Integer.parseInt(valueStr[1]);
			z = Integer.parseInt(valueStr[2]);
		}
		catch (Exception e)
		{
			
		}
	}
}
