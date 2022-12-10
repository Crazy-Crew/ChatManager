package me.h1dd3nxn1nja.chatmanager.commands;

import org.bukkit.Bukkit;
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
import me.h1dd3nxn1nja.chatmanager.utils.Version;

public class CommandStaffChat implements CommandExecutor{

	public CommandStaffChat(ChatManager plugin) {}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		FileConfiguration config = ChatManager.settings.getConfig();
		FileConfiguration messages = ChatManager.settings.getMessages();
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("StaffChat")) {
				if (player.hasPermission("chatmanager.staffchat")) {
					if (args.length == 0) {
						if (config.getBoolean("Staff_Chat.Enable")) {
							if (Methods.cm_staffChat.contains(player.getUniqueId())) {
								Methods.cm_staffChat.remove(player.getUniqueId());
								if ((config.getBoolean("Staff_Chat.Boss_Bar.Enable")) && (Version.getCurrentVersion().isNewer(Version.v1_8_R3))) {
									BossBarUtil bossBar = new BossBarUtil(Methods.color(config.getString("Staff_Chat.Boss_Bar.Title")));
									bossBar.removeStaffBossBar(player);	
								}
								player.sendMessage(Methods.color(player, messages.getString("Staff_Chat.Disabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
							} else {

								Methods.cm_staffChat.add(player.getUniqueId());
								if ((config.getBoolean("Staff_Chat.Boss_Bar.Enable")) && (Version.getCurrentVersion().isNewer(Version.v1_8_R3))) {
									BossBarUtil bossBar = new BossBarUtil(Methods.color(config.getString("Staff_Chat.Boss_Bar.Title")));
									bossBar.setStaffBossBar(player);
								}
								player.sendMessage(Methods.color(player, messages.getString("Staff_Chat.Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
							}
						return true;
						
						} else {
							player.sendMessage(Methods.color("&4Error: &cStaff chat is currently disabled and cannot be used at this time."));
							return true;
						}

					} else {
						if (config.getBoolean("Staff_Chat.Enable")) {
							StringBuilder message = new StringBuilder();
							for (int i = 0; i < args.length; i++) {
								message.append(args[i] + " ");
							}
							for (Player staff : Bukkit.getOnlinePlayers()) {
								if (staff.hasPermission("chatmanager.staffchat")) {
									staff.sendMessage(PlaceholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)));
								}
							}
							Methods.tellConsole(PlaceholderManager.setPlaceholders(player, config.getString("Staff_Chat.Format").replace("{message}", message)));
						} else {
							player.sendMessage(Methods.color("&4Error: &cStaff chat is currently disabled and cannot be used at this time."));
							return true;
						}
					}
				} else {
					player.sendMessage(Methods.noPermission());
				}
			}
		} else if (sender instanceof ConsoleCommandSender) {
			if (config.getBoolean("Staff_Chat.Enable")) {
				StringBuilder message = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					message.append(args[i] + " ");
				}
				for (Player staff : Bukkit.getOnlinePlayers()) {
					if (staff.hasPermission("chatmanager.staffchat")) {
						staff.sendMessage(Methods.color(config.getString("Staff_Chat.Format").replace("{player}", sender.getName()).replace("{message}", message)));
					}
				}
				sender.sendMessage(Methods.color(config.getString("Staff_Chat.Format").replace("{player}", sender.getName()).replace("{message}", message)));
			} else {
				sender.sendMessage(Methods.color("&4Error: &cStaff chat is currently disabled and cannot be used at this time."));
				return true;
			}
		}
		return true;
	}
}