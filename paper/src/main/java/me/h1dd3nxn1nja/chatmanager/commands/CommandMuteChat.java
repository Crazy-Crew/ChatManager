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

public class CommandMuteChat implements CommandExecutor {

	@NotNull
	private final ChatManagerMercurioMC plugin = ChatManagerMercurioMC.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("mutechat")) {
			if (player.hasPermission(Permissions.COMMAND_MUTECHAT.getNode())) {
				if (args.length == 0) {
					if (Methods.isMuted()) {
						Methods.setMuted();

						Messages.MUTE_CHAT_BROADCAST_MESSAGES_ENABLED.sendMessage(player, "{player}", player.getName());
					} else {
						Methods.setMuted();

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
						if (Methods.isMuted()) {
							Methods.setMuted();

							for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode())) {
									Messages.MUTE_CHAT_BROADCAST_MESSAGES_ENABLED.sendMessage(player, "{player}", player.getName());

									return true;
								}
							}

						} else {
							Methods.setMuted();

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