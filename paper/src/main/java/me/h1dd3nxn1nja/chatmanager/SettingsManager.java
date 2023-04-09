package me.h1dd3nxn1nja.chatmanager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SettingsManager {

	FileConfiguration config;
	File cfile;
	
	FileConfiguration Messages;
	File msgfile;

	FileConfiguration AutoBroadcast;
	File abfile;

	FileConfiguration bannedcommands;
	File cmdfile;

	FileConfiguration bannedwords;
	File wordfile;
	
	File advertisementsLog;
	
	File chatLog;
	
	File commandLog;
	
	File swearLog;
	
	File signLog;

	private final ChatManager plugin = ChatManager.getPlugin();

	public void setup() {
		if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdir();
		
		cfile = new File(plugin.getDataFolder(), "config.yml");

		if (!cfile.exists()) {
			try {
				plugin.getLogger().info("The config.yml file cannot be found, creating one.");
				File en = new File(plugin.getDataFolder(), "/config.yml");
				InputStream E = getClass().getResourceAsStream("/config.yml");
				copyFile(E, en);
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The config.yml file could not be created! StackTrace:");
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(cfile);
		
		abfile = new File(plugin.getDataFolder(), "autobroadcast.yml");
		if (!abfile.exists()) {
			try {
				plugin.getLogger().info("The autobroadcast.yml file cannot be found, creating one.");
				File en = new File(plugin.getDataFolder(), "/autobroadcast.yml");
				InputStream E = getClass().getResourceAsStream("/autobroadcast.yml");
				copyFile(E, en);
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The autobroadcast.yml file could not be created!");
				e.printStackTrace();
			}
		}

		AutoBroadcast = YamlConfiguration.loadConfiguration(abfile);
		
		msgfile = new File(plugin.getDataFolder(), "messages.yml");

		if(!msgfile.exists()) {
			try {
				plugin.getLogger().info("The messages.yml file cannot be found, creating one.");
				File en = new File(plugin.getDataFolder(), "/messages.yml");
				InputStream E = getClass().getResourceAsStream("/messages.yml");
				copyFile(E, en);
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The messages.yml file could not be created! StackTrace:");
				e.printStackTrace();
			}
		}

		Messages = YamlConfiguration.loadConfiguration(msgfile);
		
		cmdfile = new File(plugin.getDataFolder(), "bannedcommands.yml");

		if (!cmdfile.exists()) {
			try {
				plugin.getLogger().info("The bannedcommands.yml file cannot be found, creating one.");
				File en = new File(plugin.getDataFolder(), "/bannedcommands.yml");
				InputStream E = getClass().getResourceAsStream("/bannedcommands.yml");
				copyFile(E, en);
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The bannedcommands.yml file could not be created! StackTrace:");
				e.printStackTrace();
			}
		}

		bannedcommands = YamlConfiguration.loadConfiguration(cmdfile);
		
		wordfile = new File(plugin.getDataFolder(), "bannedwords.yml");

		if (!wordfile.exists()) {
			try {
				plugin.getLogger().info("The bannedwords.yml file cannot be found, creating one.");
				File en = new File(plugin.getDataFolder(), "/bannedwords.yml");
				InputStream E = getClass().getResourceAsStream("/bannedwords.yml");
				copyFile(E, en);
			} catch (Exception e) {
				plugin.getLogger().severe("Error: The bannedwords.yml file could not be created! StackTrace:");
				e.printStackTrace();
			}
		}

		bannedwords = YamlConfiguration.loadConfiguration(wordfile);
		
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

	public FileConfiguration getAutoBroadcast() {
		return AutoBroadcast;
	}
	
	public FileConfiguration getMessages() {
		return Messages;
	}

	public FileConfiguration getBannedCommands() {
		return bannedcommands;
	}

	public FileConfiguration getBannedWords() {
		return bannedwords;
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
	
	public void saveBannedWords() {
		try {
			bannedwords.save(wordfile);
		} catch (IOException e) {
			plugin.getLogger().severe("Could not save BannedWords.yml!");
		}
	}
	
	public void saveBannedCommands() {
		try {
			bannedcommands.save(cmdfile);
		} catch (IOException e) {
			plugin.getLogger().severe("Could not save BannedCommands.yml!");
		}
	}
	
	public void saveAutoBroadcast() {
		try {
			AutoBroadcast.save(abfile);
		} catch (IOException e) {
			plugin.getLogger().severe("Could not save autobroadcast.yml!");
		}
	}
	
	public void reloadMessages() {
		Messages = YamlConfiguration.loadConfiguration(msgfile);
	}
	
	public void reloadAutoBroadcast() {
		AutoBroadcast = YamlConfiguration.loadConfiguration(abfile);
	}

	public void reloadBannedCommands() {
		bannedcommands = YamlConfiguration.loadConfiguration(cmdfile);
	}

	public void reloadBannedWords() {
		bannedwords = YamlConfiguration.loadConfiguration(wordfile);
	}
	
	public FileConfiguration getConfig() {
		return config;
	}

	public void saveConfig() {
		try {
			config.save(cfile);
		} catch (IOException e) {
			plugin.getLogger().severe( "Could not save config.yml!");
		}
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(cfile);
	}
	
	public PluginDescriptionFile getDesc() {
		return plugin.getDescription();
	}

	private void copyFile(InputStream in, File out) throws Exception {
		try (InputStream fis = in; FileOutputStream fos = new FileOutputStream(out)) {
			byte[] buf = new byte[1024];
			int i;

			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		}
	}
}