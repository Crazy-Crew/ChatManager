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
import java.text.DecimalFormat;

public class CommandPing implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		FileConfiguration messages = settingsManager.getMessages();
		DecimalFormat df = new DecimalFormat("#,###");
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Error: You can only use that command in game");
			return true;
		}

		Player player = (Player) sender;

		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("ping")) {
				if (args.length >= 2) {
					if ((player.hasPermission("chatmanager.ping")) || (player.hasPermission("chatmanager.ping.others"))) {
						player.sendMessage(Methods.color("&cCommand Usage: &7/Ping {player}"));
					} else {
						player.sendMessage(Methods.noPermission());
					}

					return true;
				}

				if (player.hasPermission("chatmanager.ping")) {
					if (args.length == 0) {
						player.sendMessage(Methods.color(messages.getString("Ping.Players_Ping", "{Prefix} &7Your current ping is &b{ping} ms.")
								.replace("{Prefix}", messages.getString("Message.Prefix"))
								.replace("{ping}", df.format(Ping.getPing((Player) sender)))));
						return true;
					}
				} else {
					player.sendMessage(Methods.noPermission());
				}

				if (player.hasPermission("chatmanager.ping.others")) {
					Player target = plugin.getServer().getPlayer(args[0]);
					if (target == null || !target.isOnline()) {
						player.sendMessage(Methods.color(messages.getString("Message.Player_Not_Found")
								.replace("{target}", args[0])
								.replace("{Prefix}", messages.getString("Message.Prefix"))));
						return true;
					}

					player.sendMessage(Methods.color(messages.getString("Ping.Targets_Ping", "{Prefix} &7{target}'s current ping is &b{ping} ms.")
							.replace("{Prefix}", messages.getString("Message.Prefix"))
							.replace("{target}", target.getName())
							.replace("{ping}", df.format(Ping.getPing((Player) target)))));
					return true;
				} else {
					player.sendMessage(Methods.noPermission());
				}
			}		
		}

		return true;
	}
}