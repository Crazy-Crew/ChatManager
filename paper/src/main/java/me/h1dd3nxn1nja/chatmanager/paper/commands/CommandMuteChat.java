package me.h1dd3nxn1nja.chatmanager.paper.commands;

import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import com.ryderbelserion.chatmanager.paper.FileManager.Files;

public class CommandMuteChat implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();
	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		FileConfiguration messages = Files.MESSAGES.getFile();

		if (cmd.getName().equalsIgnoreCase("MuteChat")) {
			if (player.hasPermission("chatmanager.mutechat")) {
				if (args.length == 0) {
					if (Methods.isMuted()) {
						Methods.setMuted();
						Methods.broadcast(placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Enabled").replace("{player}", player.getName())));
					} else {
						Methods.setMuted();
						Methods.broadcast(placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Disabled").replace("{player}", player.getName())));
					}

					return true;
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
				return true;
			}

			if (args[0].equalsIgnoreCase("-s")) {
				if (player.hasPermission("chatmanager.mutechat.silent")) {
					if (args.length == 1) {
						if (Methods.isMuted()) {
							Methods.setMuted();
							for (Player staff : plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission("chatmanager.bypass.mutechat")) {
									Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Enabled").replace("{player}", player.getName())), true);
									return true;
								}
							}

						} else {
							Methods.setMuted();
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