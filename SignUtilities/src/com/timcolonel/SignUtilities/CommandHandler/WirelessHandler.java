package com.timcolonel.SignUtilities.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.Selection;
import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.WirelessGroup;

public class WirelessHandler extends CommandHandler
{
	

	
	public WirelessHandler(SignUtilities instance, CommandSender sender, String[] args)
	{
		super(instance, sender, args);
		permission = "signutils.wireless";
	}
	
	@Override
	public boolean execute() 
	{
		if (!hasPermission()) 
		{
			return false;
		}
	
		if(args.length == 0)
		{
			sender.sendMessage(ChatColor.RED + "Too few arguments");
			return false;
		}
		else 
		{
			
			Selection sel = plugin.linkMgr.getSelection((Player) sender);
			if (sel == null) 
			{
				sender.sendMessage(ChatColor.RED + "Select a region first");
				return true;
		    }
			
			if (args[0].equalsIgnoreCase("add"))
			{
				WirelessGroup wg = new WirelessGroup();
				
				if(!wg.fromSelection(sel))
				{
					sender.sendMessage(ChatColor.RED + "A least one the the selection is not a sign or a activator!");
					return true;
				}
				plugin.wlSignMgr.addConnection(wg);
				
				sender.sendMessage(ChatColor.BLUE + "New link created");
			}
			else if (args[0].equalsIgnoreCase("del")) 
			{

				WirelessGroup wg = new WirelessGroup();
				
				if(!wg.fromSelection(sel))
				{
					sender.sendMessage(ChatColor.RED + "A least one the the selaction is not a sign or a activator!");
					return true;
				}
				if(plugin.wlSignMgr.removeConnection(wg))
				{
					sender.sendMessage(ChatColor.BLUE + "This link has been deleted");
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "This combination dont exist!");
				}
				
				
			}
			else 
			{
				return false;
			}
			
		}
		
		return true;
	}
}
