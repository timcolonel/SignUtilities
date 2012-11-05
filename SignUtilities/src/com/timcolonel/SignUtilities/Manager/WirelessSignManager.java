package com.timcolonel.SignUtilities.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.Utils;
import com.timcolonel.SignUtilities.WirelessGroup;
import com.timcolonel.SignUtilities.Files.SUFile;

public class WirelessSignManager
{
	
	public static SignUtilities plugin;
	public final HashMap<String, ArrayList<String>> wirelessSigns = new HashMap<String,ArrayList<String>>();
	public SUFile wirelessSignsFile; 
	
	public WirelessSignManager(SignUtilities instance)
	{		
		plugin = instance;
		
		//Load the file
		wirelessSignsFile = new SUFile(plugin, "WirelessSigns.yml");
		wirelessSignsFile.loadConfig();
	}
	
	public boolean blockHasConnection(String key)
	{
		return wirelessSigns.containsKey(key);
	}
	
	public ArrayList<String> getSignLinkedTo(String key)
	{
		return wirelessSigns.get(key);
	}
	
	public void addConnection(WirelessGroup wg)
	{
		
 		String a = Utils.blockToString(wg.activator);
		String s = Utils.blockToString(wg.sign);
		if(wirelessSigns.containsKey(a))
		{
			ArrayList<String> list = wirelessSigns.get(a);
			if(!list.contains(s));
				list.add(s);
			wirelessSigns.put(a, list);
		}
		else
		{
			ArrayList<String> list = new ArrayList<String>();
			list.add(s);
			wirelessSigns.put(a, list);
		}
	}
	
	public boolean removeConnection(WirelessGroup wg)
	{
		String a = Utils.blockToString(wg.activator);
		String s = Utils.blockToString(wg.sign);
		if(wirelessSigns.containsKey(a))
		{
			ArrayList<String> list = wirelessSigns.get(a);
			if(!list.contains(s));
				list.remove(s);
			wirelessSigns.put(a, list);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	public void saveWireLessSigns()
	{

		Set<String> keys = wirelessSigns.keySet();
		for(String key:keys)
		{
			plugin.logger.info(key);
			wirelessSignsFile.getConfig().set("Connections." + key,wirelessSigns.get(key));
		}
		wirelessSignsFile.save();
	}
	
	public void loadWireLessSigns()
	{
		YamlConfiguration config = wirelessSignsFile.getConfig();
		if(config.isConfigurationSection("Connections"))
    	{
	    	Set<String> keys = config.getConfigurationSection("Connections").getKeys(false);
			if(keys != null)
			{
				for(String key:keys)
				{
					ArrayList<String> list = (ArrayList<String>) config.getStringList("Connections." + key);
					wirelessSigns.put(key, list);
				}
			}
			
    	}
	}
	
	
	
}
