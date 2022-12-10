package me.me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandPerWorldChat implements CommandExecutor {

	public CommandPerWorldChat(ChatManager plugin) {}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		FileConfiguration config = ChatManager.settings.getConfig();
		FileConfiguration messages = ChatManager.settings.getMessages();

		if (!(sender instanceof Player)) {
			sender.sendMessage("Error: You can only use that command in game");
			return true;
		}
		
		Player player = (Player) sender;
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("perworldchat")) {
				if (player.hasPermission("chatmanager.perworldchat")) {
					if (args.length == 0) {
						player.sendMessage(Methods.color("&cCommand Usage: &7/Perworldchat bypass"));
						return true;
					}
				} else {
					player.sendMessage(Methods.noPermission());
					return true;
				}
				if (args[0].equalsIgnoreCase("bypass")) {
					if (player.hasPermission("chatmanager.perworldchat")) {
						if (args.length == 1) {
							if (config.getBoolean("Per_World_Chat.Enable")) {
								if (!Methods.cm_pwcGlobal.contains(player.getUniqueId())) {
									Methods.cm_pwcGlobal.add(player.getUniqueId());
									player.sendMessage(Methods.color(player, messages.getString("Per_World_Chat.Bypass_Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
								} else {
									Methods.cm_pwcGlobal.remove(player.getUniqueId());
									player.sendMessage(Methods.color(player, messages.getString("Per_World_Chat.Bypass_Disabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
								}
							} else {
								player.sendMessage(Methods.color("&4Error: &cPer-world chat is currently disabled and you cannot execute that command at this time."));
							}
						} else {
							player.sendMessage(Methods.color("&cCommand Usage: &7/Perworldchat bypass"));
						}
					} else {
						player.sendMessage(Methods.noPermission());
					}
				}
			}
		}
		return true;
	}
}