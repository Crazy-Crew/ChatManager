package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.Main;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandMuteChat implements CommandExecutor {

	public CommandMuteChat(Main plugin) {}

	public static boolean muted;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		FileConfiguration messages = Main.settings.getMessages();
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Error: You can only use that command in game");
			return true;
		}
		
		Player player = (Player) sender;
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("MuteChat")) {
				if (player.hasPermission("chatmanager.mutechat")) {
					if (args.length == 0) {
						if (muted) {
							muted = false;
							Bukkit.broadcastMessage(PlaceholderManager.setPlaceholders(player,
									messages.getString("Mute_Chat.Broadcast_Messages.Enabled")
											.replace("{player}", sender.getName())
											.replace("{Prefix}", messages.getString("Message.Prefix"))));
							return true;
	
						} else {
	
							muted = true;
							Bukkit.broadcastMessage(PlaceholderManager.setPlaceholders(player,
									messages.getString("Mute_Chat.Broadcast_Messages.Disabled")
											.replace("{player}", sender.getName())
											.replace("{Prefix}", messages.getString("Message.Prefix"))));
							return true;
						}
					}
				} else {
					player.sendMessage(Methods.noPermission());
					return true;
				}
				if (args[0].equalsIgnoreCase("-s")) {
					if (player.hasPermission("chatmanager.mutechat.silent")) {
						if (args.length == 1) {
							if (muted) {
								muted = false;
								for (Player staff : Bukkit.getOnlinePlayers()) {
									if (staff.hasPermission("chatmanager.bypass.mutechat")) {
										staff.sendMessage(PlaceholderManager.setPlaceholders(player,
												messages.getString("Mute_Chat.Broadcast_Messages.Enabled")
														.replace("{player}", sender.getName())
														.replace("{Prefix}", messages.getString("Message.Prefix"))));
										return true;
									}
								}
	
							} else {
	
								muted = true;
								for (Player staff : Bukkit.getOnlinePlayers()) {
									if (staff.hasPermission("chatmanager.bypass.mutechat")) {
										staff.sendMessage(PlaceholderManager.setPlaceholders(player,
												messages.getString("Mute_Chat.Broadcast_Messages.Disabled")
														.replace("{player}", sender.getName())
														.replace("{Prefix}", messages.getString("Message.Prefix"))));
										return true;
									}
								}
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/Mutechat [-s]"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
						return true;
					}
				}
			}
		}
		return true;
	}
}