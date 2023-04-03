package me.h1dd3nxn1nja.chatmanager.commands;

import java.text.DecimalFormat;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import org.jetbrains.annotations.NotNull;

public class CommandLists implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();
	
	DecimalFormat df = new DecimalFormat("#,###");

	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = settingsManager.getConfig();
		
		if (cmd.getName().equalsIgnoreCase("List")) {
			if (sender.hasPermission("chatmanager.lists.players")) {
				if (args.length == 0) {
					StringBuilder str = new StringBuilder();

					for (Player players : plugin.getServer().getOnlinePlayers()) {
						if (str.length() > 0) str.append(", ");

						str.append("&a").append(players.getName()).append("&8");
					}

					String online = df.format(plugin.getServer().getOnlinePlayers().size());
					String max = df.format(plugin.getServer().getMaxPlayers());

					if (sender instanceof Player) {
						for (String list : config.getStringList("Lists.Player_List")) {
							Player player = (Player) sender;

							Methods.sendMessage(player, placeholderManager.setPlaceholders(player, list.replace("{players}", str.toString()).replace("{server_online}", online).replace("{server_max_players}", max)), true);
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

		if (cmd.getName().equalsIgnoreCase("Staff")) {
			if (sender.hasPermission("chatmanager.lists.staff")) {
				if (args.length == 0) {
					StringBuilder str = new StringBuilder();

					for (Player staff : plugin.getServer().getOnlinePlayers()) {
						if ((staff.hasPermission("chatmanager.staff")) || (staff.isOp())) {
							if (str.length() > 0) str.append(", ");

							str.append("&a").append(staff.getName()).append("&8");
						}
					}

					String online = df.format(plugin.getServer().getOnlinePlayers().size());
					String max = df.format(plugin.getServer().getMaxPlayers());

					if (sender instanceof Player) {
						for (String list : config.getStringList("Lists.Staff_List")) {
							Player player = (Player) sender;
							Methods.sendMessage(player, placeholderManager.setPlaceholders(player, list.replace("{players}", str.toString()).replace("{server_online}", online).replace("{server_max_players}", max)), true);
						}
					} else {
						for (String list : config.getStringList("Lists.Staff_List")) {
							Methods.sendMessage(sender, list.replace("{players}", str.toString()).replace("{server_online}", online).replace("{server_max_players}", max), true);
						}
					}
				} else {
					Methods.sendMessage(sender, "&cCommand Usage: &7/Staff", true);
				}
			} else {
				Methods.sendMessage(sender, Methods.noPermission(), true);
			}
		}

		return true;
	}
}