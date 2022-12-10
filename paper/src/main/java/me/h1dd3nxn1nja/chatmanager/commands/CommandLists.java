package me.h1dd3nxn1nja.chatmanager.commands;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;

public class CommandLists implements CommandExecutor {
	
	public CommandLists(ChatManager plugin) {}
	
	DecimalFormat df = new DecimalFormat("#,###");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		FileConfiguration config = ChatManager.settings.getConfig();
		
		if (cmd.getName().equalsIgnoreCase("List")) {
			if (sender.hasPermission("chatmanager.lists.players")) {
				if (args.length == 0) {
					StringBuilder str = new StringBuilder();
					for (Player players : Bukkit.getOnlinePlayers()) {
						if (str.length() > 0) {
							str.append(", ");
						}
						str.append("&a" + players.getName() + "&8");
					}
					if (sender instanceof Player) {
						for (String list : config.getStringList("Lists.Player_List")) {
							Player player = (Player) sender;
							sender.sendMessage(PlaceholderManager.setPlaceholders(player, list.replace("{players}", str.toString())
								.replace("{server_online}", df.format(Bukkit.getServer().getOnlinePlayers().size()))
								.replace("{server_max_players}", df.format(Bukkit.getServer().getMaxPlayers()))));
						}
					} else {
						for (String list : config.getStringList("Lists.Player_List")) {
							sender.sendMessage(Methods.color(list.replace("{players}", str.toString())
									.replace("{server_online}", df.format(Bukkit.getServer().getOnlinePlayers().size()))
									.replace("{server_max_players}", df.format(Bukkit.getServer().getMaxPlayers()))));
						}
					}
				} else {
					sender.sendMessage(Methods.color("&cCommand Usage: &7/List"));
				}
			} else {
				sender.sendMessage(Methods.noPermission());
			}
		}
		if (cmd.getName().equalsIgnoreCase("Staff")) {
			if (sender.hasPermission("chatmanager.lists.staff")) {
				if (args.length == 0) {
					StringBuilder str = new StringBuilder();
					for (Player staff : Bukkit.getOnlinePlayers()) {
						if ((staff.hasPermission("chatmanager.staff")) || (staff.isOp())) {
							if (str.length() > 0) {
								str.append(", ");
							}
							str.append("&a" + staff.getName() + "&8");
						}
					}
					if (sender instanceof Player) {
						for (String list : config.getStringList("Lists.Staff_List")) {
							Player player = (Player) sender;
							sender.sendMessage(PlaceholderManager.setPlaceholders(player, list.replace("{staff}", str.toString())
									.replace("{server_online}", df.format(Bukkit.getServer().getOnlinePlayers().size()))
									.replace("{server_max_players}", df.format(Bukkit.getServer().getMaxPlayers()))));
						}
					} else {
						for (String list : config.getStringList("Lists.Staff_List")) {
							sender.sendMessage(Methods.color(list.replace("{staff}", str.toString())
									.replace("{server_online}", df.format(Bukkit.getServer().getOnlinePlayers().size()))
									.replace("{server_max_players}", df.format(Bukkit.getServer().getMaxPlayers()))));
						}
					}
				} else {
					sender.sendMessage(Methods.color("&cCommand Usage: &7/Staff"));
				}
			} else {
				sender.sendMessage(Methods.noPermission());
			}
		}
		return true;
	}
}