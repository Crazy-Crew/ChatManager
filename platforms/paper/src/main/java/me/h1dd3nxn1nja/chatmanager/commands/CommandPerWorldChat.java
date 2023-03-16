package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandPerWorldChat implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("perworldchat")) {
			if (player.hasPermission("chatmanager.perworldchat")) {
				if (args.length == 0) {
					Methods.sendMessage(player, "&cCommand Usage: &7/Perworldchat bypass", true);
					return true;
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
				return true;
			}

			if (args[0].equalsIgnoreCase("bypass")) {
				if (player.hasPermission("chatmanager.perworldchat")) {
					if (args.length == 1) {
						if (config.getBoolean("Per_World_Chat.Enable")) {
							if (!Methods.cm_pwcGlobal.contains(player.getUniqueId())) {
								Methods.cm_pwcGlobal.add(player.getUniqueId());
								Methods.sendMessage(player, messages.getString("Per_World_Chat.Bypass_Enabled"), true);
							} else {
								Methods.cm_pwcGlobal.remove(player.getUniqueId());
								Methods.sendMessage(player, messages.getString("Per_World_Chat.Bypass_Disabled"), true);
							}
						} else {
							Methods.sendMessage(player, "&4Error: &cPer-world chat is currently disabled and you cannot execute that command at this time.", true);
						}
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/Perworldchat bypass", true);
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}
		}

		return true;
	}
}