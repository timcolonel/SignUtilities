package com.timcolonel.SignUtilities.SignCommands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;


import com.timcolonel.SignUtilities.SUMinecart;
import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.Vector;
import com.timcolonel.SignUtilities.Response.TransactionResponse;

public class MinecartSign extends SignCommand
{

	public MinecartSign(SignUtilities instance, Player player, Sign sign, Block block) 
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
		
		//Create the block where the minecart will spawn or be remove, by default the sign
		Block positionBlock = sign.getBlock();
		String playerName = sign.getLine(1); 
		double cost = 0;
		try
		{
			cost = Integer.parseInt(sign.getLine(2));
		}
		catch (Exception e)
		{
			
		}
		
		Vector relative =  new Vector();
		if(sign.getLine(3) != "")
		{
			relative.extractVector(sign.getLine(3));
		}
		
		
		
		 int X = positionBlock.getX();
		 int Y = positionBlock.getY();
		 int Z = positionBlock.getZ();
		 Location minecartLocation = new Location(player.getWorld(), X + 0.5 + relative.x, Y + relative.y, Z + 0.5 + relative.z);
		 Block chestBlock = sign.getBlock().getRelative(0, -1, 0);
		 
		//Load the minecart at the spawnPostition to see if there is one
		SUMinecart m = plugin.minecartMgr.isMinecartAt(minecartLocation);
		
		
		if (m == null) //If there is no Minecart, continue the process of creating a minecart
		{	
			if (chestBlock.getType() == Material.CHEST) //Check if there is a chest containing the minecarts
			{
				//Check if there are some minecart remaining in the chest
				if (((Chest) chestBlock.getState()).getInventory().contains(Material.MINECART.getId())) 
				{
					if(cost > 0)
					{
						//Make the player pay the price of use.
						TransactionResponse response = plugin.pluginsMgr.ecoPay(player, playerName, cost);
						if(response.success())
						{ 
							//Spawn the minecart
							Minecart minecart = player.getWorld().spawn(minecartLocation, Minecart.class);
							
							//Create the signUtilities MinecartObject that will allow to the Minecart to go back to is initial chest when destroyed
							SUMinecart su_minecart = new SUMinecart(minecart, (Chest) chestBlock.getState(), plugin);
							plugin.minecartMgr.addMinecart(minecart, su_minecart);
						}
						else 
						{
							player.sendMessage(ChatColor.RED + response.getErrorMessage());
						}
					}
				}
				else
				{
					player.sendMessage(ChatColor.RED  + "No Minecat in chest");
				}	 
			}
			else
			{
				player.sendMessage(ChatColor.RED  + "No chest");
			}
			
		}
		else
		{
			plugin.minecartMgr.minecartDestroyed(m.getMinecart());
		}
		
		return true;
		
	}

}
