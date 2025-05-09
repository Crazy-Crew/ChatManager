package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListenerLogs implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	private final File dataFolder = this.plugin.getDataFolder();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncChatEvent event) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!config.getBoolean("Logs.Log_Chat", false)) return;

		final String playerName = event.getPlayer().getName();
		final String message = event.signedMessage().message();
		final Date time = Calendar.getInstance().getTime();

		try {
			FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Chat.txt"), true);
			BufferedWriter bw = new BufferedWriter(fw);
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

		if (!config.getBoolean("Logs.Log_Commands", false)) return;

		final List<String> blacklist = config.getStringList("Logs.Blacklist_Commands");

		final String playerName = event.getPlayer().getName();
		final String message = event.getMessage();
		final Date time = Calendar.getInstance().getTime();

		for (final String command : blacklist) {
			if (message.toLowerCase().startsWith(command)) return;
		}

		if ((message.equals("/")) || (message.equals("//"))) return;

		try {
			FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Commands.txt"), true);
			BufferedWriter bw = new BufferedWriter(fw);
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

		if (!config.getBoolean("Logs.Log_Signs", false)) return;

		final String playerName = event.getPlayer().getName();
		final Date time = Calendar.getInstance().getTime();

		final Block block = event.getBlock();
		final Location location = block.getLocation();

		for (int line = 0; line < 4; line++) {
			final Component component = event.line(line);

			if (component == null) continue;

			final String message = PlainTextComponentSerializer.plainText().serialize(component);

			int X = location.getBlockX();
			int Y = location.getBlockY();
			int Z = location.getBlockZ();

			try {
				FileWriter fw = new FileWriter(new File(new File(this.dataFolder, "Logs"), "Signs.txt"), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("[" + time + "] " + playerName + " | Location: X: " + X + " Y: " + Y + " Z: " + Z + " | Line: " + line + " | " + message);
				bw.newLine();
				fw.flush();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}