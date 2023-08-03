package me.h1dd3nxn1nja.chatmanager.paper;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.h1dd3nxn1nja.chatmanager.paper.support.PluginSupport;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;

public class Methods {

	private static final ChatManager plugin = ChatManager.getPlugin();

	private static final SettingsManager settingsManager = plugin.getSettingsManager();

	private static final String format = settingsManager.getConfig().getString("Hex_Color_Format");
	private static final Pattern HEX_PATTERN = Pattern.compile(format + "([A-Fa-f0-9]{6})");

	public static String color(String message) {
		Matcher matcher = HEX_PATTERN.matcher(message);
		StringBuilder buffer = new StringBuilder();

		while (matcher.find()) {
			matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
		}

		return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
	}

	public static String color(UUID uuid, String message) {
		Matcher matcher = HEX_PATTERN.matcher(message);
		StringBuilder buffer = new StringBuilder();

		while (matcher.find()) {
			matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
		}

		Player player = plugin.getServer().getPlayer(uuid);

		return PluginSupport.PLACEHOLDERAPI.isPluginEnabled() ? ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, matcher.appendTail(buffer).toString())) : ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
	}
	
	public static String getPrefix() {
		return color(settingsManager.getMessages().getString("Message.Prefix"));
	}
	
	public static String noPermission() {
		return color(settingsManager.getMessages().getString("Message.No_Permission").replace("{Prefix}", getPrefix()));
	}

	private static boolean isMuted;

	public static boolean isMuted() {
	    return isMuted;
	}

	public static void setMuted() {
		isMuted = !isMuted;
	}
	
	public static void tellConsole(String message, boolean prefix) {
		if (prefix) {
			sendMessage(plugin.getServer().getConsoleSender(), message, true);
			return;
		}

		sendMessage(plugin.getServer().getConsoleSender(), message, false);
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

	public static void sendMessage(CommandSender commandSender, String message, boolean prefixToggle) {
		if (message == null || message.isEmpty()) return;

		String prefix = getPrefix();

		if (commandSender instanceof Player player) {
			if (!prefix.isEmpty() && prefixToggle) player.sendMessage(color(message.replace("{Prefix}", prefix))); else player.sendMessage(color(message));

			return;
		}

		if (!prefix.isEmpty() && prefixToggle) commandSender.sendMessage(color(message.replace("{Prefix}", prefix))); else commandSender.sendMessage(color(message));
	}

	public static void broadcast(String message) {
		if (message == null || message.isEmpty()) return;

		String prefix = getPrefix();

		if (!prefix.isEmpty()) plugin.getServer().broadcastMessage(color(message.replace("{Prefix}", prefix))); else plugin.getServer().broadcastMessage(color(message));
	}
}