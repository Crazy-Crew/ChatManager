package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSpy implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			this.plugin.getMethods().sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		FileConfiguration messages = Files.MESSAGES.getFile();

		if (cmd.getName().equalsIgnoreCase("CommandSpy")) {
			if (player.hasPermission("chatmanager.commandspy")) {
				if (args.length == 0) {

					boolean isValid = plugin.api().getCommandSpyData().containsUser(player.getUniqueId());

					if (isValid) {
						plugin.api().getCommandSpyData().removeUser(player.getUniqueId());
						this.plugin.getMethods().sendMessage(player, messages.getString("Command_Spy.Disabled"), true);
						return true;
					}

					plugin.api().getCommandSpyData().addUser(player.getUniqueId());
					this.plugin.getMethods().sendMessage(player, messages.getString("Command_Spy.Enabled"), true);

					return true;
				} else {
					this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/Commandspy", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
			}
		}

		if (cmd.getName().equalsIgnoreCase("SocialSpy")) {
			if (player.hasPermission("chatmanager.socialspy")) {
				if (args.length == 0) {
					boolean isValid = plugin.api().getSocialSpyData().containsUser(player.getUniqueId());

					if (isValid) {
						plugin.api().getSocialSpyData().removeUser(player.getUniqueId());
						this.plugin.getMethods().sendMessage(player, messages.getString("Social_Spy.Disabled"), true);
						return true;
					}

					plugin.api().getSocialSpyData().addUser(player.getUniqueId());
					this.plugin.getMethods().sendMessage(player, messages.getString("Social_Spy.Enabled"), true);

					return true;
				} else {
					this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/Socialspy", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
			}
		}

		return true;
	}
}