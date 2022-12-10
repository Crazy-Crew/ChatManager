package me.h1dd3nxn1nja.chatmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.hooks.EssentialsHook;
import me.h1dd3nxn1nja.chatmanager.hooks.HookManager;
import me.h1dd3nxn1nja.chatmanager.hooks.SuperVanishHook;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.utils.Version;

public class CommandMessage implements CommandExecutor {

	public ChatManager plugin;

	public CommandMessage(ChatManager plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		FileConfiguration config = ChatManager.settings.getConfig();
		FileConfiguration messages = ChatManager.settings.getMessages();

		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("Message")) {
			if (player.hasPermission("chatmanager.message")) {
				StringBuilder message = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					message.append(args[i] + " ");
				}

				if (args.length < 1) {
					player.sendMessage(Methods.color("&cCommand Usage: &7/Message <player> <message>"));
					return true;
				}

				Player target = Bukkit.getPlayer(args[0]);

				if (target == null || !target.isOnline()) {
					player.sendMessage(Methods.color(messages.getString("Message.Player_Not_Found")
							.replace("{Prefix}", messages.getString("Message.Prefix")).replace("{target}", args[0])));
					return true;
				}

				if ((target == player) && (!player.hasPermission("chatmanager.message.self"))) {
					player.sendMessage(Methods.color(messages.getString("Private_Message.Self").replace("{Prefix}", messages.getString("Message.Prefix"))));
					return true;
				}

				if (Version.getCurrentVersion().isNewer(Version.v1_8_R2)) {
					if ((target.getGameMode().equals(GameMode.SPECTATOR) && (!player.hasPermission("chatmanager.bypass.spectator")))) {
						player.sendMessage(Methods.color(messages.getString("Message.Player_Not_Found")
								.replace("{Prefix}", messages.getString("Message.Prefix"))
								.replace("{target}", args[0])));
						return true;
					}
				}

				if ((Methods.cm_togglePM.contains(target.getUniqueId()) && !player.hasPermission("chatmanager.bypass.togglepm"))) {
					player.sendMessage(Methods.color(messages.getString("Private_Message.Toggled").replace("{Prefix}", messages.getString("Message.Prefix"))));
					return true;
				}

				if ((!player.canSee(target)) && (!player.hasPermission("chatmanager.bypass.vanish"))) {
					player.sendMessage(Methods.color(messages.getString("Message.Player_Not_Found")
							.replace("{Prefix}", messages.getString("Message.Prefix")).replace("{target}", args[0])));
					return true;
				}

				if (HookManager.isEssentialsLoaded()) {
					if (EssentialsHook.getUsers(target).isAfk() && (!player.hasPermission("chatmanager.bypass.afk"))) {
						player.sendMessage(Methods.color(messages.getString("Private_Message.AFK")
								.replace("{Prefix}", messages.getString("Message.Prefix"))
								.replace("{target}", target.getName())));
					}

					if ((EssentialsHook.isIgnored(target, player)) && (!player.hasPermission("chatmanager.bypass.ignored"))) {
						player.sendMessage(Methods.color(messages.getString("Private_Message.Ignored")
								.replace("{Prefix}", messages.getString("Message.Prefix"))
								.replace("{target}", target.getName())));
						return true;
					}

					if ((EssentialsHook.isHidden(target)) && (!player.hasPermission("chatmanager.bypass.vanish"))) {
						player.sendMessage(Methods.color(messages.getString("Message.Player_Not_Found")
								.replace("{Prefix}", messages.getString("Message.Prefix"))
								.replace("{target}", args[0])));
						return true;
					}

					if (EssentialsHook.isMuted(player)) {
						return true;
					}
				}

				if ((HookManager.isSuperVanishLoaded()) && (SuperVanishHook.isVanished(target)) && (!player.hasPermission("chatmanager.bypass.vanish"))) {
					player.sendMessage(Methods.color(messages.getString("Message.Player_Not_Found")
							.replace("{Prefix}", messages.getString("Message.Prefix")).replace("{target}", args[0])));
					return true;
				}

				player.sendMessage(PlaceholderManager.setPlaceholders(target,
						config.getString("Private_Messages.Sender.Format").replace("{receiver}", target.getName())
								.replace("{receiver_displayname}", target.getDisplayName()) + message));
				target.sendMessage(PlaceholderManager.setPlaceholders(player,
						config.getString("Private_Messages.Receiver.Format").replace("{receiver}", target.getName())
								.replace("{receiver_displayname}", target.getDisplayName()) + message));

				Methods.cm_replied.put(player, target);
				Methods.cm_replied.put(target, player);

				for (Player staff : Bukkit.getOnlinePlayers()) {
					if ((staff != player) && (staff != target)) {
						if ((!player.hasPermission("chatmanager.bypass.socialspy")) && (!target.hasPermission("chatmanager.bypass.socialspy"))) {
							if (Methods.cm_socialSpy.contains(staff.getUniqueId())) {
								staff.sendMessage(Methods.color(staff, messages.getString("Social_Spy.Format").replace("{player}", player.getName())
										.replace("{receiver}", target.getName()).replace("{message}", message)));
							}
						}
					}
				}
			} else {
				player.sendMessage(Methods.noPermission());
			}
		}
		if (cmd.getName().equalsIgnoreCase("Reply")) {
			if (player.hasPermission("chatmanager.reply")) {
				if (args.length > 0) {
					StringBuilder message = new StringBuilder();
					for (int i = 0; i < args.length; i++) {
						message.append(args[i] + " ");
					}
					Player target = Methods.cm_replied.get(player);
					if (target == null || !target.isOnline()) {
						player.sendMessage(Methods.color(messages.getString("Private_Message.Recipient_Not_Found")
								.replace("{Prefix}", messages.getString("Message.Prefix"))));
						return true;
					}

					if (Methods.cm_togglePM.contains(target.getUniqueId())) {
						player.sendMessage(Methods.color(messages.getString("Private_Message.Toggled")
								.replace("{Prefix}", messages.getString("Message.Prefix"))));
						return true;
					}

					if (!player.canSee(target)) {
						player.sendMessage(Methods.color(messages.getString("Message.Player_Not_Found")
								.replace("{Prefix}", messages.getString("Message.Prefix"))
								.replace("{target}", args[0])));
						return true;
					}

					if (HookManager.isEssentialsLoaded()) {
						if (EssentialsHook.getUsers(target).isAfk()) {
							player.sendMessage(Methods.color(messages.getString("Private_Message.AFK")
									.replace("{Prefix}", messages.getString("Message.Prefix"))
									.replace("{target}", target.getName())));
						}

						if ((EssentialsHook.isIgnored(target, player)) && (!player.hasPermission("chatmanager.bypass.ignored"))) {
							player.sendMessage(Methods.color(messages.getString("Private_Message.Ignored")
									.replace("{Prefix}", messages.getString("Message.Prefix"))
									.replace("{target}", target.getName())));
							return true;
						}

						if ((EssentialsHook.isHidden(target)) && (!player.hasPermission("chatmanager.bypass.vanish"))) {
							player.sendMessage(Methods.color(messages.getString("Message.Player_Not_Found")
									.replace("{Prefix}", messages.getString("Message.Prefix"))
									.replace("{target}", args[0])));
							return true;
						}

						if (EssentialsHook.isMuted(player)) {
							return true;
						}
					}

					if ((HookManager.isSuperVanishLoaded()) && (SuperVanishHook.isVanished(target)) && (!player.hasPermission("chatmanager.bypass.vanish"))) {
						player.sendMessage(Methods.color(messages.getString("Message.Player_Not_Found")
								.replace("{Prefix}", messages.getString("Message.Prefix"))
								.replace("{target}", args[0])));
						return true;
					}

					player.sendMessage(PlaceholderManager.setPlaceholders(target,
							config.getString("Private_Messages.Sender.Format").replace("{receiver}", target.getName())
									.replace("{receiver_displayname}", target.getDisplayName()) + message));
					target.sendMessage(PlaceholderManager.setPlaceholders(player,
							config.getString("Private_Messages.Receiver.Format").replace("{receiver}", target.getName())
									.replace("{receiver_displayname}", target.getDisplayName()) + message));

					Methods.cm_replied.put(target, player);
					Methods.cm_replied.put(player, target);

					for (Player staff : Bukkit.getOnlinePlayers()) {
						if ((staff != player) && (staff != target)) {
							if ((!player.hasPermission("chatmanager.bypass.socialspy")) && (!target.hasPermission("chatmanager.bypass.socialspy"))) {
								if (Methods.cm_socialSpy.contains(staff.getUniqueId())) {
									staff.sendMessage(Methods.color(staff, messages.getString("Social_Spy.Format").replace("{player}", player.getName())
											.replace("{receiver}", target.getName()).replace("{message}", message)));
								}
							}
						}
					}
				} else {
					player.sendMessage(Methods.color("&cCommand Usage: &7/Reply <message>"));
				}
			} else {
				player.sendMessage(Methods.noPermission());
			}
		}
		if (cmd.getName().equalsIgnoreCase("TogglePM")) {
			if (player.hasPermission("chatmanager.toggle.pm")) {
				if (args.length == 0) {
					if (Methods.cm_togglePM.contains(player.getUniqueId())) {
						Methods.cm_togglePM.remove(player.getUniqueId());
						player.sendMessage(Methods.color(messages.getString("TogglePM.Disabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
					} else {
						Methods.cm_togglePM.add(player.getUniqueId());
						player.sendMessage(Methods.color(messages.getString("TogglePM.Enabled").replace("{Prefix}", messages.getString("Message.Prefix"))));
					}
					return true;
				} else {
					player.sendMessage(Methods.color("&cCommand Usage: &7/Togglepm"));
				}
			} else {
				player.sendMessage(Methods.noPermission());
			}
		}
		return true;
	}
}