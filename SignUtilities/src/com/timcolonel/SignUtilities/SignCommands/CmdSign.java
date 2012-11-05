package com.timcolonel.SignUtilities.SignCommands;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.Colors;
import com.timcolonel.SignUtilities.SignUtilities;

public class CmdSign extends SignCommand
{
	protected String cmdStr;
	
	public CmdSign(SignUtilities instance, Player player, Sign sign, Block block) 
	{
		
		super(instance, player, sign, block);
		this.permission = "signutils.cmd.use";
		cmdStr = "";
		for (int i = 1; i < 4; i++ )
		{
			cmdStr += Colors.removeColor(sign.getLine(i)) + " ";
			 
		}
		
	}
	
	@Override
	public boolean run() 
	{
		if (!hasPermission()) 
		{
			return true;
		}
		 executeCommand();
		 return true;	 
	}
	
	public void executeCommand()
	{
		 if (cmdStr.toLowerCase().contains("[player]"))
		 {
			 String[] ls = cmdStr.split(" ");
			 cmdStr = "";
			 for (int i = 0; i < ls.length; i++)
			 {
				 if(ls[i].equalsIgnoreCase("[player]"))
				 {
					 ls[i] = player.getName();
				 }
				 cmdStr += ls[i] + " ";
			 }
		 }
		 player.performCommand(cmdStr);
	}
}
