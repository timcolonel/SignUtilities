package com.timcolonel.SignUtilities;

import org.bukkit.Location;
import org.bukkit.World;

public class Selection 
{
	Location pointA;
	Location pointB;
	World world;
	
	public Selection()
	{
		pointA = null;
		pointB = null;
		world = null;
	}
	
	public Selection(Location a, Location b)
	{
		pointA = a;
		pointB = b;
		world = pointA.getWorld();
	}
	
	public void setPointA(Location a)
	{
		pointA = a;
	}
	
	public void setPointB(Location b)
	{
		pointB = b;
	}
	
	public void setWorld(World w)
	{
		world = w;
	}
	
	public Location getPointA()
	{
		return pointA;
	}
	
	public Location getPointB()
	{
		return pointB;
	}
	
	public World getWorld()
	{
		return world;
	}
}
