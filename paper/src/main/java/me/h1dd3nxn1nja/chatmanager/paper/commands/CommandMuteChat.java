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
import com.ryderbelserion.chatmanager.paper.files.Files;

public class CommandMuteChat implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();
	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		if (!(sender instanceof Player player)) {
			this.plugin.getMethods().sendMessage(sender, "&cError: You can only use that command in-game", true);
			return true;
		}

		FileConfiguration messages = Files.MESSAGES.getFile();

		if (cmd.getName().equalsIgnoreCase("MuteChat")) {
			if (player.hasPermission("chatmanager.mutechat")) {
				if (args.length == 0) {
					if (this.plugin.getMethods().isMuted()) {
						this.plugin.getMethods().setMuted();
						this.plugin.getMethods().broadcast(placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Enabled").replace("{player}", player.getName())));
					} else {
						this.plugin.getMethods().setMuted();
						this.plugin.getMethods().broadcast(placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Disabled").replace("{player}", player.getName())));
					}

					return true;
				}
			} else {
				this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				return true;
			}

			if (args[0].equalsIgnoreCase("-s")) {
				if (player.hasPermission("chatmanager.mutechat.silent")) {
					if (args.length == 1) {
						if (this.plugin.getMethods().isMuted()) {
							this.plugin.getMethods().setMuted();
							for (Player staff : plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission("chatmanager.bypass.mutechat")) {
									this.plugin.getMethods().sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Enabled").replace("{player}", player.getName())), true);
									return true;
								}
							}

						} else {
							this.plugin.getMethods().setMuted();
							for (Player staff : plugin.getServer().getOnlinePlayers()) {
								if (staff.hasPermission("chatmanager.bypass.mutechat")) {
									this.plugin.getMethods().sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Mute_Chat.Broadcast_Messages.Disabled").replace("{player}", player.getName())), true);
									return true;
								}
							}
						}
					} else {
						this.plugin.getMethods().sendMessage(player, "&cCommand Usage: &7/Mutechat [-s]", true);
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
					return true;
				}
			}
		}

		return true;
	}
}