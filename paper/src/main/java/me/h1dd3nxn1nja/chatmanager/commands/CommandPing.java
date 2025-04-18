package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Messages;
import me.h1dd3nxn1nja.chatmanager.ChatManagerMercurioMC;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandPing implements CommandExecutor {

	@NotNull
	private final ChatManagerMercurioMC plugin = ChatManagerMercurioMC.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("ping")) {
			if (!player.hasPermission(Permissions.COMMAND_PING.getNode())) {
				Messages.NO_PERMISSION.sendMessage(player);

				return true;
			}

			if (args.length == 0) {
				Messages.PING_PLAYERS_PING.sendMessage(player);

				return true;
			}

			if (!player.hasPermission(Permissions.COMMAND_PING_OTHERS.getNode())) {
				Messages.NO_PERMISSION.sendMessage(player);

				return true;
			}

			if (args.length >= 2) {
				Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/ping [player]");

				return true;
			}

			Player target = this.plugin.getServer().getPlayer(args[0]);

			if (target == null || !target.isOnline()) {
				Messages.PLAYER_NOT_FOUND.sendMessage(player, "{target}", args[0]);

				return true;
			}

			Messages.PING_TARGETS_PING.sendMessage(player, "{target}", target.getName());

			return true;
		}

		return true;
	}
}