package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import com.ryderbelserion.chatmanager.enums.Files;

public class CommandMuteChat implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		if (cmd.getName().equalsIgnoreCase("mutechat")) {
			if (player.hasPermission(Permissions.COMMAND_MUTECHAT.getNode())) {
				if (args.length == 0) {
					if (Methods.isMuted()) {
						Methods.setMuted();
						Methods.broadcast(player, messages.getString("Mute_Chat.Broadcast_Messages.Enabled").replace("{player}", player.getName()));
					} else {
						Methods.setMuted();
						Methods.broadcast(player, messages.getString("Mute_Chat.Broadcast_Messages.Disabled").replace("{player}", player.getName()));
					}

					return true;
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
				return true;
			}

			if (args[0].equalsIgnoreCase("-s")) {
				if (player.hasPermission(Permissions.COMMAND_MUTECHAT_SILENT.getNode())) {
					if (args.length == 1) {
						if (Methods.isMuted()) {
							Methods.setMuted();
							for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode())) {
									Methods.sendMessage(player, messages.getString("Mute_Chat.Broadcast_Messages.Enabled").replace("{player}", player.getName()), true);
									return true;
								}
							}

						} else {
							Methods.setMuted();

							for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode())) {
									Methods.sendMessage(player, messages.getString("Mute_Chat.Broadcast_Messages.Disabled").replace("{player}", player.getName()), true);
									return true;
								}
							}
						}
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/mutechat [-s]", true);
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
					return true;
				}
			}
		}

		return true;
	}
}