package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.List;

public class ListenerBannedCommand implements Listener {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration bannedCommands = settingsManager.getBannedCommands();
		FileConfiguration messages = settingsManager.getMessages();

		Player player = event.getPlayer();

		List<String> cmd = bannedCommands.getStringList("Banned-Commands");

		if (config.getBoolean("Banned_Commands.Enable")) return;

		if (!player.hasPermission("chatmanager.bypass.bannedcommands")) {
			if (!config.getBoolean("Banned_Commands.Increase_Sensitivity")) {
				for (String command : cmd) {
					if (event.getMessage().toLowerCase().equals("/" + command)) {
						event.setCancelled(true);
						Methods.sendMessage(player, messages.getString("Banned_Commands.Message").replace("{command}", command), true);
						NotifyStaff(player, command);
						TellConsole(player, command);
						ExecuteCommand(player);
					}
				}
			} else {
				for (String command : cmd) {
					if (event.getMessage().toLowerCase().contains("/" + command)) {
						event.setCancelled(true);
						Methods.sendMessage(player, messages.getString("Banned_Commands.Message").replace("{command}", command), true);
						NotifyStaff(player, command);
						TellConsole(player, command);
						ExecuteCommand(player);
					}
				}
			}
		}

		if (!player.hasPermission("chatmanager.bypass.coloncommands")) {
			if (event.getMessage().split(" ")[0].contains(":")) {
				event.setCancelled(true);
				Methods.sendMessage(player, messages.getString("Banned_Commands.Message").replace("{command}", event.getMessage().replace("/", "")), true);
				NotifyStaff(player, event.getMessage().replace("/", ""));
				TellConsole(player, event.getMessage().replace("/", ""));
				ExecuteCommand(player);
			}
		}
	}

	public void NotifyStaff(Player player, String message) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		if (!config.getBoolean("Banned_Commands.Notify_Staff")) return;

		for (Player staff : plugin.getServer().getOnlinePlayers()) {
			if (staff.hasPermission("chatmanager.notify.bannedcommands")) {
				Methods.sendMessage(staff, messages.getString("Banned_Commands.Message").replace("{player}", player.getName()).replace("{command}", message), true);
			}
		}
	}

	public void TellConsole(Player player, String message) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		if (!config.getBoolean("Banned_Commands.Notify_Staff")) return;

		Methods.tellConsole(messages.getString("Banned_Commands.Notify_Staff_Format").replace("{player}", player.getName()).replace("{command}", message), true);
	}

	public void ExecuteCommand(Player player) {
		FileConfiguration config = settingsManager.getConfig();

		if (!config.getBoolean("Banned_Commands.Execute_Command")) return;

		if (!config.contains("Banned_Commands.Executed_Command")) return;

		String command = config.getString("Banned_Commands.Executed_Command").replace("{player}", player.getName());
		List<String> commands = config.getStringList("Banned_Commands.Executed_Command");

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