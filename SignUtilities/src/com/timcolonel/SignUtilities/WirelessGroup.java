package com.timcolonel.SignUtilities;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class WirelessGroup 
{
	public Block activator;
	public Block sign;
	
	public WirelessGroup() 
	{
		activator = null;
		sign = null;
	}
	
	public WirelessGroup(Block a, Block s) 
	{
		activator = a;
		sign = s;
	}
	
	public WirelessGroup(Selection sel) 
	{
		activator = null;
		sign = null;
		
		fromSelection(sel);
	}
	
	public boolean fromSelection(Selection sel)
	{
		Location l1 = sel.getPointA();
		Location l2 = sel.getPointB();
	
		Block b1 = l1.getBlock();
		Block b2 = l2.getBlock();
		
		
		if(Utils.isASign(b1) && Utils.isAnActivator(b2))
		{
			sign = b1;
			activator = b2;
		}
		else if(Utils.isASign(b2) && Utils.isAnActivator(b1))
		{
			sign = 	b2;
			activator = b1;
		}
		else
		{
			return false;
		}
		return true;
	
	}
}
