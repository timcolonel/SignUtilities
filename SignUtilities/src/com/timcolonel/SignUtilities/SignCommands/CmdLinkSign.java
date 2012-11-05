package com.timcolonel.SignUtilities.SignCommands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.Colors;
import com.timcolonel.SignUtilities.SignAction;
import com.timcolonel.SignUtilities.SignUtilities;

public class CmdLinkSign extends CmdSign
{
	protected SignAction action; 
	protected String cmds[] = new String[4];
	int selected = 0;
	
	public CmdLinkSign(SignUtilities instance, Player player, Sign sign, Block block, SignAction action) 
	{
		super(instance, player, sign, block);
		this.permission = "signutils.cmd.custom.use";
		
		this.action = action;
		int i = 0;
		for(String line: sign.getLines())
		{
			cmds[i] = "";
			if(line.contains("[") && line.contains("]"))
			{

				if(Colors.ContainColor(line, ChatColor.DARK_BLUE) )
				{
					selected = i;
				}
				String str;
				str = Colors.removeColor(line);
				str = str.substring(1, str.length() -1);

				if(plugin.config.cmdLinks.containsKey(str))
				{	
					cmds[i] = str;
				}
			}

			i++;
			
		}
		if(!cmds[selected].equalsIgnoreCase(""))
		{
			cmdStr = plugin.config.cmdLinks.get(cmds[selected]);
		}
		else
		{
			cmdStr = "";
		}
	}
	
	@Override
	public boolean run() 
	{
		if (!hasPermission()) 
		{
			return true;
		}
		if(!cmdStr.equalsIgnoreCase(""))
		{
			//If left click on sign, execute
			if(action == SignAction.LeftClick)
			{
				executeCommand();
			}
			else if(action == SignAction.RightClick)//If right click change position
			{
				int prevSel = selected;
				selected ++;
				selected %= 4;
				while(cmds[selected].equalsIgnoreCase(""))
				{
					selected ++;
					selected %= 4;
	
				}
	
				//Remove the color on the previous selection
				sign.setLine(prevSel, Colors.removeColor(sign.getLine(prevSel)));
	
				//set the color to the new selection
				sign.setLine(selected, ChatColor.DARK_BLUE + sign.getLine(selected));
				sign.update();
				
				cmdStr = plugin.config.cmdLinks.get(cmds[selected]);
			}
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	

}
