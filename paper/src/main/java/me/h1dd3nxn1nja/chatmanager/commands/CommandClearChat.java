package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandClearChat extends Global implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("clearchat")) {
			if (sender.hasPermission(Permissions.COMMAND_CLEARCHAT.getNode())) {
				if (args.length == 0) {
					for (Player members : this.plugin.getServer().getOnlinePlayers()) {
						if (!members.hasPermission(Permissions.BYPASS_CLEAR_CHAT.getNode())) {
							sendClearMessage(members);

							if (sender instanceof Player player) {
								Messages.CLEAR_CHAT_BROADCAST_MESSAGE.sendMessage(members, "{player}", player.getName());
							} else if (sender instanceof ConsoleCommandSender) {
								Messages.CLEAR_CHAT_BROADCAST_MESSAGE.sendMessage(members, "{player}", sender.getName());

								Messages.CLEAR_CHAT_STAFF_MESSAGE.sendMessage(sender, "{player}", sender.getName());
							}
						} else {
							Messages.CLEAR_CHAT_STAFF_MESSAGE.sendMessage(sender, "{player}", sender.getName());
						}
					}
				} else {
					Messages.INVALID_USAGE.sendMessage(sender, "{usage}", "/clearchat");
				}
			} else {
				Messages.NO_PERMISSION.sendMessage(sender);
			}
		}

		return true;
	}

	public void sendClearMessage(CommandSender sender) {
		FileConfiguration config = Files.CONFIG.getConfiguration();
		int lines = config.getInt("Clear_Chat.Broadcasted_Lines", 300);
		
		for (int i = 0; i < lines; i++) {
			sender.sendMessage("");
		}
	}
}