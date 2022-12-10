package me.me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandClearChat implements CommandExecutor {

	public CommandClearChat(ChatManager plugin) {}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		FileConfiguration messages = ChatManager.settings.getMessages();
		
		if (cmd.getName().equalsIgnoreCase("ClearChat")) {
			if (sender.hasPermission("chatmanager.clearchat")) {
				if (args.length == 0) {
					for (Player members : Bukkit.getOnlinePlayers()) {
						if (!members.hasPermission("chatmanager.bypass.clearchat")) {
							sendClearMessage(members);
							if (sender instanceof Player) {
								Player player = (Player) sender;
								for (String broadcastMessage : messages.getStringList("Clear_Chat.Broadcast_Message")) {
									members.sendMessage(Methods.color(player, broadcastMessage.replace("{player}", player.getName())));
								}
							} else if (sender instanceof ConsoleCommandSender) {
								for (String broadcastMessage : messages.getStringList("Clear_Chat.Broadcast_Message")) {
									members.sendMessage(Methods.color(broadcastMessage.replace("{player}", sender.getName())));
								}
								sender.sendMessage(Methods.color(messages.getString("Clear_Chat.Staff_Message")
										.replace("{player}", sender.getName()).replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						} else {
							members.sendMessage(Methods.color(messages.getString("Clear_Chat.Staff_Message")
									.replace("{player}", sender.getName()).replace("{Prefix}", messages.getString("Message.Prefix"))));
						}
					}
				} else {
					sender.sendMessage(Methods.color("&cCommand Usage: &7/Clearchat"));
				}
			} else {
				sender.sendMessage(Methods.noPermission());
			}
		}
		return true;
	}

	public void sendClearMessage(CommandSender sender) {
		FileConfiguration config = ChatManager.settings.getConfig();
		int lines = config.getInt("Clear_Chat.Broadcasted_Lines");
		
		for (int i = 0; i < lines; i++) {
			sender.sendMessage("");
		}
	}
}