package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandPerWorldChat implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getFile();
		FileConfiguration messages = Files.MESSAGES.getFile();

		if (!(sender instanceof Player player)) {
			this.plugin.getMethods().sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("perworldchat")) {
			if (player.hasPermission("chatmanager.perworldchat")) {
				if (args.length == 0) {
					this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/Perworldchat bypass", true);
					return true;
				}
			} else {
				this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				return true;
			}

			if (args[0].equalsIgnoreCase("bypass")) {
				if (player.hasPermission("chatmanager.perworldchat")) {
					if (args.length == 1) {
						if (config.getBoolean("Per_World_Chat.Enable")) {
							if (!plugin.api().getPerWorldChatData().containsUser(player.getUniqueId())) {
								plugin.api().getPerWorldChatData().addUser(player.getUniqueId());
								this.plugin.getMethods().sendMessage(player, messages.getString("Per_World_Chat.Bypass_Enabled"), true);
							} else {
								plugin.api().getPerWorldChatData().removeUser(player.getUniqueId());
								this.plugin.getMethods().sendMessage(player, messages.getString("Per_World_Chat.Bypass_Disabled"), true);
							}
						} else {
							this.plugin.getMethods().sendMessage(player, "&4Error: &cPer-world chat is currently disabled and you cannot execute that command at this time.", true);
						}
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/Perworldchat bypass", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}
		}

		return true;
	}
}