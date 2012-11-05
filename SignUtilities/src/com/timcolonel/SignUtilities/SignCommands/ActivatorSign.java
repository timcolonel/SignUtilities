package com.timcolonel.SignUtilities.SignCommands;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SignUtilities;
/**************************************************************
 * This sign will activate all the blocks around it
 **************************************************************/
public class ActivatorSign extends SignCommand
{
	public ActivatorSign(SignUtilities instance, Player player, Sign sign, Block block) 
	{
		super(instance, player, sign, block);
		this.permission = "signutils.activator.use";
	}

	@Override
	public boolean run() 
	{
		
		if (!hasPermission()) 
		{
			return true;
		}
		Block mainBlock = sign.getBlock();
		
		activateBlock(mainBlock, 1, 0, 0);
		activateBlock(mainBlock, -1, 0, 0);
		activateBlock(mainBlock, 0, 1, 0);
		activateBlock(mainBlock, 0, -1, 0);
		activateBlock(mainBlock, 0, 0, 1);
		activateBlock(mainBlock, 0, 0, -1);
		
		return true;
	}
	
	public void activateBlock(Block main, float x, float y, float z)
	{
		Location loc = main.getLocation();
		loc.add(x, y, z);
		Block blockToActivate = loc.getBlock();
		BlockState state = blockToActivate.getState();
		switch(state.getTypeId())
		{
			
			case 23: //If its a dispenser
				Dispenser dispenser = (Dispenser) state;
				dispenser.dispense();
				break;
		
		
		}
		
		
	}
}
