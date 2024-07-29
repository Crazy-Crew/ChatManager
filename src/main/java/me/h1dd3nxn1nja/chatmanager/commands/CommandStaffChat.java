package me.h1dd3nxn1nja.chatmanager.commands;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
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

		if (sender instanceof Player player) {
			if (cmd.getName().equalsIgnoreCase("staffchat")) {
				if (player.hasPermission(Permissions.TOGGLE_STAFF_CHAT.getNode())) {
					if (args.length == 0) {
						if (config.getBoolean("Staff_Chat.Enable", false)) {
							boolean isValid = this.plugin.api().getStaffChatData().containsUser(player.getUniqueId());

							if (isValid) {
								this.plugin.api().getStaffChatData().removeUser(player.getUniqueId());

								// We want to remove anyway just in case they turned it off.
								BossBarUtil bossBar = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title", "&eStaff Chat"))));
								bossBar.removeStaffBossBar(player);

								Messages.STAFF_CHAT_DISABLED.sendMessage(player);

								return true;
							}

							this.plugin.api().getStaffChatData().addUser(player.getUniqueId());

							boolean isBossBarEnabled = config.getBoolean("Staff_Chat.Boss_Bar.Enable", false);

							if (isBossBarEnabled) {
								BossBarUtil bossBar = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title", "&eStaff Chat"))));

								bossBar.setStaffBossBar(player);
							}

							Messages.STAFF_CHAT_ENABLED.sendMessage(player);

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
								if (Permissions.TOGGLE_STAFF_CHAT.hasPermission(staff)) {
									Methods.sendMessage(staff, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", message.toString()));
								}
							}

							Methods.tellConsole(config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", message.toString()), false);
						} else {
							Methods.sendMessage(player, "&4Error: &cStaff Chat is currently disabled & cannot be used at this time.", true);

							return true;
						}
					}
				} else {
					Messages.NO_PERMISSION.sendMessage(player);
				}
			}
		} else if (sender instanceof ConsoleCommandSender) {
			if (config.getBoolean("Staff_Chat.Enable", false)) {
				StringBuilder message = new StringBuilder();

				for (String arg : args) {
					message.append(arg).append(" ");
				}

				for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
					if (staff.hasPermission(Permissions.TOGGLE_STAFF_CHAT.getNode())) Methods.sendMessage(staff, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}")
							.replace("{player}", sender.getName())
							.replace("{message}", message), true);
				}

				Methods.sendMessage(sender, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}")
						.replace("{player}", sender.getName())
						.replace("{message}", message), true);
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