package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;

public class CommandRules implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = settingsManager.getConfig();

		if (cmd.getName().equalsIgnoreCase("rules")) {
			if (sender.hasPermission("chatmanager.rules")) {
				if (args.length == 0) {
					for (String rules : config.getStringList("Server_Rules.Rules.1")) {
						Methods.sendMessage(sender, rules, true);
					}
				} else if (args.length == 1) {
					int page = Integer.parseInt(args[0]);

					for (String rules : config.getStringList("Server_Rules.Rules." + page)) {
						Methods.sendMessage(sender, rules, true);
					}
				} else {
					Methods.sendMessage(sender, "&cCommand Usage: &7/Rules <page>", true);
				}
			} else {
				Methods.sendMessage(sender, Methods.noPermission(), true);
			}
		}

		return true;
	}
}