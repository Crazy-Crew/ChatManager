package me.h1dd3nxn1nja.chatmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import me.h1dd3nxn1nja.chatmanager.Main;
import me.h1dd3nxn1nja.chatmanager.Methods;

public class CommandRules implements CommandExecutor {

	public CommandRules(Main plugin) {}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		FileConfiguration config = Main.settings.getConfig();

		if (cmd.getName().equalsIgnoreCase("rules")) {
			if (sender.hasPermission("chatmanager.rules")) {
				if (args.length == 0) {
					for (String rules : config.getStringList("Server_Rules.Rules.1")) {
						sender.sendMessage(Methods.color(rules));
					}
				} else if (args.length == 1) {
					int page = Integer.parseInt(args[0]);
					for (String rules : config.getStringList("Server_Rules.Rules." + page)) {
						sender.sendMessage(Methods.color(rules));
					}
				} else {
					sender.sendMessage(Methods.color("&cCommand Usage: &7/Rules <page>"));
				}
			} else {
				sender.sendMessage(Methods.noPermission());
			}
		}
		return true;
	}
}