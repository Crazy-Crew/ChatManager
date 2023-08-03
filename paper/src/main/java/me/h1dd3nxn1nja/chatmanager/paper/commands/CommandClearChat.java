package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import org.jetbrains.annotations.NotNull;

public class CommandClearChat implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = Files.MESSAGES.getFile();

		if (cmd.getName().equalsIgnoreCase("ClearChat")) {
			if (sender.hasPermission("chatmanager.clearchat")) {
				if (args.length == 0) {
					for (Player members : plugin.getServer().getOnlinePlayers()) {
						if (!members.hasPermission("chatmanager.bypass.clearchat")) {
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
					this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/Clearchat", true);
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