package com.timcolonel.SignUtilities.Manager;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;

import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.Files.SUColorFile;

public class ColorsManager 
{
	public static SignUtilities plugin;
	private SUColorFile colorFile;
	public final HashMap<String ,ChatColor > colorsNames = new HashMap<String ,ChatColor>();
	
	public ColorsManager(SignUtilities instance)
	{		
		plugin = instance;
		colorFile = new SUColorFile(plugin, "colorsFile.yml");
		colorFile.loadConfig();
	}
	
	public void loadColor()
	{
		List<String> colorL = colorFile.getColorList("AQUA");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.AQUA);
		}
		
		colorL = colorFile.getColorList("BLACK");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.BLACK);
		}
		
		colorL = colorFile.getColorList("BLUE");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.BLUE);
		}
		
		colorL = colorFile.getColorList("DARK_AQUA");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.DARK_AQUA);
		}
		
		colorL = colorFile.getColorList("DARK_BLUE");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.DARK_BLUE);
		}
		
		colorL = colorFile.getColorList("DARK_GRAY");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.DARK_GRAY);
		}
		
		colorL = colorFile.getColorList("DARK_GREEN");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.DARK_GREEN);
		}
		colorL = colorFile.getColorList("DARK_PURPLE");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.DARK_PURPLE);
		}
		
		colorL = colorFile.getColorList("DARK_RED");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.DARK_RED);
		}
		
		colorL = colorFile.getColorList("GOLD");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.GOLD);
		}
		
		colorL = colorFile.getColorList("GRAY");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.GRAY);
		}
		
		colorL = colorFile.getColorList("GREEN");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.GREEN);
		}
		
		colorL = colorFile.getColorList("LIGHT_PURPLE");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.LIGHT_PURPLE);
		}
		
		colorL = colorFile.getColorList("RED");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.RED);
		}
		
		colorL = colorFile.getColorList("WHITE");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.WHITE);
		}
		
		colorL = colorFile.getColorList("YELLOW");
		for (String color: colorL)
		{
			colorsNames.put(color, ChatColor.YELLOW);
		}
		//setDefaultColor();
		
	}
	
	
	public Boolean colorSign(Sign sign,String colorStr, int line)
	{
		String[] lines = sign.getLines();
		if (line == 0)
		{
			int i = 0;
			
			for (String l : lines) 
			{
				l = ChatColor.stripColor(l);
				// Don't Colour a line if it will overwrite the max length (15)
				if(l.length() <= 15 && l.length() > 0)
				{
					if (colorsNames.containsKey(colorStr))
					{
						sign.setLine(i, ""+ colorsNames.get(colorStr) + l);
					}	
					else
					{
						return false;
					}
				}
				i++;
			}
		}
		else
		{
			line --;//Allow to set the first line to be 1 and mot 0
			String l = lines[line];
			l = ChatColor.stripColor(l);
			// Don't Colour a line if it will overwrite the max length (15)
			if(l.length() < 14 && l.length() > 0)
			{
				if (colorsNames.containsKey(colorStr))
				{
					sign.setLine(line, colorsNames.get(colorStr) + l);
				
				}	
				else
				{
					return false;
				}
			}
		}
		sign.update();
		return true;
	}

	
	
	
	public void setDefaultColor()
	{
		colorsNames.put("AQUA", ChatColor.AQUA);
		colorsNames.put("BLACK", ChatColor.BLACK);
		colorsNames.put("BLUE", ChatColor.BLUE);
		colorsNames.put("DARK_AQUA", ChatColor.DARK_AQUA);
		colorsNames.put("DARK_BLUE", ChatColor.DARK_BLUE);
		colorsNames.put("DARK_GRAY", ChatColor.DARK_GRAY);
		colorsNames.put("DARK_GREEN", ChatColor.DARK_GREEN);
		colorsNames.put("DARK_PURPLE", ChatColor.DARK_PURPLE);
		colorsNames.put("DARK_RED", ChatColor.DARK_RED);
		colorsNames.put("GOLD", ChatColor.GOLD);
		colorsNames.put("GRAY", ChatColor.GRAY);
		colorsNames.put("GREEN", ChatColor.GREEN);
		colorsNames.put("LIGHT_PURPLE", ChatColor.LIGHT_PURPLE);
		colorsNames.put("RED", ChatColor.RED);
		colorsNames.put("WHITE", ChatColor.WHITE);
		colorsNames.put("YELLOW", ChatColor.YELLOW);
		
	}

	
}
