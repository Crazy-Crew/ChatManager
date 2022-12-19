package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.utils.Ping;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.text.DecimalFormat;

public class CommandPing implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = settingsManager.getMessages();
		DecimalFormat df = new DecimalFormat("#,###");
		
		if (!(sender instanceof Player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("ping")) {
			if (args.length >= 2) {
				if ((player.hasPermission("chatmanager.ping")) || (player.hasPermission("chatmanager.ping.others"))) {
					Methods.sendMessage(player, "&cCommand Usage: &7/Ping {player}", true);
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}

				return true;
			}

			if (player.hasPermission("chatmanager.ping")) {
				if (args.length == 0) {
					Methods.sendMessage(player, messages.getString("Ping.Players_Ping").replace("{ping}", df.format(Ping.getPing((Player) sender))), true);
					return true;
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}

			if (player.hasPermission("chatmanager.ping.others")) {
				Player target = plugin.getServer().getPlayer(args[0]);

				if (target == null || !target.isOnline()) {
					Methods.sendMessage(player, messages.getString("Message.Player_Not_Found").replace("{target}", args[0]), true);
					return true;
				}

				Methods.sendMessage(player, messages.getString("Ping.Targets_Ping").replace("{target}", target.getName()).replace("{ping}", df.format(Ping.getPing(target))), true);
				return true;
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		return true;
	}
}