package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.FileManager;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import me.h1dd3nxn1nja.chatmanager.paper.utils.BossBarUtil;
import org.jetbrains.annotations.NotNull;

public class CommandStaffChat implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();
	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = FileManager.Files.CONFIG.getFile();
		FileConfiguration messages = FileManager.Files.MESSAGES.getFile();
		if (sender instanceof Player player) {
			if (cmd.getName().equalsIgnoreCase("StaffChat")) {
				if (player.hasPermission("chatmanager.staffchat")) {
					if (args.length == 0) {
						if (config.getBoolean("Staff_Chat.Enable")) {
							boolean isValid = plugin.api().getStaffChatData().containsUser(player.getUniqueId());

							if (isValid) {
								plugin.api().getStaffChatData().removeUser(player.getUniqueId());

								BossBarUtil bossBar = new BossBarUtil(Methods.color(config.getString("Staff_Chat.Boss_Bar.Title")));
								bossBar.removeStaffBossBar(player);

								Methods.sendMessage(player, messages.getString("Staff_Chat.Disabled"), true);

								return true;
							}

							plugin.api().getStaffChatData().addUser(player.getUniqueId());

							BossBarUtil bossBar = new BossBarUtil(Methods.color(config.getString("Staff_Chat.Boss_Bar.Title")));
							bossBar.setStaffBossBar(player);

							Methods.sendMessage(player, messages.getString("Staff_Chat.Enabled"), true);

							return true;

						} else {
							Methods.sendMessage(player, "&4Error: &cStaff Chat is currently disabled & cannot be used at this time.", true);
							return true;
						}
					} else {
						if (config.getBoolean("Staff_Chat.Enable")) {
							StringBuilder message = new StringBuilder();

							for (String arg : args) {
								message.append(arg).append(" ");
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

				for (String arg : args) {
					message.append(arg).append(" ");
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