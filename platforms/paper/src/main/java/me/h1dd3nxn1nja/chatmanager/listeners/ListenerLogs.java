package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListenerLogs implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = settingsManager.getConfig();

		String playerName = event.getPlayer().getName();
		String message = event.getMessage();
		Date time = Calendar.getInstance().getTime();

		if (config.getBoolean("Logs.Log_Chat")) {
			try {
				FileWriter fw = new FileWriter(settingsManager.getChatLogs(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("[" + time + "] " + playerName + ": " + message.replaceAll("§", "&"));
				bw.newLine();
				fw.flush();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = settingsManager.getConfig();

		List<String> blacklist = config.getStringList("Logs.Blacklist_Commands");

		String playerName = event.getPlayer().getName();
		String message = event.getMessage();
		Date time = Calendar.getInstance().getTime();

		if (config.getBoolean("Logs.Log_Commands")) {
			for (String command : blacklist) {
				if (event.getMessage().toLowerCase().startsWith(command)) return;
			}

			if ((message.equals("/")) || (message.equals("//"))) return;

			try {
				FileWriter fw = new FileWriter(settingsManager.getCommandLogs(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("[" + time + "] " + playerName + ": " + message.replaceAll("§", "&"));
				bw.newLine();
				fw.flush();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event) {
		FileConfiguration config = settingsManager.getConfig();

		String playerName = event.getPlayer().getName();
		Date time = Calendar.getInstance().getTime();

		for (int line = 0; line < 4; line++) {
			String message = event.getLine(line);

			int X = event.getBlock().getLocation().getBlockX();
			int Y = event.getBlock().getLocation().getBlockY();
			int Z = event.getBlock().getLocation().getBlockZ();

			if (config.getBoolean("Logs.Log_Signs")) {
				try {
					FileWriter fw = new FileWriter(settingsManager.getSignLogs(), true);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write("[" + time + "] " + playerName + " | Location: X: " + X + " Y: " + Y + " Z: " + Z + " | Line: " + line + " | "+ message.replaceAll("§", "&"));
					bw.newLine();
					fw.flush();
					bw.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}