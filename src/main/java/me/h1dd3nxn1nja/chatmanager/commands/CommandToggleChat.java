package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandToggleChat implements CommandExecutor {

	public CommandToggleChat(ChatManager plugin) {}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		FileConfiguration messages = ChatManager.settings.getMessages();
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Error: You can only use that command in game");
			return true;
		}
		
		Player player = (Player) sender;
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("toggleChat")) {
				if (player.hasPermission("chatmanager.toggle.chat")) {
					if (args.length == 0) {
						if (Methods.cm_toggleChat.contains(player.getUniqueId())) {
							Methods.cm_toggleChat.remove(player.getUniqueId());
							player.sendMessage(PlaceholderManager.setPlaceholders(player, messages.getString("Toggle_Chat.Disabled")));
						} else {
							Methods.cm_toggleChat.add(player.getUniqueId());
							player.sendMessage(PlaceholderManager.setPlaceholders(player, messages.getString("Toggle_Chat.Enabled")));
						}
						return true;
					} else {
						player.sendMessage(Methods.color("&cCommand Usage: &7/Togglechat"));
					}
				} else {
					player.sendMessage(Methods.noPermission());
				}
			}
		}
		return true;
	}
}
