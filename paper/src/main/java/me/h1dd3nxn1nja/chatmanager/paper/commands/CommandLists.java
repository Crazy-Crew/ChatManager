package me.h1dd3nxn1nja.chatmanager.paper.commands;

import java.text.DecimalFormat;
import com.ryderbelserion.chatmanager.paper.enums.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import com.ryderbelserion.chatmanager.paper.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandLists implements CommandExecutor {
	
	private final DecimalFormat df = new DecimalFormat("#,###");

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@NotNull
	private final PlaceholderManager placeholderManager = this.plugin.getCrazyManager().getPlaceholderManager();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getFile();

		if (cmd.getName().equalsIgnoreCase("list")) {
			if (sender.hasPermission(Permissions.COMMAND_LISTS_PLAYERS.getNode())) {
				if (args.length == 0) {
					StringBuilder str = new StringBuilder();

					for (Player players : this.plugin.getServer().getOnlinePlayers()) {
						if (!str.isEmpty()) str.append(", ");

						str.append("&a").append(players.getName()).append("&8");
					}

					String online = this.df.format(this.plugin.getServer().getOnlinePlayers().size());
					String max = this.df.format(this.plugin.getServer().getMaxPlayers());

					if (sender instanceof Player) {
						for (String list : config.getStringList("Lists.Player_List")) {
							Player player = (Player) sender;

							this.plugin.getMethods().sendMessage(player, this.placeholderManager.setPlaceholders(player, list.replace("{players}", str.toString()).replace("{server_online}", online).replace("{server_max_players}", max)), true);
						}
					} else {
						for (String list : config.getStringList("Lists.Player_List")) {
							this.plugin.getMethods().sendMessage(sender, list.replace("{players}", str.toString()).replace("{server_online}", online).replace("{server_max_players}", max), true);
						}
					}
				} else {
					this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/list", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
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

					String online = this.df.format(this.plugin.getServer().getOnlinePlayers().size());
					String max = this.df.format(this.plugin.getServer().getMaxPlayers());

					if (sender instanceof Player player) {
						for (String list : config.getStringList("Lists.Staff_List")) {
							this.plugin.getMethods().sendMessage(player, this.placeholderManager.setPlaceholders(player, list.replace("{players}", str.toString()).replace("{server_online}", online).replace("{server_max_players}", max)), true);
						}
					} else {
						for (String list : config.getStringList("Lists.Staff_List")) {
							this.plugin.getMethods().sendMessage(sender, list.replace("{players}", str.toString()).replace("{server_online}", online).replace("{server_max_players}", max), true);
						}
					}
				} else {
					this.plugin.getMethods().sendMessage(sender, "&cCommand Usage: &7/staff", true);
				}
			} else {
				this.plugin.getMethods().sendMessage(sender, this.plugin.getMethods().noPermission(), true);
			}
		}

		return true;
	}
}