package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListenerAntiUnicode implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		String message = event.getMessage();

		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();
		
		List<String> whitelisted = config.getStringList("Anti_Unicode.Whitelist");

		Pattern pattern = Pattern.compile("^[A-Za-z0-9-~!@#$%^&*()<>_+=-{}|';:.,\\[\"\"]|';:.,/?><_.]+$");
		Matcher matcher = pattern.matcher(event.getMessage().toLowerCase().replaceAll("\\s+", ""));

		if (config.getBoolean("Anti_Unicode.Enable")) {
			if (!Methods.cm_staffChat.contains(player.getUniqueId())) {
				if (!player.hasPermission("chatmanager.bypass.antiunicode")) {
					for (String allowed : whitelisted) {
						if (event.getMessage().contains(allowed)) return;
					}

					if (!matcher.find()) {
						event.setCancelled(true);
						player.sendMessage(Methods.color(player, messages.getString("Anti_Unicode.Message").replace("{Prefix}", messages.getString("Message.Prefix"))));
						if (config.getBoolean("Anti_Unicode.Notify_Staff")) {
							for (Player staff : plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission("chatmanager.notify.antiunicode")) {
									staff.sendMessage(Methods.color(player, messages.getString("Anti_Unicode.Notify_Staff_Format")
											.replace("{player}", player.getName()).replace("{message}", message)
											.replace("{prefix}", messages.getString("Message.Prefix"))));
								}
							}

							Methods.tellConsole(Methods.color(player, messages.getString("Anti_Unicode.Notify_Staff_Format")
											.replace("{player}", player.getName()).replace("{message}", message)
											.replace("{prefix}", messages.getString("Message.Prefix"))));
						}
						if (config.getBoolean("Anti_Unicode.Execute_Command")) {
							if (config.contains("Anti_Unicode.Executed_Command")) {
								String command = config.getString("Anti_Unicode.Executed_Command").replace("{player}", player.getName());
								List<String> commands = config.getStringList("Anti_Unicode.Executed_Command");

								new BukkitRunnable() {
									public void run() {
										plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
										for (String cmd : commands) {
											plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd.replace("{player}", player.getName()));
										}
									}
								}.runTask(plugin);
							}
						}
					}
				}
			}
		}
	}
}