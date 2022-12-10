package me.me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandSpy implements CommandExecutor {

	public CommandSpy(ChatManager plugin) {}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		FileConfiguration messages = ChatManager.settings.getMessages();

		if (!(sender instanceof Player)) {
			sender.sendMessage("Error: You can only use that command in game");
			return true;
		}

		Player player = (Player) sender;
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("CommandSpy")) {
				if (player.hasPermission("chatmanager.commandspy")) {
					if (args.length == 0) {
						if (Methods.cm_commandSpy.contains(player.getUniqueId())) {
							Methods.cm_commandSpy.remove(player.getUniqueId());
							player.sendMessage(Methods.color(player, messages.getString("Command_Spy.Disabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
						} else {
							Methods.cm_commandSpy.add(player.getUniqueId());
							player.sendMessage(Methods.color(player, messages.getString("Command_Spy.Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
						}
						return true;
					} else {
						player.sendMessage(Methods.color("&cCommand Usage: &7/Commandspy"));
					}
				} else {
					player.sendMessage(Methods.noPermission());
				}
			}
			
			if (cmd.getName().equalsIgnoreCase("SocialSpy")) {
				if (player.hasPermission("chatmanager.socialspy")) {
					if (args.length == 0) {
						if (Methods.cm_socialSpy.contains(player.getUniqueId())) {
							Methods.cm_socialSpy.remove(player.getUniqueId());
							player.sendMessage(Methods.color(player, messages.getString("Social_Spy.Disabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
						} else {
							Methods.cm_socialSpy.add(player.getUniqueId());
							player.sendMessage(Methods.color(player, messages.getString("Social_Spy.Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
						}
					return true;
					} else {
						player.sendMessage(Methods.color("&cCommand Usage: &7/Socialspy"));
					}
				} else {
					player.sendMessage(Methods.noPermission());
				}
			}
		}
		return true;
	}
}