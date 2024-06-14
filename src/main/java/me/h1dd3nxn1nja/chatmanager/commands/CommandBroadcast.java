package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import java.util.List;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandBroadcast implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@NotNull
	private final PlaceholderManager placeholderManager = this.plugin.getCrazyManager().getPlaceholderManager();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getFile();

		String prefix = config.getString("Broadcast_Commands.Command.Broadcast.Prefix");
		String color = config.getString("Broadcast_Commands.Command.Broadcast.Default_Color");

		String broadcastPath = "Broadcast_Commands.Command.Broadcast.sound";
		String broadcastSound = config.getString(broadcastPath + ".value");
		int broadcastVolume = config.contains(broadcastPath + ".volume") ? config.getInt(broadcastPath + ".volume") : 10;
		int broadcastPitch = config.contains(broadcastPath + ".pitch") ? config.getInt(broadcastPath + ".pitch") : 1;

		String announcementPath = "Broadcast_Commands.Command.Announcement.sound";
		String announcementSound = config.getString(announcementPath + ".value");
		int announcementVolume = config.contains(announcementPath + ".volume") ? config.getInt(announcementPath + ".volume") : 10;
		int announcementPitch = config.contains(announcementPath + ".pitch") ? config.getInt(announcementPath + ".pitch") : 1;

		String warningPath = "Broadcast_Commands.Command.Warning.sound";
		String warningSound = config.getString(warningPath + ".value");
		int warningVolume = config.contains(warningPath + ".volume") ? config.getInt(warningPath + ".volume") : 10;
		int warningPitch = config.contains(warningPath + ".pitch") ? config.getInt(warningPath + ".pitch") : 1;

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
						online.sendMessage(this.plugin.getMethods().color(prefix + color + message));
						try {
							online.playSound(online.getLocation(), Sound.valueOf(broadcastSound), broadcastVolume, broadcastPitch);
						} catch (IllegalArgumentException ignored) {}
					}
				} else {
					this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/Broadcast <message>", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("announcement")) {
			if (sender.hasPermission(Permissions.COMMAND_ANNOUNCEMENT.getNode())) {
				if (args.length != 0) {
					sendBroadcast(sender, args, announcementSound, announcementVolume, announcementPitch, announcement);
				} else {
					this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/Announcement <message>", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
			}	
		}
		
		if (cmd.getName().equalsIgnoreCase("warning")) {
			if (sender.hasPermission(Permissions.COMMAND_WARNING.getNode())) {
				if (args.length != 0) {
					sendBroadcast(sender, args, warningSound, warningVolume, warningPitch, warning);
				} else {
					this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/Warning <message>", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
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
					online.sendMessage(this.placeholderManager.setPlaceholders(player, announce.replace("{player}", player.getName()).replace("{message}", message).replace("\\n", "\n")));
				}

				try {
					online.playSound(online.getLocation(), Sound.valueOf(sound), volume, pitch);
				} catch (IllegalArgumentException ignored) {}
			}

			if (sender instanceof ConsoleCommandSender) this.plugin.getMethods().broadcast(announce.replace("{player}", sender.getName()).replace("{message}", message).replace("\\n", "\n"));
		}
	}
}