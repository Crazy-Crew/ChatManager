package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandStaffChat implements CommandExecutor, TabCompleter {

	@NotNull
	private final ChatManager plugin = ChatManager.get();

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
		FileConfiguration config = Files.CONFIG.getConfiguration();
		FileConfiguration messages = Files.MESSAGES.getConfiguration();

		if (sender instanceof Player player) {
			if (cmd.getName().equalsIgnoreCase("staffchat")) {
				if (player.hasPermission(Permissions.TOGGLE_STAFF_CHAT.getNode())) {
					if (args.length == 0) {
						if (config.getBoolean("Staff_Chat.Enable")) {
							boolean isValid = this.plugin.api().getStaffChatData().containsUser(player.getUniqueId());

							if (isValid) {
								this.plugin.api().getStaffChatData().removeUser(player.getUniqueId());

								// We want to remove anyway just in case they turned it off.
								BossBarUtil bossBar = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title"))));
								bossBar.removeStaffBossBar(player);

								Methods.sendMessage(player, messages.getString("Staff_Chat.Disabled"), true);

								return true;
							}

							this.plugin.api().getStaffChatData().addUser(player.getUniqueId());

							boolean isBossBarEnabled = config.contains("Staff_Chat.Boss_Bar.Enable") && config.getBoolean("Staff_Chat.Boss_Bar.Enable");

							if (isBossBarEnabled) {
								BossBarUtil bossBar = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title"))));
								bossBar.setStaffBossBar(player);
							}

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

							for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
								Methods.sendMessage(staff, config.getString("Staff_Chat.Format").replace("{message}", message), true);
							}

							Methods.tellConsole(config.getString("Staff_Chat.Format").replace("{message}", message), true);
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

				for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
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

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return new ArrayList<>();
	}
}