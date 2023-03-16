package me.h1dd3nxn1nja.chatmanager.commands;

import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import me.h1dd3nxn1nja.chatmanager.support.vanish.EssentialsVanishSupport;
import me.h1dd3nxn1nja.chatmanager.support.vanish.GenericVanishSupport;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;

public class CommandMessage implements CommandExecutor {

	private final ChatManager plugin = ChatManager.getPlugin();

	private final SettingsManager settingsManager = plugin.getSettingsManager();

	private final PluginManager pluginManager = plugin.getPluginManager();

	private final EssentialsVanishSupport essentialsVanishSupport = plugin.getPluginManager().getEssentialsVanishSupport();

	private final GenericVanishSupport genericVanishSupport = plugin.getPluginManager().getGenericVanishSupport();

	private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = settingsManager.getConfig();
		FileConfiguration messages = settingsManager.getMessages();

		EssentialsSupport essentialsSupport = pluginManager.getEssentialsSupport();

		String playerNotFound = messages.getString("Message.Player_Not_Found");

		if (sender instanceof ConsoleCommandSender) {
			plugin.getLogger().warning("This command can only be used by a player.");
			return true;
		}

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("Message")) {
			if (player.hasPermission("chatmanager.message")) {
				StringBuilder message = new StringBuilder();

				for (int i = 1; i < args.length; i++) {
					message.append(args[i] + " ");
				}

				if (args.length < 1) {
					Methods.sendMessage(player, "&cCommand Usage: &7/Message <player> <message>", true);
					return true;
				}

				Player target = plugin.getServer().getPlayer(args[0]);

				if (target == null || !target.isOnline()) {
					if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
					return true;
				}

				if ((target == player) && (!player.hasPermission("chatmanager.message.self"))) {
					Methods.sendMessage(player, messages.getString("Private_Message.Self"), true);
					return true;
				}

				if ((target.getGameMode().equals(GameMode.SPECTATOR) && (!player.hasPermission("chatmanager.bypass.spectator")))) {
					if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
					return true;
				}

				if ((Methods.cm_togglePM.contains(target.getUniqueId()) && !player.hasPermission("chatmanager.bypass.togglepm"))) {
					Methods.sendMessage(player, messages.getString("Private_Message.Toggled"), true);
					return true;
				}

				if ((!player.canSee(target)) && (!player.hasPermission("chatmanager.bypass.vanish"))) {
					if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
					return true;
				}

				if (essentialsCheck(args, messages, essentialsSupport, playerNotFound, player, target)) return true;

				if (PluginSupport.PREMIUM_VANISH.isPluginEnabled() || PluginSupport.SUPER_VANISH.isPluginEnabled() && genericVanishSupport.isVanished(target) && !player.hasPermission("chatmanager.bypass.vanish")) {
					if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
					return true;
				}

				Methods.sendMessage(player, placeholderManager.setPlaceholders(target, config.getString("Private_Messages.Sender.Format")
						.replace("{receiver}", target.getName())
						.replace("{receiver_displayname}", target.getDisplayName()) + message), true);

				Methods.sendMessage(target, placeholderManager.setPlaceholders(player, config.getString("Private_Messages.Receiver.Format")
						.replace("{receiver}", target.getName())
						.replace("{receiver_displayname}", player.getDisplayName()) + message), true);

				Methods.cm_replied.put(player, target);
				Methods.cm_replied.put(target, player);

				for (Player staff : plugin.getServer().getOnlinePlayers()) {
					if ((staff != player) && (staff != target)) {
						if ((!player.hasPermission("chatmanager.bypass.socialspy")) && (!target.hasPermission("chatmanager.bypass.socialspy"))) {
							if (Methods.cm_socialSpy.contains(staff.getUniqueId())) {
								Methods.sendMessage(staff, messages.getString("Social_Spy.Format").replace("{player}", player.getName()).replace("{receiver}", target.getName()).replace("{message}", message), true);
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
						Methods.sendMessage(player, messages.getString("Private_Message.Recipient_Not_Found"), true);
						return true;
					}

					if (Methods.cm_togglePM.contains(target.getUniqueId())) {
						Methods.sendMessage(player, messages.getString("Private_Message.Toggled"), true);
						return true;
					}

					if (!player.canSee(target)) {
						if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
						return true;
					}

					if (essentialsCheck(args, messages, essentialsSupport, playerNotFound, player, target)) return true;

					if (PluginSupport.PREMIUM_VANISH.isPluginEnabled() || PluginSupport.SUPER_VANISH.isPluginEnabled() && genericVanishSupport.isVanished(target) && !player.hasPermission("chatmanager.bypass.vanish")) {
						if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
						return true;
					}

					Methods.sendMessage(player, placeholderManager.setPlaceholders(target, config.getString("Private_Messages.Sender.Format")
							.replace("{receiver}", target.getName())
							.replace("{receiver_displayname}", target.getDisplayName()) + message), true);

					Methods.sendMessage(target, placeholderManager.setPlaceholders(player, config.getString("Private_Messages.Receiver.Format")
							.replace("{receiver}", target.getName())
							.replace("{receiver_displayname}", player.getDisplayName()) + message), true);

					Methods.cm_replied.put(target, player);
					Methods.cm_replied.put(player, target);

					for (Player staff : plugin.getServer().getOnlinePlayers()) {
						if ((staff != player) && (staff != target)) {
							if ((!player.hasPermission("chatmanager.bypass.socialspy")) && (!target.hasPermission("chatmanager.bypass.socialspy"))) {
								if (Methods.cm_socialSpy.contains(staff.getUniqueId())) Methods.sendMessage(staff, messages.getString("Social_Spy.Format").replace("{player}", player.getName()).replace("{receiver}", target.getName()).replace("{message}", message), true);
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
						Methods.sendMessage(player, messages.getString("TogglePM.Disabled"), true);
					} else {
						Methods.cm_togglePM.add(player.getUniqueId());
						Methods.sendMessage(player, messages.getString("TogglePM.Enabled"), true);
					}
					return true;
				} else {
					Methods.sendMessage(player, "&cCommand Usage: &7/Togglepm", true);
				}
			} else {
				Methods.sendMessage(player, Methods.noPermission(), true);
			}
		}

		return true;
	}

	private boolean essentialsCheck(String[] args, FileConfiguration messages, EssentialsSupport essentialsSupport, String playerNotFound, Player player, Player target) {
		if (PluginSupport.ESSENTIALS.isPluginEnabled()) {
			if (essentialsSupport.getUser(target).isAfk() && (!player.hasPermission("chatmanager.bypass.afk"))) {
				Methods.sendMessage(player, messages.getString("Private_Message.AFK").replace("{target}", target.getName()), true);
				return true;
			}

			if (essentialsSupport.isIgnored(target, player) && (!player.hasPermission("chatmanager.bypass.ignored"))) {
				Methods.sendMessage(player, messages.getString("Private_Message.Ignored").replace("{target}", target.getName()), true);
				return true;
			}

			if (essentialsVanishSupport.isVanished(target) && (!player.hasPermission("chatmanager.bypass.vanish"))) {
				if (playerNotFound != null) Methods.sendMessage(player, playerNotFound.replace("{target}", args[0]), true);
				return true;
			}

			return essentialsSupport.isMuted(player);
		}

		return false;
	}
}