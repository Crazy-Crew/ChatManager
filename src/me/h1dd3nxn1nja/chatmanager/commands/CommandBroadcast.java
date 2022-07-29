package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.Main;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandBroadcast implements CommandExecutor {

	public CommandBroadcast(Main plugin) {}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		FileConfiguration config = Main.settings.getConfig();
		
		String broadcastSound = config.getString("Broadcast_Commands.Command.Broadcast.Sound");
		String announcementSound = config.getString("Broadcast_Commands.Command.Announcement.Sound");
		String warningSound = config.getString("Broadcast_Commands.Command.Warning.Sound");
		String prefix = config.getString("Broadcast_Commands.Command.Broadcast.Prefix");
		String color = config.getString("Broadcast_Commands.Command.Broadcast.Default_Color");
		List<String> announcement = config.getStringList("Broadcast_Commands.Command.Announcement.Message");
		List<String> warning = config.getStringList("Broadcast_Commands.Command.Warning.Message");

		if (cmd.getName().equalsIgnoreCase("broadcast")) {
			if (sender.hasPermission("chatmanager.broadcast")) {
				if (args.length != 0) {
					StringBuilder message = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						message.append(args[i] + " ");
					}
					for (Player online : Bukkit.getServer().getOnlinePlayers()) {
						online.sendMessage(Methods.color(prefix + color + message));
						try {
							online.playSound(online.getLocation(), Sound.valueOf(broadcastSound), 10, 1);
						} catch (IllegalArgumentException ignored) {
						}
					}
				} else {
					sender.sendMessage(Methods.color("&cCommand Usage: &7/Broadcast <message>"));
				}
			} else {
				sender.sendMessage(Methods.noPermission());
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("Announcement")) {
			if (sender.hasPermission("chatmanager.announcement")) {
				if (args.length != 0) {
					StringBuilder message = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						message.append(args[i] + " ");
					}
					for (String announce : announcement) {
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							if (sender instanceof Player) {
								Player player = (Player) sender;
								online.sendMessage(PlaceholderManager.setPlaceholders(player, announce.replace("{player}", player.getName()).replace("{message}", message).replace("\\n", "\n")));
							}
							try {
								online.playSound(online.getLocation(), Sound.valueOf(announcementSound), 10, 1);
							} catch (IllegalArgumentException ignored) {}
						}
						if (sender instanceof ConsoleCommandSender) {
							Bukkit.broadcastMessage(Methods.color(announce.replace("{player}", sender.getName()).replace("{message}", message).replace("\\n", "\n")));
						}
					}
				} else {
					sender.sendMessage(Methods.color("&cCommand Usage: &7/Announcement <message>"));
				}
			} else {
				sender.sendMessage(Methods.noPermission());
			}	
		}
		
		if (cmd.getName().equalsIgnoreCase("Warning")) {
			if (sender.hasPermission("chatmanager.warning")) {
				if (args.length != 0) {
					StringBuilder message = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						message.append(args[i] + " ");
					}
					for (String announce : warning) {
						for (Player online : Bukkit.getServer().getOnlinePlayers()) {
							if (sender instanceof Player) {
								Player player = (Player) sender;
								online.sendMessage(PlaceholderManager.setPlaceholders(player, announce.replace("{player}", player.getName()).replace("{message}", message).replace("\\n", "\n")));
							}
							try {
								online.playSound(online.getLocation(), Sound.valueOf(warningSound), 10, 1);
							} catch (IllegalArgumentException ignored) {
							}
						}
						if (sender instanceof ConsoleCommandSender) {
							Bukkit.broadcastMessage(Methods.color(announce.replace("{player}", sender.getName()).replace("{message}", message).replace("\\n", "\n")));
						}
					}
				} else {
					sender.sendMessage(Methods.color("&cCommand Usage: &7/Warning <message>"));
				}
			} else {
				sender.sendMessage(Methods.noPermission());
			}
		}
		return true;
	}
}