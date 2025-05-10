package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListenerLogs extends Global implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final String playerName = event.getPlayer().getName();
		final String message = event.getMessage();
		final Date time = Calendar.getInstance().getTime();

		if (!config.getBoolean("Logs.Log_Chat", false)) return;

		try {
			final FileWriter fw = new FileWriter(new File(new File(this.plugin.getDataFolder(), "Logs"), "Chat.txt"), true);
			final BufferedWriter bw = new BufferedWriter(fw);
			bw.write("[" + time + "] " + playerName + ": " + message.replaceAll("§", "&"));
			bw.newLine();
			fw.flush();
			bw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final List<String> blacklist = config.getStringList("Logs.Blacklist_Commands");

		final String playerName = event.getPlayer().getName();
		final String message = event.getMessage();
		final Date time = Calendar.getInstance().getTime();

		if (!config.getBoolean("Logs.Log_Commands", false)) return;

		for (final String command : blacklist) {
			if (event.getMessage().toLowerCase().startsWith(command)) return;
		}

		if ((message.equals("/")) || (message.equals("//"))) return;

		try {
			final FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Commands.txt"), true);
			final BufferedWriter bw = new BufferedWriter(fw);
			bw.write("[" + time + "] " + playerName + ": " + message.replaceAll("§", "&"));
			bw.newLine();
			fw.flush();
			bw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		final String playerName = event.getPlayer().getName();
		final Date time = Calendar.getInstance().getTime();

		final Location block = event.getBlock().getLocation();

		for (int line = 0; line < 4; line++) {
			final String message = event.getLine(line);

			int X = block.getBlockX();
			int Y = block.getBlockY();
			int Z = block.getBlockZ();

			if (!config.getBoolean("Logs.Log_Signs", false)) return;

			try {
				final FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Signs.txt"), true);
				final BufferedWriter bw = new BufferedWriter(fw);
				bw.write("[" + time + "] " + playerName + " | Location: X: " + X + " Y: " + Y + " Z: " + Z + " | Line: " + line + " | " + message.replaceAll("§", "&"));
				bw.newLine();
				fw.flush();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}