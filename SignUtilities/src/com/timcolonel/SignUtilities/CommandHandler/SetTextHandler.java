package com.timcolonel.SignUtilities.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SUEditor;
import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.Utils;

public class SetTextHandler extends CommandHandler
{
	
	public SetTextHandler(SignUtilities instance, CommandSender sender, String[] args) 
	{
		super(instance, sender, args);
		permission = "signutils.settext";
	}
	
	@Override
	public boolean execute() 
	{
		if (!hasPermission()) 
		{
			return false;
		}
		
		if(plugin.signSelMgr.hasPlayerSelected((Player) sender))
		{
			
			if(args.length == 0)
			{
				sender.sendMessage(ChatColor.RED + "Too few arguments");
				return false;
			}
			else 
			{		
				if (Utils.isASign(plugin.signSelMgr.getSelection((Player) sender)))
				{
					//Check if the player is trying to edit a sign in a protected region
					if(!plugin.pluginsMgr.canEditSign((Player) sender))
					{
						return true;
					}
					
					try 
					{
						int l = Integer.parseInt(args[0]);
						if (l < 0 || l > 4 ) //Check if the line number is real
						{
							sender.sendMessage(ChatColor.RED + "There is only four lines on a sign");
							return false;
						}
					
						String text = "";
						for(int i = 1; i <args.length; i++)
						{
							
							text += args[i] + " ";	
						}
						
						if(SUEditor.changeText((Sign) plugin.signSelMgr.getSelection((Player) sender).getState(), text, l - 1, (Player) sender))
						{	
							sender.sendMessage(ChatColor.BLUE + "Set line " +args[0]+   " with the text '"+ text + "'");
							return true;
						}
			
					}
					catch (Exception e) 
					{
						//Get all the text form the args
						String text = "";
						for(int i = 0; i <args.length; i++)
						{
							text += args[i] + " ";	
						}
						
						if(SUEditor.changeText((Sign) plugin.signSelMgr.getSelection((Player) sender).getState(), text, 0, (Player) sender))
						{
							sender.sendMessage(ChatColor.BLUE + "Set text sucessfully");
							return true;
						}
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "No sign selected (The sign may have been removed)");
					return false;
				}
		
			}
		
		}
		else
		{
			sender.sendMessage(ChatColor.RED + "No sign selected (The sign may have been removed)");
			return false;
		}
		return false;
	}

}
