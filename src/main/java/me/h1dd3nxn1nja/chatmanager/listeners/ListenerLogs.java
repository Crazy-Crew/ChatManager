package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListenerLogs implements Listener {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@EventHandler(ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		String playerName = event.getPlayer().getName();
		String message = event.getMessage();
		Date time = Calendar.getInstance().getTime();

		if (!config.getBoolean("Logs.Log_Chat")) return;

		/*try {
			FileWriter fw = new FileWriter(this.plugin.getFileManager().getFile("Chat.txt").getFileObject(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("[" + time + "] " + playerName + ": " + message.replaceAll("§", "&"));
			bw.newLine();
			fw.flush();
			bw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		List<String> blacklist = config.getStringList("Logs.Blacklist_Commands");

		String playerName = event.getPlayer().getName();
		String message = event.getMessage();
		Date time = Calendar.getInstance().getTime();

		if (!config.getBoolean("Logs.Log_Commands")) return;

		for (String command : blacklist) {
			if (event.getMessage().toLowerCase().startsWith(command)) return;
		}

		if ((message.equals("/")) || (message.equals("//"))) return;

		/*try {
			FileWriter fw = new FileWriter(this.plugin.getFileManager().getFile("Commands.txt").getFileObject(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("[" + time + "] " + playerName + ": " + message.replaceAll("§", "&"));
			bw.newLine();
			fw.flush();
			bw.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}*/
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onSignChange(SignChangeEvent event) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		String playerName = event.getPlayer().getName();
		Date time = Calendar.getInstance().getTime();

		for (int line = 0; line < 4; line++) {
			String message = event.getLine(line);

			int X = event.getBlock().getLocation().getBlockX();
			int Y = event.getBlock().getLocation().getBlockY();
			int Z = event.getBlock().getLocation().getBlockZ();

			if (!config.getBoolean("Logs.Log_Signs")) return;

			/*try {
				FileWriter fw = new FileWriter(this.plugin.getFileManager().getFile("Signs.txt").getFileObject(), true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("[" + time + "] " + playerName + " | Location: X: " + X + " Y: " + Y + " Z: " + Z + " | Line: " + line + " | " + message.replaceAll("§", "&"));
				bw.newLine();
				fw.flush();
				bw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}*/
		}
	}
}