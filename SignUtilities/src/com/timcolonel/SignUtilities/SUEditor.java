package com.timcolonel.SignUtilities;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class SUEditor 
{
	SUEditor()
	{		

	}
	
	public static boolean changeText(Sign sign,String text, int line, Player p)
	{
		if (line == 0)
		{
			String[] lines = text.split(";",4 );
			
			int i = 0;
			
			for (String l : lines) 
			{
				if(SignUtilities.plugin.pluginsMgr.canColorSign(p))
					sign.setLine(i,  Colors.findColor(l));
				else sign.setLine(i,  l);
					
				i++;
			}
			//Clear the line left
			while (i < 4)
			{
				sign.setLine(i, "");
				i++;
			}
		}
		else
		{
			String ctext = Colors.findColor(text);
			sign.setLine(line, ctext);
		}
		sign.update();
		return true;
	}
	
	
	
	
}
