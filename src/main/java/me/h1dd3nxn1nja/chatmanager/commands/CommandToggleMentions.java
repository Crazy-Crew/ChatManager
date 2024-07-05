package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandToggleMentions implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = Files.MESSAGES.getConfiguration();
		
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (!cmd.getName().equalsIgnoreCase("togglementions")) return true;

		if (!player.hasPermission(Permissions.TOGGLE_MENTIONS.getNode())) {
			Methods.sendMessage(player, Methods.noPermission(), true);
			return true;
		}

		if (args.length == 0) {
			if (this.plugin.api().getToggleMentionsData().containsUser(player.getUniqueId())) {
				this.plugin.api().getToggleMentionsData().removeUser(player.getUniqueId());
				Methods.sendMessage(player, messages.getString("Toggle_Mentions.Disabled"), true);
				return true;
			}

			this.plugin.api().getToggleMentionsData().addUser(player.getUniqueId());
			Methods.sendMessage(player, messages.getString("Toggle_Mentions.Enabled"), true);

			return true;
		}

		Methods.sendMessage(player, "&cCommand Usage: &7/toggleMentions", true);

		return true;
	}
}