package com.timcolonel.SignUtilities.Manager;

import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SignAction;
import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.SignCommands.ActivatorSign;
import com.timcolonel.SignUtilities.SignCommands.CmdLinkSign;
import com.timcolonel.SignUtilities.SignCommands.CmdSign;
import com.timcolonel.SignUtilities.SignCommands.MinecartRemoveSign;
import com.timcolonel.SignUtilities.SignCommands.MinecartSign;
import com.timcolonel.SignUtilities.SignCommands.NewsSign;
import com.timcolonel.SignUtilities.SignCommands.WebLinkSign;



public class SUSignManager 
{
	public final HashMap<String,Block> tempSigns = new HashMap<String,Block>();
	public final HashMap<Player ,Boolean> directEditEnable = new HashMap<Player,Boolean>();
	
	
	public static SignUtilities plugin;
	public SUSignManager(SignUtilities instance)
	{		
		plugin = instance;
	}
	
	public boolean runSignCommand(Player p , Block signBlock, Block clickedBlock, BlockFace face , SignAction action)
	{
		Sign sign = (Sign) signBlock.getState();
		
		boolean commandSign = false;
		//Check if the first line of the sign is a Dark_blue, meaning that it is a command sign  or if the user want to see the information 
		
		 if(sign.getLine(0).contains("info:"))
	     {   
			 
			 NewsSign newsSign = new NewsSign(plugin, p, sign, clickedBlock, face);
			 newsSign.run();
			 commandSign = true;
	     }
		 
		 String key = sign.getLine(0);
		
		 if (key.contains("[cmd]") && plugin.pluginsMgr.hasPermission(p, "signutils.cmd.use"))
		 {

			 CmdSign cmdSign = new CmdSign(plugin, p, sign, clickedBlock);
			 cmdSign.run();
			 commandSign = true;
		 }
		 else if (key.contains("[minecart]"))
		 {
			
			 minecartCommand(p, clickedBlock, sign);
			 commandSign = true;
		 }
		 else if (key.contains("[minecart:rem]"))
		 {
			 minecartRemoveCommand(p, clickedBlock, sign);
			 commandSign = true;
		 }
		 else if(key.contains("[activator]"))
		 {
			 activateBlockAroundSign(p, sign, clickedBlock);
			 commandSign = true;
		 }

		 for(String str: sign.getLines())
		 {
			 if(str.contains("[") && str.contains("]"))
			 {
				 CmdLinkSign cmdLinkSign = new CmdLinkSign(plugin, p, sign, clickedBlock, action);
				 if(cmdLinkSign.run())
					 commandSign = true;
				 break;
			 }

			
		 }
		 for(String str: sign.getLines())
		 {
			 if(str.contains("(") && str.contains(")"))
			 {
				 WebLinkSign webLinkSign = new WebLinkSign(plugin, p, sign, clickedBlock, action);
				 if(webLinkSign.run())
					 commandSign = true;
				 break;
			 } 	
		 }
			 
			
		 
		 
		 
		 
		 return commandSign;
			
	}
	
	public void minecartCommand(Player p , Block clickedBlock, Sign sign)
	{
		MinecartSign minecartSign = new MinecartSign(plugin, p, sign, clickedBlock);
		minecartSign.run();
	}
	public void minecartRemoveCommand(Player p , Block clickedBlock, Sign sign)
	{
		MinecartRemoveSign removeSign = new MinecartRemoveSign(plugin, p, sign, clickedBlock);
		removeSign.run();
	}
	
	public void activateBlockAroundSign(Player p, Sign sign, Block block)
	{
		ActivatorSign activator = new ActivatorSign(plugin, p, sign, block);
		activator.run();
	}


	
	/**DISPLAY OR NOT THE MESSAGE "YOU SELECTED A SIGN"
	 * 
	 * @param player
	 * @param commandSign: boolean that tell if the sign was a command sign or not
	 */
	
}
