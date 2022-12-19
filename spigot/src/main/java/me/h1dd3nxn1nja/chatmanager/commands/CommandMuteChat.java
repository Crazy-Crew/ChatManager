package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandMuteChat implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	public static boolean muted;

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = settingsManager.getMessages();
		
		if (!(sender instanceof Player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}
		
		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("MuteChat")) {
			if (player.hasPermission("chatmanager.mutechat")) {
				if (args.length == 0) {
					if (muted) {
						muted = false;
						Methods.broadcast(placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Enabled").replace("{player}", player.getName())));
						return true;
					} else {
						muted = true;
						Methods.broadcast(placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Disabled").replace("{player}", player.getName())));
						return true;
					}
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
				return true;
			}

			if (args[0].equalsIgnoreCase("-s")) {
				if (player.hasPermission("chatmanager.mutechat.silent")) {
					if (args.length == 1) {
						if (muted) {
							muted = false;
							for (Player staff : plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission("chatmanager.bypass.mutechat")) {
									Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Enabled").replace("{player}", player.getName())), true);
									return true;
								}
							}

						} else {
							muted = true;
							for (Player staff : plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission("chatmanager.bypass.mutechat")) {
									Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Disabled").replace("{player}", player.getName())), true);
									return true;
								}
							}
						}
					} else {
						Methods.sendMessage(player, "&cCommand Usage: &7/Mutechat [-s]", true);
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