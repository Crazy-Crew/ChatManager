package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.paper.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.paper.enums.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandClearChat implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = Files.MESSAGES.getFile();

		if (cmd.getName().equalsIgnoreCase("clearchat")) {
			if (sender.hasPermission(Permissions.COMMAND_CLEARCHAT.getNode())) {
				if (args.length == 0) {
					for (Player members : this.plugin.getServer().getOnlinePlayers()) {
						if (!members.hasPermission(Permissions.BYPASS_CLEAR_CHAT.getNode())) {
							sendClearMessage(members);

							if (sender instanceof Player player) {
								for (String broadcastMessage : messages.getStringList("Clear_Chat.Broadcast_Message")) {
									this.plugin.getMethods().sendMessage(members, broadcastMessage.replace("{player}", player.getName()), true);
								}
							} else if (sender instanceof ConsoleCommandSender) {
								for (String broadcastMessage : messages.getStringList("Clear_Chat.Broadcast_Message")) {
									this.plugin.getMethods().sendMessage(members, broadcastMessage.replace("{player}", sender.getName()), true);
								}

								this.plugin.getMethods().sendMessage(sender, messages.getString("Clear_Chat.Staff_Message").replace("{player}", sender.getName()), true);
							}
						} else {
							this.plugin.getMethods().sendMessage(sender, messages.getString("Clear_Chat.Staff_Message").replace("{player}", sender.getName()), true);
						}
					}
				} else {
					this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/clearchat", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
			}
		}

		return true;
	}

	public void sendClearMessage(CommandSender sender) {
		FileConfiguration config = Files.CONFIG.getFile();
		int lines = config.getInt("Clear_Chat.Broadcasted_Lines");
		
		for (int i = 0; i < lines; i++) {
			sender.sendMessage("");
		}
	}
}