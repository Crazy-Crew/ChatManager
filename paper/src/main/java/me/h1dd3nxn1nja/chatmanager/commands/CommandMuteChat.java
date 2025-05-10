package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.api.objects.PaperServer;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.core.ServerState;
import com.ryderbelserion.chatmanager.managers.ServerManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandMuteChat extends Global implements CommandExecutor {



	private final ServerManager serverManager = this.plugin.getServerManager();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("mutechat")) {
			final PaperServer server = this.serverManager.getServer();

			if (player.hasPermission(Permissions.COMMAND_MUTECHAT.getNode())) {
				if (args.length == 0) {

					if (server.hasState(ServerState.MUTED)) {
						server.removeState(ServerState.MUTED);

						Messages.MUTE_CHAT_BROADCAST_MESSAGES_ENABLED.sendMessage(player, "{player}", player.getName());
					} else {
						server.addState(ServerState.MUTED);

						Messages.MUTE_CHAT_BROADCAST_MESSAGES_DISABLED.sendMessage(player, "{player}", player.getName());
					}

					return true;
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(player);

				return true;
			}

			if (args[0].equalsIgnoreCase("-s")) {
				if (player.hasPermission(Permissions.COMMAND_MUTECHAT_SILENT.getNode())) {
					if (args.length == 1) {
						if (server.hasState(ServerState.MUTED)) {
							server.removeState(ServerState.MUTED);

							for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode())) {
									Messages.MUTE_CHAT_BROADCAST_MESSAGES_ENABLED.sendMessage(player, "{player}", player.getName());

									return true;
								}
							}
						} else {
							server.addState(ServerState.MUTED);

							for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode())) {
									Messages.MUTE_CHAT_BROADCAST_MESSAGES_DISABLED.sendMessage(player, "{player}", player.getName());

									return true;
								}
							}
						}
					} else {
						Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/mutechat [-s]");
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);

					return true;
				}
			}
		}

		return true;
	}
}