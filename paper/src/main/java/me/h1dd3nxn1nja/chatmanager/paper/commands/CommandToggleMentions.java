package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CommandToggleMentions implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

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

		if (!player.hasPermission("chatmanager.toggle.mentions")) {
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

		this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/ToggleMentions", true);

		return true;
	}
}