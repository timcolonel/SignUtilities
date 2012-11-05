package com.timcolonel.SignUtilities.Listeners;

import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import com.timcolonel.SignUtilities.SignUtilities;

public class SUVehicleListener implements Listener //extends VehicleListener 
{

	public static SignUtilities plugin;
	
	public SUVehicleListener(SignUtilities instance) 
	{
		plugin = instance;
	}
	
	@EventHandler
	public void onVehicleDestroy (VehicleDestroyEvent event)
	{
		try
		{
			Minecart m = (Minecart) event.getVehicle();
			if(plugin.minecartMgr.minecarts.containsKey(m))
			{
				
				event.setCancelled(true);
				plugin.minecartMgr.minecartDestroyed(m);
			}
		}
		catch (Exception e)
		{
			
		}

		
	}
	
	@EventHandler
	public void onVehicleEnter(VehicleEnterEvent event)
	{
		
	}
	
	@EventHandler
	public void onVehicleDamage(VehicleDamageEvent event)
	{
		try
		{
			Minecart m = (Minecart) event.getVehicle();
			if(plugin.minecartMgr.minecarts.containsKey(m))
			{
				m.setVelocity(	event.getAttacker().getLocation().getDirection()); 
			}
		}
		catch (Exception e)
		{
			
		}
	
	}
	
}
