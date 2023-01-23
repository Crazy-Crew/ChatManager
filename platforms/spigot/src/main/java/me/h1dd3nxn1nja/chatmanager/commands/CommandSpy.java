package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandSpy implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration messages = settingsManager.getMessages();

		if (!(sender instanceof Player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("CommandSpy")) {
			if (player.hasPermission("chatmanager.commandspy")) {
				if (args.length == 0) {
					if (Methods.cm_commandSpy.contains(player.getUniqueId())) {
						Methods.cm_commandSpy.remove(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Command_Spy.Disabled"), true);
					} else {
						Methods.cm_commandSpy.add(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Command_Spy.Enabled"), true);
					}

					return true;
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/Commandspy", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		if (cmd.getName().equalsIgnoreCase("SocialSpy")) {
			if (player.hasPermission("chatmanager.socialspy")) {
				if (args.length == 0) {
					if (Methods.cm_socialSpy.contains(player.getUniqueId())) {
						Methods.cm_socialSpy.remove(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Social_Spy.Disabled"), true);
					} else {
						Methods.cm_socialSpy.add(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Social_Spy.Enabled"), true);
					}

					return true;
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/Socialspy", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		return true;
	}
}