package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandPerWorldChat implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("perworldchat")) {
			if (player.hasPermission(Permissions.COMMAND_PERWORLDCHAT.getNode())) {
				if (args.length == 0) {
					Methods.sendMessage(player, "&cCommand Usage: &7/perworldchat bypass", true);

					return true;
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(player);

				return true;
			}

			if (args[0].equalsIgnoreCase("bypass")) {
				if (player.hasPermission(Permissions.COMMAND_PERWORLDCHAT.getNode())) {
					if (args.length == 1) {
						if (config.getBoolean("Per_World_Chat.Enable", false)) {
							if (!this.plugin.api().getPerWorldChatData().containsUser(player.getUniqueId())) {
								this.plugin.api().getPerWorldChatData().addUser(player.getUniqueId());

								Messages.PER_WORLD_CHAT_BYPASS_ENABLED.sendMessage(player);
							} else {
								this.plugin.api().getPerWorldChatData().removeUser(player.getUniqueId());

								Messages.PER_WORLD_CHAT_BYPASS_DISABLED.sendMessage(player);
							}
						} else {
							Methods.sendMessage(player, "&4Error: &cPer-world chat is currently disabled and you cannot execute that command at this time.", true);
						}
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/perworldchat bypass", true);
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);
				}
			}
		}

		return true;
	}
}