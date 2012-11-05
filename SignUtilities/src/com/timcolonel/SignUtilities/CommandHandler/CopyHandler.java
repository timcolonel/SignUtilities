package com.timcolonel.SignUtilities.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.SignUtilitiesClipBoard;
import com.timcolonel.SignUtilities.Utils;

public class CopyHandler extends CommandHandler
{
	public CopyHandler(SignUtilities instance, CommandSender sender, String[] args) 
	{
		super(instance, sender, args);
		permission = "signutils.copy";
	}
	
	
	@Override
	public boolean execute() 
	{
		//Permissions
		if (!hasPermission()) 
		{
			return false;
		}
				
		if(plugin.signSelMgr.hasPlayerSelected((Player) sender))
		{
			
			if (Utils.isASign(plugin.signSelMgr.getSelection((Player) sender)))
			{
				//Check if the sign is there
				Sign s = (Sign) plugin.signSelMgr.getSelection((Player) sender).getState();
				
				if(args.length == 0)
				{
						SignUtilitiesClipBoard cb = new SignUtilitiesClipBoard();
						for(int i = 0; i < 4; i++)
						{
							cb.content.add(s.getLine(i));
							cb.lines.add(i);
						}
						
						plugin.playerClipBoard.put((Player) sender ,cb); 
						sender.sendMessage(ChatColor.BLUE + "Sign copied");
						return true;
					
					}
					else if(args.length > 4) //Mean there is too much args
					{
						sender.sendMessage(ChatColor.RED + "Too much args");
						return false;	
					}
					else //Copy only the specified lines
					{
						SignUtilitiesClipBoard cb = new SignUtilitiesClipBoard();
						
						for (int i = 0; i < args.length; i++ )
						{
							try 
							{
								int l = Integer.parseInt(args[i]);
								l--;

								if (l < 0 || l > 4 ) //Check if the line number is real
								{
									sender.sendMessage(ChatColor.RED + "There is only four lines on a sign");
									return false;
								}
							
								cb.lines.add(l);
								cb.content.add(s.getLine(l));
						
							}
							catch (Exception e) 
							{
								sender.sendMessage(ChatColor.RED + "Number Expected");
								return false;
							}
						
							}
							plugin.playerClipBoard.put((Player) sender ,cb); 
							sender.sendMessage(ChatColor.BLUE + "Sign copied");
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
		return false;
	}

}
