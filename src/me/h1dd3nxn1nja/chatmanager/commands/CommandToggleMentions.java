package me.h1dd3nxn1nja.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.h1dd3nxn1nja.chatmanager.Main;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;

public class CommandToggleMentions implements CommandExecutor {
	
	public CommandToggleMentions(Main plugin) {}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		FileConfiguration messages = Main.settings.getMessages();
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Error: You can only use that command in game");
			return true;
		}
		
		Player player = (Player)sender;
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("togglementions")) {
				if (player.hasPermission("chatmanager.toggle.mentions")) {
					if (args.length == 0) {
						if (Methods.cm_toggleMentions.contains(player.getUniqueId())) {
							Methods.cm_toggleMentions.remove(player.getUniqueId());
							player.sendMessage(PlaceholderManager.setPlaceholders(player, messages.getString("Toggle_Mentions.Disabled")));
						} else {
							Methods.cm_toggleMentions.add(player.getUniqueId());
							player.sendMessage(PlaceholderManager.setPlaceholders(player, messages.getString("Toggle_Mentions.Enabled")));
						}
					} else {
						player.sendMessage(Methods.color("&cCommand Usage: &7/ToggleMentions"));
					}
				} else {
					player.sendMessage(Methods.noPermission());
				}
			}
		}
		return true;
	}
}