package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CommandToggleMentions extends Global implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);

			return true;
		}

		if (!cmd.getName().equalsIgnoreCase("togglementions")) return true;

		if (!player.hasPermission(Permissions.TOGGLE_MENTIONS.getNode())) {
			Messages.NO_PERMISSION.sendMessage(player);

			return true;
		}

		final UUID uuid = player.getUniqueId();

		if (args.length == 0) {
			if (this.toggleMentionsData.containsUser(uuid)) {
				this.toggleMentionsData.removeUser(uuid);

				Messages.TOGGLE_MENTIONS_DISABLED.sendMessage(player);

				return true;
			}

			this.toggleMentionsData.addUser(uuid);

			Messages.TOGGLE_MENTIONS_ENABLED.sendMessage(player);

			return true;
		}

		Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/togglementions");

		return true;
	}
}