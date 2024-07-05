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
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CommandLists implements CommandExecutor {

	private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (cmd.getName().equalsIgnoreCase("list")) {
			if (sender.hasPermission(Permissions.COMMAND_LISTS_PLAYERS.getNode())) {
				if (args.length == 0) {
					StringBuilder str = new StringBuilder();

					for (Player players : this.plugin.getServer().getOnlinePlayers()) {
						if (!str.isEmpty()) str.append(", ");

						str.append("&a").append(players.getName()).append("&8");
					}

					final String online = String.valueOf(this.plugin.getServer().getOnlinePlayers().size());
					final String max = String.valueOf(this.plugin.getServer().getMaxPlayers());

					if (sender instanceof Player player) {
						for (String list : config.getStringList("Lists.Player_List")) {
							Methods.sendMessage(player, Methods.placeholders(false, player, list.replace("{players}", str.toString())).replace("{server_online}", online).replace("{server_max_players}", max), true);
						}
					} else {
						for (String list : config.getStringList("Lists.Player_List")) {
							Methods.sendMessage(sender, list.replace("{players}", str.toString()).replace("{server_online}", online).replace("{server_max_players}", max), true);
						}
					}
				} else {
					Methods.sendMessage(sender, "&cCommand Usage: &7/list", true);
				}
			} else {
				Methods.sendMessage(sender, Methods.noPermission(), true);
			}
		}

		if (cmd.getName().equalsIgnoreCase("staff")) {
			if (sender.hasPermission(Permissions.COMMAND_LISTS_STAFF.getNode())) {
				if (args.length == 0) {
					StringBuilder str = new StringBuilder();

					for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
						if ((staff.hasPermission(Permissions.COMMAND_STAFF.getNode())) || (staff.isOp())) {
							if (!str.isEmpty()) str.append(", ");

							str.append("&a").append(staff.getName()).append("&8");
						}
					}

					final String online = String.valueOf(this.plugin.getServer().getOnlinePlayers().size());
					final String max = String.valueOf(this.plugin.getServer().getMaxPlayers());

					if (sender instanceof Player player) {
						for (String list : config.getStringList("Lists.Staff_List")) {
							Methods.sendMessage(player, Methods.placeholders(false, player, list.replace("{players}", str.toString())).replace("{server_online}", online).replace("{server_max_players}", max), true);
						}
					} else {
						for (String list : config.getStringList("Lists.Staff_List")) {
							Methods.sendMessage(sender, list.replace("{players}", str.toString()).replace("{server_online}", online).replace("{server_max_players}", max), true);
						}
					}
				} else {
					Methods.sendMessage(sender, "&cCommand Usage: &7/staff", true);
				}
			} else {
				Methods.sendMessage(sender, Methods.noPermission(), true);
			}
		}

		return true;
	}
}