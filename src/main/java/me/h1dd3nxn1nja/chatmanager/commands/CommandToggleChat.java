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

public class CommandToggleChat implements CommandExecutor {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		if (!cmd.getName().equalsIgnoreCase("togglechat")) return true;

		if (!player.hasPermission(Permissions.TOGGLE_CHAT.getNode())) {
			Methods.sendMessage(player, Methods.noPermission(), true);
			return true;
		}

		if (args.length == 0) {
			if (this.plugin.api().getToggleChatData().containsUser(player.getUniqueId())) {
				this.plugin.api().getToggleChatData().removeUser(player.getUniqueId());
				Methods.sendMessage(player, messages.getString("Toggle_Chat.Disabled"), true);
				return true;
			}

			this.plugin.api().getToggleChatData().addUser(player.getUniqueId());
			Methods.sendMessage(player, messages.getString("Toggle_Chat.Enabled"), true);

			return true;
		}

		Methods.sendMessage(player, "&cCommand Usage: &7/togglechat", true);

		return true;
	}
}