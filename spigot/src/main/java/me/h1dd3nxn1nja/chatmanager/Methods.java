package me.h1dd3nxn1nja.chatmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import me.h1dd3nxn1nja.chatmanager.utils.ServerProtocol;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import me.clip.placeholderapi.PlaceholderAPI;
import me.h1dd3nxn1nja.chatmanager.commands.CommandMuteChat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerCaps;

public class Methods {

	private static final ChatManager plugin = ChatManager.getPlugin();

	private static final SettingsManager settingsManager = plugin.getSettingsManager();
	
	public static HashMap<Player, Player> cm_replied = new HashMap<Player, Player>();
	public static HashMap<Player, String> cm_previousMessages = new HashMap<>();
	public static HashMap<Player, String> cm_previousCommand = new HashMap<>();
	public static HashMap<Player, Integer> cm_chatCooldown = new HashMap<>();
	public static HashMap<Player, BukkitRunnable> cm_cooldownTask = new HashMap<>();
	public static HashMap<Player, Integer> cm_commandCooldown = new HashMap<>();
	public static HashMap<Player, Integer> cm_MOTDTask = new HashMap<>();
	
	public static Set<Player> cm_cooldowns = new HashSet<>();
	
	public static HashSet<UUID> cm_pwcGlobal = new HashSet<>();
	public static HashSet<UUID> cm_pwcSpy = new HashSet<>();
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

	private static final char COLOR_CHAR = ChatColor.COLOR_CHAR;
	
	public static String color(String message) {
		if (ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1)) {
			String format = settingsManager.getConfig().getString("Hex_Color_Format");
			Pattern hex = Pattern.compile(format + "([A-Fa-f0-9]{6})");
			Matcher matcher = hex.matcher(message);
			StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

			while (matcher.find()) {
				String group = matcher.group(1);
				matcher.appendReplacement(buffer, COLOR_CHAR + "x"
						+ COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
						+ COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
						+ COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
				);
			}
		}

		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String color(Player player, String message) {
		if (ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1)) {
			String format = settingsManager.getConfig().getString("Hex_Color_Format");
			Pattern hex = Pattern.compile(format + "([A-Fa-f0-9]{6})");
			Matcher matcher = hex.matcher(message);
			StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

			while (matcher.find()) {
				String group = matcher.group(1);
				matcher.appendReplacement(buffer, COLOR_CHAR + "x"
						+ COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
						+ COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
						+ COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
				);
			}

			if (PluginSupport.PLACEHOLDERAPI.isPluginEnabled()) return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, matcher.appendTail(buffer).toString()));else return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
		}

		return ChatColor.translateAlternateColorCodes('&', message);
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
			if (receiver.getLocation().distanceSquared(player.getLocation()) <= radius * radius) return true;
		}

		return false;
	}
	
	public static boolean inWorld(Player player, Player receiver) {
		if (receiver.getLocation().getWorld().equals(player.getLocation().getWorld())) return true;

		return false;
	}

	public static void sendMessage(CommandSender commandSender, String message, boolean prefixToggle) {
		if (message == null || message.isEmpty()) return;

		String prefix = getPrefix();

		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;

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