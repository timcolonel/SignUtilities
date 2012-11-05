package com.timcolonel.SignUtilities;

import org.bukkit.ChatColor;

public class Colors 
{
	Colors()
	{
		
	}
	
	public static String findColor(String str)
	{
		String[] splitLine = str.split("&");
		String newLine = splitLine[0];

		
		for (int j = 1; j < splitLine.length; j++) 
		{
			int col; 
			col = "0123456789abcdef".indexOf(splitLine[j].toLowerCase().charAt(0));
			if (splitLine[j].length() == 0 // Double ## ou && 
					|| col == -1 //If the first character is not one of those 0123456789abcdef
					) 
			{
				newLine += "&";
			} 
			else //Otherwise replace the & with the § characters that will be automatically translate to chatcolor
			{
				newLine += "§";
			}
			newLine += splitLine[j];
		}
	
		return newLine;
		
	}
	
	//Translate the color format of bukkit to the '&' format
	public static String decodeColor(String str)
	{
		
		String newStr = "";
		for (int i = 0; i< str.length(); i++)
		{
		
			if (str.charAt(i) == 167)
			{
				newStr += "&";
			}
			else
			{
				newStr += str.charAt(i);
			}
				
		}
	
		return newStr;
		
	}
	
	//Remove all the color to get the text
	public static String removeColor(String str)
	{
		
		String newStr = "";
		int i = 0;
		while(i< str.length())
		{
		
			if (str.charAt(i) == 167)
			{
				i++;//Skip the next char as it's the color code.
			}
			else
			{
				newStr += str.charAt(i);
			}
			i++;
		}
	
		return newStr;
		
	}
	
	public static boolean ContainColor(String str, ChatColor color)
	{
		return str.contains(""+color);
	}
}
