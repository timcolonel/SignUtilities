package com.timcolonel.SignUtilities.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SignUtilities;

public class DirectEditHandler extends CommandHandler
{

	public DirectEditHandler(SignUtilities instance, CommandSender sender, String[] args) 
	{
		super(instance, sender, args);
		permission = "signutils.directedit";
	}
	
	@Override
	public boolean execute() 
	{
		if (!hasPermission()) 
		{
			return false;
		}
		
		if (plugin.signMgr.directEditEnable.containsKey((Player) sender))
		{
			plugin.signMgr.directEditEnable.put((Player) sender, !plugin.signMgr.directEditEnable.get((Player) sender));
		}
		else
		{
			plugin.signMgr.directEditEnable.put((Player) sender, !plugin.config.directEditDefault);
		}
		
		if (plugin.signMgr.directEditEnable.get((Player) sender))
		{
			sender.sendMessage(ChatColor.BLUE + "Direct Sign editing mode enable");
		}
		else
		{
			sender.sendMessage(ChatColor.DARK_PURPLE + "Direct Sign editing mode disable");			
		}
		return true;
	}
}
