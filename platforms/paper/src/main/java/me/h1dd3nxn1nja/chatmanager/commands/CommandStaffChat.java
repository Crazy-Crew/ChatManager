package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.utils.ServerProtocol;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;

public class CommandStaffChat implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("StaffChat")) {
				if (player.hasPermission("chatmanager.staffchat")) {
					if (args.length == 0) {
						if (config.getBoolean("Staff_Chat.Enable")) {
							if (Methods.cm_staffChat.contains(player.getUniqueId())) {
								Methods.cm_staffChat.remove(player.getUniqueId());
								if ((config.getBoolean("Staff_Chat.Boss_Bar.Enable")) && (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1))) {
									BossBarUtil bossBar = new BossBarUtil(Methods.color(config.getString("Staff_Chat.Boss_Bar.Title")));
									bossBar.removeStaffBossBar(player);
								}

								Methods.sendMessage(player, messages.getString("Staff_Chat.Disabled"), true);
							} else {
								Methods.cm_staffChat.add(player.getUniqueId());

								if ((config.getBoolean("Staff_Chat.Boss_Bar.Enable")) && (ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1))) {
									BossBarUtil bossBar = new BossBarUtil(Methods.color(config.getString("Staff_Chat.Boss_Bar.Title")));
									bossBar.setStaffBossBar(player);
								}

								Methods.sendMessage(player, messages.getString("Staff_Chat.Enabled"), true);
							}

							return true;

						} else {
							Methods.sendMessage(player, "&4Error: &cStaff Chat is currently disabled & cannot be used at this time.", true);
							return true;
						}
					} else {
						if (config.getBoolean("Staff_Chat.Enable")) {
							StringBuilder message = new StringBuilder();

							for (int i = 0; i < args.length; i++) {
								message.append(args[i] + " ");
							}

							for (Player staff : plugin.getServer().getOnlinePlayers()) {
								Methods.sendMessage(staff, placeholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)), true);
							}

							Methods.tellConsole(placeholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)), true);
						} else {
							Methods.sendMessage(player, "&4Error: &cStaff Chat is currently disabled & cannot be used at this time.", true);
							return true;
						}
					}
				} else {
					Methods.sendMessage(player, Methods.noPermission(), true);
				}
			}
		} else if (sender instanceof ConsoleCommandSender) {
			if (config.getBoolean("Staff_Chat.Enable")) {
				StringBuilder message = new StringBuilder();

				for (int i = 0; i < args.length; i++) {
					message.append(args[i] + " ");
				}

				for (Player staff : plugin.getServer().getOnlinePlayers()) {
					if (staff.hasPermission("chatmanager.staffchat")) Methods.sendMessage(staff, config.getString("Staff_Chat.Format").replace("{player}", sender.getName()).replace("{message}", message), true);
				}

				Methods.sendMessage(sender, config.getString("Staff_Chat.Format").replace("{player}", sender.getName()).replace("{message}", message), true);
			} else {
				Methods.sendMessage(sender, "&4Error: &cStaff Chat is currently disabled & cannot be used at this time.", true);
				return true;
			}
		}

		return true;
	}
}