package me.h1dd3nxn1nja.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.paper.utils.BossBarUtil;
import org.jetbrains.annotations.NotNull;

public class CommandStaffChat implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();
	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getFile();
		FileConfiguration messages = Files.MESSAGES.getFile();

		if (sender instanceof Player player) {
			if (cmd.getName().equalsIgnoreCase("StaffChat")) {
				if (player.hasPermission("chatmanager.staffchat")) {
					if (args.length == 0) {
						if (config.getBoolean("Staff_Chat.Enable")) {
							boolean isValid = plugin.api().getStaffChatData().containsUser(player.getUniqueId());

							if (isValid) {
								plugin.api().getStaffChatData().removeUser(player.getUniqueId());

								// We want to remove anyway just in case they turned it off.
								BossBarUtil bossBar = new BossBarUtil(this.plugin.getMethods().color(config.getString("Staff_Chat.Boss_Bar.Title")));
								bossBar.removeStaffBossBar(player);

								this.plugin.getMethods().sendMessage(player, messages.getString("Staff_Chat.Disabled"), true);

								return true;
							}

							plugin.api().getStaffChatData().addUser(player.getUniqueId());

							boolean isBossBarEnabled = config.contains("Staff_Chat.Boss_Bar.Enable") && config.getBoolean("Staff_Chat.Boss_Bar.Enable");

							if (isBossBarEnabled) {
								BossBarUtil bossBar = new BossBarUtil(this.plugin.getMethods().color(config.getString("Staff_Chat.Boss_Bar.Title")));
								bossBar.setStaffBossBar(player);
							}

							this.plugin.getMethods().sendMessage(player, messages.getString("Staff_Chat.Enabled"), true);

							return true;

						} else {
							this.plugin.getMethods().sendMessage(player, "&4Error: &cStaff Chat is currently disabled & cannot be used at this time.", true);
							return true;
						}
					} else {
						if (config.getBoolean("Staff_Chat.Enable")) {
							StringBuilder message = new StringBuilder();

							for (String arg : args) {
								message.append(arg).append(" ");
							}

							for (Player staff : plugin.getServer().getOnlinePlayers()) {
								this.plugin.getMethods().sendMessage(staff, placeholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)), true);
							}

							this.plugin.getMethods().tellConsole(placeholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)), true);
						} else {
							this.plugin.getMethods().sendMessage(player, "&4Error: &cStaff Chat is currently disabled & cannot be used at this time.", true);
							return true;
						}
					}
				} else {
					this.plugin.getMethods().sendMessage(player, this.plugin.getMethods().noPermission(), true);
				}
			}
		} else if (sender instanceof ConsoleCommandSender) {
			if (config.getBoolean("Staff_Chat.Enable")) {
				StringBuilder message = new StringBuilder();

				for (String arg : args) {
					message.append(arg).append(" ");
				}

				for (Player staff : plugin.getServer().getOnlinePlayers()) {
					if (staff.hasPermission("chatmanager.staffchat")) this.plugin.getMethods().sendMessage(staff, config.getString("Staff_Chat.Format").replace("{player}", sender.getName()).replace("{message}", message), true);
				}

				this.plugin.getMethods().sendMessage(sender, config.getString("Staff_Chat.Format").replace("{player}", sender.getName()).replace("{message}", message), true);
			} else {
				this.plugin.getMethods().sendMessage(sender, "&4Error: &cStaff Chat is currently disabled & cannot be used at this time.", true);
				return true;
			}
		}

		return true;
	}
}