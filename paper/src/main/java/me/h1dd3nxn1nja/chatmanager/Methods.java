package me.h1dd3nxn1nja.chatmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import me.clip.placeholderapi.PlaceholderAPI;
import me.h1dd3nxn1nja.chatmanager.commands.CommandMuteChat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerCaps;
import net.md_5.bungee.api.ChatColor;

public class Methods {

	private static final ChatManager plugin = ChatManager.getPlugin();

	private static final SettingsManager settingsManager = plugin.getSettingsManager();
	
	public static HashMap<Player, Player> cm_replied = new HashMap<Player, Player>();
	public static HashMap<Player, String> cm_previousMessages = new HashMap<>();
	public static HashMap<Player, String> cm_previousCommand = new HashMap<>();
	public static HashMap<Player, Integer> cm_chatCooldown = new HashMap<>();
	public static HashMap<Player, BukkitRunnable> cm_cooldownTask = new HashMap<>();
	public static HashMap<Player, Integer> cm_commandCooldown = new HashMap<>();
	
	public static HashSet<UUID> cm_pwcGlobal = new HashSet<>();
	public static HashSet<UUID> cm_toggleChat = new HashSet<>();
	public static HashSet<UUID> cm_toggleMentions = new HashSet<>();
	
	public static HashSet<UUID> cm_localChat = new HashSet<>();
	public static HashSet<UUID> cm_globalChat = new HashSet<>();
	public static HashSet<UUID> cm_worldChat = new HashSet<>();
	public static HashSet<UUID> cm_spyChat = new HashSet<>();
	
	public static ArrayList<UUID> cm_antiBot = new ArrayList<>();
	public static ArrayList<UUID> cm_commandSpy = new ArrayList<>();
	public static ArrayList<UUID> cm_socialSpy = new ArrayList<>();
	public static ArrayList<UUID> cm_staffChat = new ArrayList<>();
	public static ArrayList<UUID> cm_togglePM = new ArrayList<>();

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

	public static String color(Player player, String message) {
		Matcher matcher = HEX_PATTERN.matcher(message);
		StringBuilder buffer = new StringBuilder();

		while (matcher.find()) {
			matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
		}

		return PluginSupport.PLACEHOLDERAPI.isPluginEnabled() ? ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, matcher.appendTail(buffer).toString())) : ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
	}
	
	public static String getPrefix() {
		return color(settingsManager.getMessages().getString("Message.Prefix"));
	}
	
	public static String noPermission() {
		return color(settingsManager.getMessages().getString("Message.No_Permission").replace("{Prefix}", getPrefix()));
	}
	
	public static boolean getMuted() {
	    return CommandMuteChat.muted;
	}
	
	public boolean isUppercase(String string) {
		return ListenerCaps.upperCase.contains(string);
	}
	
	public static void tellConsole(String message, boolean prefix) {
		if (prefix) {
			sendMessage(plugin.getServer().getConsoleSender(), message, true);
			return;
		}

		sendMessage(plugin.getServer().getConsoleSender(), message, false);
	}
	
	public static boolean inRange(Player player, Player receiver, int radius) {
		if (receiver.getLocation().getWorld().equals(player.getLocation().getWorld())) {
			return receiver.getLocation().distanceSquared(player.getLocation()) <= radius * radius;
		}

		return false;
	}
	
	public static boolean inWorld(Player player, Player receiver) {
		return receiver.getLocation().getWorld().equals(player.getLocation().getWorld());
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