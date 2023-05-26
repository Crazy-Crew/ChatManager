package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSpy implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = settingsManager.getMessages();

		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("CommandSpy")) {
			if (player.hasPermission("chatmanager.commandspy")) {
				if (args.length == 0) {

					boolean isValid = plugin.getCrazyManager().api().getCommandSpyData().containsUser(player.getUniqueId());

					if (isValid) {
						plugin.getCrazyManager().api().getCommandSpyData().removeUser(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Command_Spy.Disabled"), true);
						return true;
					}

					plugin.getCrazyManager().api().getCommandSpyData().addUser(player.getUniqueId());
					Methods.sendMessage(player, messages.getString("Command_Spy.Enabled"), true);

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
					boolean isValid = plugin.getCrazyManager().api().getSocialSpyData().containsUser(player.getUniqueId());

					if (isValid) {
						plugin.getCrazyManager().api().getSocialSpyData().removeUser(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("Social_Spy.Disabled"), true);
						return true;
					}

					plugin.getCrazyManager().api().getSocialSpyData().addUser(player.getUniqueId());
					Methods.sendMessage(player, messages.getString("Social_Spy.Enabled"), true);

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