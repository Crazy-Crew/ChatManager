package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Messages;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSpy implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);

			return true;
		}

		if (cmd.getName().equalsIgnoreCase("commandspy")) {
			if (player.hasPermission(Permissions.COMMAND_SPY.getNode())) {
				if (args.length == 0) {

					boolean isValid = this.plugin.api().getCommandSpyData().containsUser(player.getUniqueId());

					if (isValid) {
						this.plugin.api().getCommandSpyData().removeUser(player.getUniqueId());

						Messages.COMMAND_SPY_DISABLED.sendMessage(player);

						return true;
					}

					this.plugin.api().getCommandSpyData().addUser(player.getUniqueId());

					Messages.COMMAND_SPY_ENABLED.sendMessage(player);

					return true;
				} else {
					Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/commandspy");
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(player);
			}
		}

		if (cmd.getName().equalsIgnoreCase("socialspy")) {
			if (player.hasPermission(Permissions.SOCIAL_SPY.getNode())) {
				if (args.length == 0) {
					boolean isValid = this.plugin.api().getSocialSpyData().containsUser(player.getUniqueId());

					if (isValid) {
						this.plugin.api().getSocialSpyData().removeUser(player.getUniqueId());

						Messages.SOCIAL_SPY_DISABLED.sendMessage(player);

						return true;
					}

					this.plugin.api().getSocialSpyData().addUser(player.getUniqueId());

					Messages.SOCIAL_SPY_ENABLED.sendMessage(player);

					return true;
				} else {
					Messages.INVALID_USAGE.sendMessage(player, "{usage}", "/socialspy");
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(player);
			}
		}

		return true;
	}
}