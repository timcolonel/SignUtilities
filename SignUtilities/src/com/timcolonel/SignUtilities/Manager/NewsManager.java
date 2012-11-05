package com.timcolonel.SignUtilities.Manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.timcolonel.SignUtilities.SignUtilities;
import com.timcolonel.SignUtilities.Files.SUFile;

public class NewsManager 
{
	
	public static SignUtilities plugin;
	private SUFile infoFile;
	
	public NewsManager(SignUtilities instance)
	{		
		plugin = instance;
		infoFile = new SUFile(plugin, "NewsPapers.yml");
		infoFile.loadConfig();
	}
	
	public List<String> getNewsPaperList()
	{
		
		Set<String> newsPaperSet = new HashSet<String>();
		if(infoFile.getConfig().isConfigurationSection("newsPapers"))
		{
			newsPaperSet =  infoFile.getConfig().getConfigurationSection("newsPapers").getKeys(false);
		}
		else
		{
			infoFile.getConfig().createSection("newsPapers");
		}
		List<String> newsPaperList = new ArrayList<String>(newsPaperSet);
		return newsPaperList;
	}
	
	/**Return the full news paper name that fit the most with the name send
	 * 
	 * @param shortName the first part of the newspaper name
	 * @return full name of the newspaper
	 */
	public String getNewsPaperName(String shortName)
	{
		List<String> newsPaperList = getNewsPaperList();

		if (newsPaperList != null)
		{
			for (String newsPaperName : newsPaperList) 
			{
				if (newsPaperName.startsWith(shortName.toLowerCase()))
				{
					return newsPaperName;

				}

			}
		}
		return shortName; //NewsPaper not found return the shortName
	}
	/**Return the number of news of a particular newspaper
	 * 
	 * @param newsPaperName newsPaper where to count the news
	 * @return
	 */
	public int getNewsNumber(String shortName)
	{
		String newsPaperName = getNewsPaperName(shortName);
		List<String> newsList = getNewsList(newsPaperName);
		return newsList.size();
	}
	
	public List<String> getNewsList(String shortName)
	{
		String newsPaperName = getNewsPaperName(shortName);
		Set<String> newsSet = new HashSet<String>();
		if(infoFile.getConfig().isConfigurationSection("newsPapers." + newsPaperName))
		{
			newsSet = infoFile.getConfig().getConfigurationSection("newsPapers." + newsPaperName).getKeys(false);
		}
		else
		{
			infoFile.getConfig().createSection("newsPapers." + newsPaperName);
		}
		List<String> newsList =  new ArrayList<String>(newsSet);
		return newsList;
	}
	
	public String getNews(String newsPaperName, String newsName)
	{
		return infoFile.getConfig().getString("newsPapers." + newsPaperName + "." + newsName);
	}
	
	public String getNews(String newsPaperName, int newsNumber)
	{
		List<String> newsList = getNewsList(newsPaperName);
		String newsName = newsList.get(newsNumber - 1);
		return getNews(newsPaperName, newsName);
	}
	
	public String getRandomNews(String newsPaperName)
	{
		Random r = new Random();
		int newsNb = getNewsNumber(newsPaperName);
		if (newsNb == 0)
		{
			return ChatColor.RED + "NO NEWS";
		}
		else 
		{
			int newsNumber = 1 + r.nextInt(newsNb);
			String news = getNews(newsPaperName, newsNumber);
			return news;
		}
	}
	
	public String getNextNews(String name)
	{
		String fullName = getNewsPaperName(name);
		return getRandomNews(fullName);
	}
	
	public Boolean addNews(String shortName, String newsName,String text)
	{
		String newsPaperName = getNewsPaperName(shortName);
		
		String path ="newsPapers." + newsPaperName + "." + newsName;
		infoFile.getConfig().set(path, text);
		infoFile.save();
		return true;
	}
	
	public Boolean delNews(String shortName, String newsName)
	{
		String newsPaperName = getNewsPaperName(shortName);
		String path ="newsPapers." + newsPaperName;
		
		//Check if the newsPaper exists
		if(infoFile.getConfig().isConfigurationSection(path))
		{
			//Check if the newsPaper contains the news
			if(infoFile.getConfig().getConfigurationSection(path).getKeys(false).contains(newsName))
			{
				infoFile.getConfig().set(path + "." + newsName, null);
				infoFile.save();
				return true;	
			}
			else 
			{
				return false;
			}
			
		}
		else 
		{
			return false;
		}
	}
	
	public void listNews(String shortName, Player player, int page)
	{
		String newsPaperName = getNewsPaperName(shortName);
		String path ="newsPapers." + newsPaperName;
		
		//Check if the newsPaper exists
		if(infoFile.getConfig().isConfigurationSection(path))
		{
			List<String> news = getNewsList(shortName);
			int pageNb = (Integer) news.size() / 10 + 1;
			
			player.sendMessage(ChatColor.BLUE + "News of " 
						+ ChatColor.GREEN + "\"" + newsPaperName + "\"" 
						+ ChatColor.BLUE + ", page " 
						+ ChatColor.GRAY + page
						+ ChatColor.BLUE + " of "
						+ ChatColor.GRAY + pageNb);
			int startDisp = 0;
			if(page >= 1 && page <= pageNb)
			{
				 startDisp= (page - 1)*10;
			}
			
			int endDisp = startDisp +9;
			if(endDisp >= news.size())
			{
				endDisp = news.size();
			}
			
			
			for(int i = startDisp; i < endDisp; i++)
			{	
				String newsName = news.get(i);
				String text = getNews(newsPaperName, newsName);
				player.sendMessage(ChatColor.DARK_GREEN + newsName + ": " 
						+ ChatColor.GRAY + text);
			}
		}
		else 
		{
			player.sendMessage(ChatColor.RED + "No newspapers with this name: "
					+ ChatColor.DARK_RED + "\"" + newsPaperName + "\"");

		}
	}
	
}
