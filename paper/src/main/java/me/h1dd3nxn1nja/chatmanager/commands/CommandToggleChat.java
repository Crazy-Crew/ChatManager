package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandToggleChat implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = settingsManager.getMessages();

		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (!cmd.getName().equalsIgnoreCase("toggleChat")) return true;

		if (!player.hasPermission("chatmanager.toggle.chat")) {
			Methods.sendMessage(player, Methods.noPermission(), true);
			return true;
		}

		if (args.length == 0) {
			if (plugin.api().getToggleChatData().containsUser(player.getUniqueId())) {
				plugin.api().getToggleChatData().removeUser(player.getUniqueId());
				Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Chat.Disabled")), true);
				return true;
			}

			plugin.api().getToggleChatData().addUser(player.getUniqueId());
			Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Chat.Enabled")), true);

			return true;
		}

		Methods.sendMessage(player, "&cCommand Usage: &7/Togglechat", true);

		return true;
	}
}