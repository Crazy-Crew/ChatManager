package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.FileManager.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import java.util.List;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandBroadcast implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getFile();

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

					for (String arg : args) {
						message.append(arg).append(" ");
					}

					for (Player online : plugin.getServer().getOnlinePlayers()) {
						online.sendMessage(Methods.color(prefix + color + message));
						try {
							online.playSound(online.getLocation(), Sound.valueOf(broadcastSound), 10, 1);
						} catch (IllegalArgumentException ignored) {}
					}
				} else {
					Methods.sendMessage(sender, "&cCommand Usage: &7/Broadcast <message>", true);
				}
			} else {
				Methods.sendMessage(sender, Methods.noPermission(), true);
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("Announcement")) {
			if (sender.hasPermission("chatmanager.announcement")) {
				if (args.length != 0) {
					sendBroadcast(sender, args, announcementSound, announcement);
				} else {
					Methods.sendMessage(sender, "&cCommand Usage: &7/Announcement <message>", true);
				}
			} else {
				Methods.sendMessage(sender, Methods.noPermission(), true);
			}	
		}
		
		if (cmd.getName().equalsIgnoreCase("Warning")) {
			if (sender.hasPermission("chatmanager.warning")) {
				if (args.length != 0) {
					sendBroadcast(sender, args, warningSound, warning);
				} else {
					Methods.sendMessage(sender, "&cCommand Usage: &7/Warning <message>", true);
				}
			} else {
				Methods.sendMessage(sender, Methods.noPermission(), true);
			}
		}

		return true;
	}

	private void sendBroadcast(@NotNull CommandSender sender, String[] args, String warningSound, List<String> warning) {
		StringBuilder message = new StringBuilder();

		for (String arg : args) {
			message.append(arg).append(" ");
		}

		for (String announce : warning) {
			for (Player online : plugin.getServer().getOnlinePlayers()) {
				if (sender instanceof Player player) {
					online.sendMessage(placeholderManager.setPlaceholders(player, announce.replace("{player}", player.getName()).replace("{message}", message).replace("\\n", "\n")));
				}

				try {
					online.playSound(online.getLocation(), Sound.valueOf(warningSound), 10, 1);
				} catch (IllegalArgumentException ignored) {}
			}

			if (sender instanceof ConsoleCommandSender) Methods.broadcast(announce.replace("{player}", sender.getName()).replace("{message}", message).replace("\\n", "\n"));
		}
	}
}