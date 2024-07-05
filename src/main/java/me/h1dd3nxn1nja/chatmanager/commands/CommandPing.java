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

public class CommandPing implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = Files.MESSAGES.getConfiguration();
		
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("ping")) {
			if (!player.hasPermission(Permissions.COMMAND_PING.getNode())) {
				Methods.sendMessage(player, Methods.noPermission(), true);
				return true;
			}

			if (args.length == 0) {
				Methods.sendMessage(player, messages.getString("Ping.Players_Ping"), true);
				return true;
			}

			if (!player.hasPermission(Permissions.COMMAND_PING_OTHERS.getNode())) {
				Methods.sendMessage(player, Methods.noPermission(), true);
				return true;
			}

			if (args.length >= 2) {
				Methods.sendMessage(player, "&cCommand Usage: &7/ping [player]", true);
				return true;
			}

			Player target = this.plugin.getServer().getPlayer(args[0]);

			if (target == null || !target.isOnline()) {
				Methods.sendMessage(player, messages.getString("Message.Player_Not_Found").replace("{target}", args[0]), true);
				return true;
			}

			Methods.sendMessage(player, messages.getString("Ping.Targets_Ping").replace("{target}", target.getName()), true);
			return true;
		}

		return true;
	}
}