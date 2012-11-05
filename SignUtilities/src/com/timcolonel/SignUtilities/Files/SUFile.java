package com.timcolonel.SignUtilities.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.timcolonel.SignUtilities.SignUtilities;

/**
* A class that handles the configuration file.
*
* @author iffa
* @author Pandarr
* @author Sammy
* @author kitskub
*/
public class SUFile
{
	// Variables
	protected  YamlConfiguration config;
	protected  File configFile;
	protected  boolean loaded = false;
	protected String name;
	public static SignUtilities plugin;
	
	/**
	  * Gets the configuration file.
	  *
	  * @return the config
	  */
	public  YamlConfiguration getConfig() 
	{
		if (!loaded) 
		{
			loadConfig();
		}
		return config;
	}
		 
		/**
		  * Gets the configuration file.
		  *
		  * @return Configuration file
		  */
	public  File getConfigFile() 
	{
		return configFile;
	}
	 
	/**
	  * Loads the configuration file from the .jar.
	  */
	public  void loadConfig() 
	{
		//Make the directory if its doesn't exist
		(new File("plugins/SignUtilities/")).mkdir();  
		configFile = new File("plugins/SignUtilities/", name);
		if (configFile.exists()) 
		{
			config = new YamlConfiguration();
			try 
			{
				config.load(configFile);
			} 
			catch (FileNotFoundException ex) 
			{
				// TODO: Log exception
				System.out.println("[SignUtilties] Error loading config: NO FILE");
				
			} 
			catch (IOException ex) 
			{
				// TODO: Log exception
				System.out.println("[SignUtilties] Error loading config: IOEX");
			} 
			catch (InvalidConfigurationException ex) 
			{
				// TODO: Log exception
				System.out.println("[SignUtilties] Error loading config: INVALID CONFIG");
			
			}
			loaded = true;
			} 
			else 
			{

				
				try 
				{
					InputStream jarURL = SUFile.class.getResourceAsStream("/" + name);
					copyFile(jarURL, configFile);
					config = new YamlConfiguration();
					config.load(configFile);
					loaded = true;
					// TODO: Log that config has been loaded
				} catch (Exception e) 
				{
					System.out.println("[MineGame] Error loading config when coping from jar");
					// TODO: Log exception
				}
			}
		}
		 
		/**
		  * Copies a file to a new location.
		  *
	  * @param in InputStream
	  * @param out File
	  *
	  * @throws Exception
	  */
	 protected void copyFile(InputStream in, File out) throws Exception 
	 {
		 
		InputStream fis = in;
		FileOutputStream fos = new FileOutputStream(out);
		try 
		{
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) 
			{
				fos.write(buf, 0, i);
			}
		} 
		catch (Exception e) 
		{
		
			throw e;
		} 
		finally 
		{
			if (fis != null) 
			{
				fis.close();
			}
			if (fos != null) 
			{
				fos.close();
			}
		}
		
	}
	 
	 public void save()
	{
		try {
			config.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	 
	
	/**
	  * Constructor of SpaceConfig.
	  */
	public SUFile(SignUtilities instance)
	{
		plugin = instance;
		name = "config.yml";
	}
	
	public SUFile(SignUtilities instance, String fileName) 
	{
		plugin = instance;
		name = fileName;
	}
}

