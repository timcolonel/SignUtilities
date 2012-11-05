package com.timcolonel.SignUtilities;


import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.World;


import com.timcolonel.SignUtilities.CommandHandler.CommandManager;
import com.timcolonel.SignUtilities.ExternalPlugins.SUPluginsManager;
import com.timcolonel.SignUtilities.Files.SignUtilitiesConfig;
import com.timcolonel.SignUtilities.Listeners.SUBlockListener;
import com.timcolonel.SignUtilities.Listeners.SUPlayerListener;
import com.timcolonel.SignUtilities.Listeners.SUVehicleListener;
import com.timcolonel.SignUtilities.Manager.ColorsManager;
import com.timcolonel.SignUtilities.Manager.NewsManager;
import com.timcolonel.SignUtilities.Manager.PlayerLinkSelectionManager;
import com.timcolonel.SignUtilities.Manager.PlayerSignSelectionManager;
import com.timcolonel.SignUtilities.Manager.SUMinecartManager;
import com.timcolonel.SignUtilities.Manager.SUSignManager;
import com.timcolonel.SignUtilities.Manager.WirelessSignManager;



public class SignUtilities extends JavaPlugin
{


	public static SignUtilities plugin;
	
	public final Logger logger = Logger.getLogger("Minecraft");
	
	/*****************************************************************
					Creation of config files
	 ******************************************************************/
	
	public String enabledstartup = "Enabled On Startup";
	public boolean enabled;
	public SignUtilitiesConfig config = new SignUtilitiesConfig(this);

	////////////////////////////////////////////////////////////////////////////////////////
	//                                       LISTENERS                                    //
	////////////////////////////////////////////////////////////////////////////////////////
	private final SUBlockListener blockListener = new SUBlockListener(this);
	private final SUPlayerListener playerListener = new SUPlayerListener(this);
	private final SUVehicleListener vehicleListener = new SUVehicleListener(this);
	
	
	////////////////////////////////////////////////////////////////////////////////////////
	//                                       MANAGERS                                     //
	////////////////////////////////////////////////////////////////////////////////////////
	private final CommandManager cmdManager = new CommandManager(this);
	public final SUPluginsManager pluginsMgr = new SUPluginsManager(this);
	public final SUSignManager  signMgr = new SUSignManager(this);
	public final WirelessSignManager  wlSignMgr = new WirelessSignManager(this);
	public final SUMinecartManager  minecartMgr = new SUMinecartManager(this);
	public final NewsManager  newsMgr = new NewsManager(this);
	public final ColorsManager colorsMgr = new ColorsManager(this);
	public final PlayerSignSelectionManager signSelMgr = new PlayerSignSelectionManager(this);
	public final PlayerLinkSelectionManager linkMgr = new PlayerLinkSelectionManager(this);
	
	public final HashMap<Player,SignUtilitiesClipBoard> playerClipBoard = new HashMap<Player, SignUtilitiesClipBoard>();


		
		
	@Override
	public void onDisable()
	{
		
		this.logger.info("SignUtililies disable");
		wlSignMgr.saveWireLessSigns();
	}
	
	@Override
	public void onEnable()
	{
		plugin = this;
		
		config.loadConfig(); 
		
		
		colorsMgr.loadColor();
		wlSignMgr.loadWireLessSigns();
		pluginsMgr.setupPlugins();
		
		////////////////////////////////////////////////////////////////////////////////////////
		//                               SET THE LISTENERS                                    //
		////////////////////////////////////////////////////////////////////////////////////////
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(blockListener, this);
		pm.registerEvents(playerListener, this);
		pm.registerEvents(vehicleListener, this);
		
		
		
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
		
	}	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		return cmdManager.manage(sender, cmd, commandLabel, args);
	}
	
	public Block stringToBlock(String str)
	{
		String[] v = str.substring(1, str.length() - 1).split(",");
        if(v.length == 4)
        {
        	int x = 0;
        	int y = 0;
        	int z = 0;
        	World w = null;    	
        	
        	try
        	{
        		x = Integer.parseInt(v[0]);   		
        		y = Integer.parseInt(v[1]);        		
        		z = Integer.parseInt(v[2]);  		
        		w = this.getServer().getWorld(v[3]);
        	}
        	catch (Exception e)
        	{
        		System.out.println("The string is not in a block format.");
        		return null;
        	}
        	
        	Location l = new Location(w,x,y,z);
        	return l.getBlock();
        }
        else
        {
        	System.out.println("The string is not in a block format.");
        	return null;
        }
	}

	
}
