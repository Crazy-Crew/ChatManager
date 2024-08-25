package com.ryderbelserion.chatmanager.utils;

import java.util.UUID;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import com.ryderbelserion.vital.common.managers.PluginManager;
import com.ryderbelserion.vital.paper.util.AdvUtil;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;

public class Methods {

	private static final ChatManager plugin = ChatManager.get();

	private static final SettingsManager config = ConfigManager.getConfig();

	public static void playSound(final FileConfiguration config, final String path) {
		String sound = config.getString(path + ".value");

		boolean isEnabled = config.contains(path + ".toggle") && config.getBoolean(path + ".toggle");
		double volume = config.contains(path + ".volume") ? config.getDouble(path + ".volume") : 1.0;
		double pitch = config.contains(path + ".pitch") ? config.getDouble(path + ".pitch") : 1.0;

		if (isEnabled) {
			for (Player online : plugin.getServer().getOnlinePlayers()) {
				online.playSound(online.getLocation(), Sound.valueOf(sound), (float) volume, (float) pitch);
			}
		}
	}

	public static void playSound(Player player, FileConfiguration config, String path) {
		String sound = config.getString(path + ".value");
		boolean isEnabled = config.contains(path + ".toggle") && config.getBoolean(path + ".toggle");
		double volume = config.contains(path + ".volume") ? config.getDouble(path + ".volume") : 1.0;
		double pitch = config.contains(path + ".pitch") ? config.getDouble(path + ".pitch") : 1.0;

		if (isEnabled) {
			player.playSound(player.getLocation(), Sound.valueOf(sound), (float) volume, (float) pitch);
		}
	}
	
	public static boolean inRange(UUID uuid, UUID receiver, int radius) {
		Player player = plugin.getServer().getPlayer(uuid);
		Player other = plugin.getServer().getPlayer(receiver);

		if (other.getLocation().getWorld().equals(player.getLocation().getWorld())) {
			return other.getLocation().distanceSquared(player.getLocation()) <= radius * radius;
		}

		return false;
	}
	
	public static boolean inWorld(UUID uuid, UUID receiver) {
		Player player = plugin.getServer().getPlayer(uuid);
		Player other = plugin.getServer().getPlayer(receiver);

		return other.getLocation().getWorld().equals(player.getLocation().getWorld());
	}

	public static void sendMessage(CommandSender commandSender, String message, boolean ignorePrefix) {
		sendMessage(commandSender, config.getProperty(ConfigKeys.prefix), message, ignorePrefix, false);
	}

	public static void sendMessage(CommandSender commandSender, String prefix, String message, boolean ignorePrefix) {
		sendMessage(commandSender, prefix, message, ignorePrefix, false);
	}

	public static void sendMessage(CommandSender commandSender, String prefix, String message, boolean ignorePrefix, boolean isStaffChat) {
		if (message == null || message.isEmpty()) return;

		if (commandSender instanceof Player player) {
			player.sendMessage(placeholders(isStaffChat, ignorePrefix, prefix, player, message));

			return;
		}

		commandSender.sendRichMessage(placeholders(ignorePrefix, prefix, commandSender, message));
	}

	public static void sendMessage(CommandSender commandSender, String message) {
		sendMessage(commandSender, "", message, true, true);
	}

	public static void broadcast(Player player, String message) {
		if (message == null || message.isEmpty()) return;

		plugin.getServer().broadcast(AdvUtil.parse(placeholders(config.getProperty(ConfigKeys.prefix).isEmpty(), player, message)));
	}

	public static String placeholders(final boolean isStaffChat, final boolean ignorePrefix, final String prefix, final CommandSender sender, final String message) {
		String clonedMessage = message;

		if (!ignorePrefix) {
			clonedMessage = clonedMessage.replace("{Prefix}", prefix).replaceAll("\\{prefix}", prefix);
		}

		if (sender instanceof Player player) {
			if (PluginManager.isEnabled("PlaceholderAPI")) {
				clonedMessage = PlaceholderAPI.setPlaceholders(player, clonedMessage);
			}

			if (isStaffChat) {
				return clonedMessage.replaceAll("\\{server_name}", config.getProperty(ConfigKeys.server_name));
			}

			return clonedMessage.replaceAll("\\{player}", player.getName()).replaceAll("\\{server_name}", config.getProperty(ConfigKeys.server_name));
		}

		return clonedMessage.replaceAll("\\{server_name}", config.getProperty(ConfigKeys.server_name));
	}

	public static String placeholders(final boolean ignorePrefix, final String prefix, final CommandSender sender, final String message) {
		return placeholders(false, ignorePrefix, prefix, sender, message);
	}

	public static String placeholders(boolean ignorePrefix, final CommandSender sender, final String message) {
		return placeholders(false, ignorePrefix, config.getProperty(ConfigKeys.prefix), sender, message);
	}
}