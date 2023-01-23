package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;

public class CommandToggleMentions implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration messages = settingsManager.getMessages();

		if (!(sender instanceof Player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("togglementions")) {
			if (player.hasPermission("chatmanager.toggle.mentions")) {
				if (args.length == 0) {
					if (Methods.cm_toggleMentions.contains(player.getUniqueId())) {
						Methods.cm_toggleMentions.remove(player.getUniqueId());
						Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Mentions.Disabled")), true);
					} else {
						Methods.cm_toggleMentions.add(player.getUniqueId());
						Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Mentions.Enabled")), true);
					}
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/ToggleMentions", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		return true;
	}
}