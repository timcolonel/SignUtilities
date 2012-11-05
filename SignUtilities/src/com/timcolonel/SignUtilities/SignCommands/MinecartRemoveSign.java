package com.timcolonel.SignUtilities.SignCommands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SUMinecart;
import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.Vector;

public class MinecartRemoveSign extends SignCommand
{

	public MinecartRemoveSign(SignUtilities instance, Player player, Sign sign, Block block) 
	{
		super(instance, player, sign, block);
		this.permission = "signutils.minecart.use";
	}
	
	@Override
	public boolean run()
	{
		if (!hasPermission()) 
		{
			return true;
		}
		//Create the block where the minecart will be remove, by default the sign
		Block positionBlock = sign.getBlock();
	
		Vector relative =  new Vector();
		if(sign.getLine(3) != "")
		{
			relative.extractVector(sign.getLine(3));
		}
 
		int X = positionBlock.getX();
		int Y = positionBlock.getY();
		int Z = positionBlock.getZ();
		Location minecartLocation = new Location(player.getWorld(), X + 0.5 + relative.x, Y + relative.y, Z + 0.5 + relative.z);
		 
		//Load the minecart at the spawnPostition to see if there is one
		SUMinecart m = plugin.minecartMgr.isMinecartAt(minecartLocation);
		if (m != null)
		{			
			plugin.minecartMgr.minecartDestroyed(m.getMinecart());
			return true;
		}
		else return false;
	}
}
