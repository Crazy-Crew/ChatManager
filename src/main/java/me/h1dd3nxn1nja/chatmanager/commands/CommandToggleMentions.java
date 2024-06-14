package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.paper.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.paper.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandToggleMentions implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@NotNull
	private final PlaceholderManager placeholderManager = this.plugin.getCrazyManager().getPlaceholderManager();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration messages = Files.MESSAGES.getFile();
		
		if (!(sender instanceof Player player)) {
			this.plugin.getMethods().sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		if (!cmd.getName().equalsIgnoreCase("togglementions")) return true;

		if (!player.hasPermission(Permissions.TOGGLE_MENTIONS.getNode())) {
			this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
			return true;
		}

		if (args.length == 0) {
			if (this.plugin.api().getToggleMentionsData().containsUser(player.getUniqueId())) {
				this.plugin.api().getToggleMentionsData().removeUser(player.getUniqueId());
				this.plugin.getMethods().sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Mentions.Disabled")), true);
				return true;
			}

			this.plugin.api().getToggleMentionsData().addUser(player.getUniqueId());
			this.plugin.getMethods().sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Mentions.Enabled")), true);

			return true;
		}

		this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/toggleMentions", true);

		return true;
	}
}