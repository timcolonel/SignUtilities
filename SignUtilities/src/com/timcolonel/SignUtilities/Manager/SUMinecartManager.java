package com.timcolonel.SignUtilities.Manager;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Minecart;

import com.timcolonel.SignUtilities.SUMinecart;
import com.timcolonel.SignUtilities.SignUtilities;

public class SUMinecartManager
{
	public static SignUtilities plugin;
	public final HashMap<Minecart, SUMinecart> minecarts = new HashMap<Minecart, SUMinecart>();
	public SUMinecartManager(SignUtilities instance)
	{
		plugin = instance;
	}
	
	public SUMinecart isMinecartAt(Location l)
	{

		for (Minecart m: minecarts.keySet()) 
		{
			if(l.distance(m.getLocation()) < 1)
			{
				return minecarts.get(m);
				
			}
			
		}
		
		return null;
	}
	
	public void minecartDestroyed(Minecart m)
	{
		minecarts.get(m).minecartDestroyed(true); //Add a new minecart item in the native chest
		minecarts.remove(m); //RTemove the minecart from the SU minecart slist
		m.remove(); //Destroy the entity
	}
	
	public boolean addMinecart(Minecart minecart, SUMinecart su_minecart)
	{
		minecarts.put(minecart, su_minecart);
		return true;
	}
}

