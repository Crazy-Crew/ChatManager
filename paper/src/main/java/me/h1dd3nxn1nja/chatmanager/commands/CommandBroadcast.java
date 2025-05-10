package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandBroadcast extends Global implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		String broadcastPath = "Broadcast_Commands.Command.Broadcast.sound";
		String broadcastSound = config.getString(broadcastPath + ".value", "ENTITY_PLAYER_LEVELUP");
		int broadcastVolume = config.getInt(broadcastPath + ".volume", 10);
		int broadcastPitch = config.getInt(broadcastPath + ".pitch", 1);

		String announcementPath = "Broadcast_Commands.Command.Announcement.sound";
		String announcementSound = config.getString(announcementPath + ".value", "ENTITY_PLAYER_LEVELUP");
		int announcementVolume = config.getInt(announcementPath + ".volume", 10);
		int announcementPitch = config.getInt(announcementPath + ".pitch", 1);

		String warningPath = "Broadcast_Commands.Command.Warning.sound";
		String warningSound = config.getString(warningPath + ".value", "ENTITY_PLAYER_LEVELUP");
		int warningVolume = config.getInt(warningPath + ".volume", 10);
		int warningPitch = config.getInt(warningPath + ".pitch", 1);

		List<String> announcement = config.getStringList("Broadcast_Commands.Command.Announcement.Message");
		List<String> warning = config.getStringList("Broadcast_Commands.Command.Warning.Message");

		if (cmd.getName().equalsIgnoreCase("broadcast")) {
			if (sender.hasPermission(Permissions.COMMAND_BROADCAST.getNode())) {
				if (args.length != 0) {
					StringBuilder message = new StringBuilder();

					for (String arg : args) {
						message.append(arg).append(" ");
					}

					for (Player online : this.plugin.getServer().getOnlinePlayers()) {
						Methods.sendMessage(online, message.toString(), true);

						try {
							online.playSound(online.getLocation(), Sound.valueOf(broadcastSound), broadcastVolume, broadcastPitch);
						} catch (IllegalArgumentException ignored) {}
					}
				} else {
					Messages.INVALID_USAGE.sendMessage(sender, "{usage}", "/broadcast <message>");
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(sender);
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("announcement")) {
			if (sender.hasPermission(Permissions.COMMAND_ANNOUNCEMENT.getNode())) {
				if (args.length != 0) {
					sendBroadcast(sender, args, announcementSound, announcementVolume, announcementPitch, announcement);
				} else {
					Messages.INVALID_USAGE.sendMessage(sender, "{usage}", "/announcement <message>");
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(sender);
			}	
		}
		
		if (cmd.getName().equalsIgnoreCase("warning")) {
			if (sender.hasPermission(Permissions.COMMAND_WARNING.getNode())) {
				if (args.length != 0) {
					sendBroadcast(sender, args, warningSound, warningVolume, warningPitch, warning);
				} else {
					Messages.INVALID_USAGE.sendMessage(sender, "{usage}", "/warning <message>");
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(sender);
			}
		}

		return true;
	}

	private void sendBroadcast(@NotNull CommandSender sender, String[] args, String sound, int volume, int pitch, List<String> warning) {
		StringBuilder message = new StringBuilder();

		for (String arg : args) {
			message.append(arg).append(" ");
		}

		for (String announce : warning) {
			for (Player online : this.plugin.getServer().getOnlinePlayers()) {
				if (sender instanceof Player player) {
					online.sendMessage(Methods.placeholders(false, player, announce.replace("{player}", player.getName())).replace("{message}", message).replace("\\n", "\n"));
				}

				try {
					online.playSound(online.getLocation(), Sound.valueOf(sound), volume, pitch);
				} catch (IllegalArgumentException ignored) {}
			}

			if (sender instanceof ConsoleCommandSender) Methods.broadcast(null, announce.replace("{player}", sender.getName()).replace("{message}", message).replace("\\n", "\n"));
		}
	}
}