package com.timcolonel.SignUtilities.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.Utils;

public class SetColorHandler extends CommandHandler
{
	
	public SetColorHandler(SignUtilities instance, CommandSender sender, String[] args) 
	{
		super(instance, sender, args);
		permission = "signutils.setcolor";
	}
	
	@Override
	public boolean execute() 
	{
		
		// If there is no args, set all the lines colors to black		

		if (!hasPermission()) 
		{
			return false;
		}
				
				
		if(plugin.signSelMgr.hasPlayerSelected((Player) sender))
		{
			//Check if the sign is there
			if (Utils.isASign(plugin.signSelMgr.getSelection((Player) sender)))
			{
				//Check if the player is trying to edit a sign in a protected region
				if(!plugin.pluginsMgr.canEditSign((Player) sender))
				{
					return true;
				}
				if(args.length == 0)
				{
					if(plugin.colorsMgr.colorSign((Sign) plugin.signSelMgr.getSelection((Player) sender).getState(), "BLACK", 0))
					{
						sender.sendMessage(ChatColor.BLUE + "Sign color reset to black");
							return true;
					}
					else
					{				
						return false;
					}
				}
				else if(args.length == 1) //If there is only one set all the line to the color specified
				{
					if(plugin.colorsMgr.colorSign((Sign) plugin.signSelMgr.getSelection((Player) sender).getState(), args[0].toUpperCase(), 0))
					{
						sender.sendMessage(ChatColor.BLUE + "Sign color set to " + args[0].toUpperCase() );
					return true;
				}
				else
				{	
					sender.sendMessage(ChatColor.RED + "This color doesn't exist:" + args[0].toUpperCase() );
						return false;
					}
				
				
				}
				else if (args.length == 2)
				{
					try 
					{
						int l = Integer.parseInt(args[1]);
					
						if (l < 0 || l > 4 ) //Check if the line number is real
					{
						sender.sendMessage(ChatColor.RED + "There is only four lines on a sign");
						return false;
					}
				
					if(plugin.colorsMgr.colorSign((Sign) plugin.signSelMgr.getSelection((Player) sender).getState(), args[0].toUpperCase(), l))
					{
						sender.sendMessage(ChatColor.BLUE + "Set line " +args[1]+   " in "+ args[0].toUpperCase() );
						return true;
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "This color doesn't exist: " + args[0].toUpperCase() );
						return false;
					}	
				}
				catch (Exception e)
				{
					sender.sendMessage(ChatColor.RED + "Must be color,line" );
						return false;
					}
				}
				else //Mean there is too much args
				{
					sender.sendMessage(ChatColor.RED + "Too much args");
						return false;
					}
				}
				else //Mean there is too much args
				{
				
					sender.sendMessage(ChatColor.RED + "No sign selected (The sign may have been removed)");
						return false;
				}
				
				}
				else
				{
				sender.sendMessage(ChatColor.RED + "No sign selected (The sign may have been removed)");
				return false;
				}		

	}
	

}
