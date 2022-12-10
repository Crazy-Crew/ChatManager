package me.me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ListenerBannedCommand implements Listener {
	
	public ChatManager plugin;

	public ListenerBannedCommand(ChatManager plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {

		FileConfiguration config = ChatManager.settings.getConfig();
		FileConfiguration bannedcommands = ChatManager.settings.getBannedCommands();
		FileConfiguration messages = ChatManager.settings.getMessages();

		Player player = event.getPlayer();

		List<String> cmd = bannedcommands.getStringList("Banned-Commands");

		if (config.getBoolean("Banned_Commands.Enable")) {
			if (!player.hasPermission("chatmanager.bypass.bannedcommands")) {
				if (!config.getBoolean("Banned_Commands.Increase_Sensitivity")) {
					for (String command : cmd) {
						if (event.getMessage().toLowerCase().equals("/" + command)) {
							event.setCancelled(true);
							player.sendMessage(Methods.color(player, messages.getString("Banned_Commands.Message")
									.replace("{Prefix}", messages.getString("Message.Prefix"))
									.replace("{command}", command)));
							NotifyStaff(player, command);
							TellConsole(player, command);
							ExecuteCommand(player);
						}
					}
				} else {
					for (String command : cmd) {
						if (event.getMessage().toLowerCase().contains("/" + command)) {
							event.setCancelled(true);
							player.sendMessage(Methods.color(player, messages.getString("Banned_Commands.Message")
									.replace("{Prefix}", messages.getString("Message.Prefix"))
									.replace("{command}", command)));
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
					player.sendMessage(Methods.color(player, messages.getString("Banned_Commands.Message")
							.replace("{Prefix}", messages.getString("Message.Prefix")).replace("{command}", event.getMessage().replace("/", ""))));
					NotifyStaff(player, event.getMessage().replace("/", ""));
					TellConsole(player, event.getMessage().replace("/", ""));
					ExecuteCommand(player);
				}
			}
		}
	}

	public void NotifyStaff(Player player, String message) {
		
		FileConfiguration config = ChatManager.settings.getConfig();
		FileConfiguration messages = ChatManager.settings.getMessages();

		if (config.getBoolean("Banned_Commands.Notify_Staff")) {
			for (Player staff : Bukkit.getOnlinePlayers()) {
				if (staff.hasPermission("chatmanager.notify.bannedcommands")) {
					staff.sendMessage(Methods.color(player, messages.getString("Banned_Commands.Notify_Staff_Format")
							.replace("{player}", player.getName()).replace("{command}", message)
							.replace("{Prefix}", messages.getString("Message.Prefix"))));
				}
			}
		}
	}
	
	public void TellConsole(Player player, String message) {
		
		FileConfiguration config = ChatManager.settings.getConfig();
		FileConfiguration messages = ChatManager.settings.getMessages();
		
		if (config.getBoolean("Banned_Commands.Notify_Staff")) {
			Methods.tellConsole(Methods.color(player, messages.getString("Banned_Commands.Notify_Staff_Format")
					.replace("{player}", player.getName()).replace("{command}", message)
					.replace("{Prefix}", messages.getString("Message.Prefix"))));
		}
	}

	public void ExecuteCommand(Player player) {
		
		FileConfiguration config = ChatManager.settings.getConfig();
		
		if (config.getBoolean("Banned_Commands.Execute_Command")) {
			if (config.contains("Banned_Commands.Executed_Command")) {
				String command = config.getString("Banned_Commands.Executed_Command").replace("{player}", player.getName());
				List<String> commands = config.getStringList("Banned_Commands.Executed_Command");
				new BukkitRunnable() {
					public void run() {
						Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
						for (String cmd : commands) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd.replace("{player}", player.getName()));
						}
					}
				}.runTask(plugin);
			}
		}
	}
}