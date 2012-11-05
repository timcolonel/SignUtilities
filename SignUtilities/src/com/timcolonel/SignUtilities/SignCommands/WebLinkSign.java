package com.timcolonel.SignUtilities.SignCommands;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.Colors;
import com.timcolonel.SignUtilities.SignAction;
import com.timcolonel.SignUtilities.SignUtilities;

public class WebLinkSign extends SignCommand
{
	protected SignAction action; 
	protected String links[] = new String[4];
	protected String url;
	int selected = 0;
	
	public WebLinkSign(SignUtilities instance, Player player, Sign sign, Block block, SignAction action) 
	{
		super(instance, player, sign, block);
		this.permission = "signutils.web.use";
		
		this.action = action;
		int i = 0;
		for(String line: sign.getLines())
		{
			links[i] = "";
			if(line.contains("(") && line.contains(")"))
			{

				if(Colors.ContainColor(line, ChatColor.DARK_BLUE) )
				{
					selected = i;
				}
				String str;
				str = Colors.removeColor(line);
				str = str.substring(1, str.length() -1);

				if(plugin.config.webLinks.containsKey(str))
				{	
					links[i] = str;
				}
			}

			i++;
		}

		if(!links[selected].equalsIgnoreCase(""))
		{
			url = plugin.config.webLinks.get(links[selected]);
		}
		else
		{
			url = "";
		}
	}
	
	@Override
	public boolean run() 
	{
		if (!hasPermission()) 
		{
			return true;
		}

		if(!url.equalsIgnoreCase(""))
		{
			//If left click on sign, execute
			if(action == SignAction.LeftClick)
			{
				displayUrl();
			}
			else if(action == SignAction.RightClick)//If right click change position
			{
				int prevSel = selected;
				selected ++;
				selected %= 4;
				while(links[selected].equalsIgnoreCase(""))
				{
					selected ++;
					selected %= 4;
	
				}
	
				//Remove the color on the previous selection
				sign.setLine(prevSel, Colors.removeColor(sign.getLine(prevSel)));
	
				//set the color to the new selection
				sign.setLine(selected, ChatColor.DARK_BLUE + sign.getLine(selected));
				sign.update();
				
				url = plugin.config.webLinks.get(links[selected]);
			}
			return true;
		}
		else
		{
			return false;
		}
		
	}

	private void displayUrl() 
	{
		System.out.println("oijoijoi");
		player.sendMessage(url);
	}
	
	

}
