package me.h1dd3nxn1nja.chatmanager.paper.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import org.jetbrains.annotations.NotNull;

public class CommandToggleMentions implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (!cmd.getName().equalsIgnoreCase("togglementions")) return true;

		if (!player.hasPermission("chatmanager.toggle.mentions")) {
			Methods.sendMessage(player, Methods.noPermission(), true);
			return true;
		}

		if (args.length == 0) {
			if (plugin.api().getToggleMentionsData().containsUser(player.getUniqueId())) {
				plugin.api().getToggleMentionsData().removeUser(player.getUniqueId());
				Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Mentions.Disabled")), true);
				return true;
			}

			plugin.api().getToggleMentionsData().addUser(player.getUniqueId());
			Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Mentions.Enabled")), true);

			return true;
		}

		Methods.sendMessage(player, "&cCommand Usage: &7/ToggleMentions", true);

		return true;
	}
}