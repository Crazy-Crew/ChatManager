package me.h1dd3nxn1nja.chatmanager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import java.io.File;

public class SettingsManager {
	
	File advertisementsLog;
	
	File chatLog;
	
	File commandLog;
	
	File swearLog;
	
	File signLog;

	private final ChatManager plugin = ChatManager.getPlugin();

	public void setup() {
		if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();

		File folder = new File(plugin.getDataFolder(), "logs");
		folder.mkdir();

		if (!folder.exists()) {
			try {
				plugin.getLogger().info("The Logs folder cannot be found, creating one.");
				folder.createNewFile();
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The Logs folder could not be created! StackTrace:");
				e.printStackTrace();
			}
		}

		advertisementsLog = new File(folder, "advertisements.txt");

		if (!advertisementsLog.exists()) {
			try {
				plugin.getLogger().info("The advertisements.txt file cannot be found, creating one.");
				advertisementsLog.createNewFile();
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The advertisements.txt file could not be created! StackTrace:");
				e.printStackTrace();
			}
		}

		chatLog = new File(folder, "chat.txt");

		if (!chatLog.exists()) {
			try {
				plugin.getLogger().info("The chat.txt file cannot be found, creating one.");
				chatLog.createNewFile();
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The chat.txt file could not be created! StackTrace:");
				e.printStackTrace();
			}
		}

		commandLog = new File(folder, "commands.txt");

		if (!commandLog.exists()) {
			try {
				plugin.getLogger().info("The commands.txt file cannot be found, creating one.");
				commandLog.createNewFile();
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The commands.txt file could not be created! StackTrace:");
				e.printStackTrace();
			}
		}

		swearLog = new File(folder, "swears.txt");

		if (!swearLog.exists()) {
			try {
				plugin.getLogger().info("The swears.txt file cannot be found, creating one.");
				swearLog.createNewFile();
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The swears.txt file could not be created! StackTrace:");
				e.printStackTrace();
			}
		}

		signLog = new File(folder, "signs.txt");

		if (!signLog.exists()) {
			try {
				plugin.getLogger().info("The signs.txt file cannot be found, creating one.");
				signLog.createNewFile();
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The signs.txt file could not be created! StackTrace:");
				e.printStackTrace();
			}
		}
	}
	
	public File getAdvertisementLogs() {
		return advertisementsLog;
	}
	
	public File getChatLogs() {
		return chatLog;
	}
	
	public File getCommandLogs() {
		return commandLog;
	}
	
	public File getSwearLogs() {
		return swearLog;
	}
	
	public File getSignLogs() {
		return signLog;
	}
}