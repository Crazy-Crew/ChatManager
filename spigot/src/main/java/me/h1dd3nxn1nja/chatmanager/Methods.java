package me.h1dd3nxn1nja.chatmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.h1dd3nxn1nja.chatmanager.utils.ServerProtocol;
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
	
	public final static Pattern HEX_COLOR_PATTERN = Pattern.compile("#([A-Fa-f0-9]{6})");
	
	public static String color(String message) {
		if (ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1)) {
			Matcher matcher = HEX_COLOR_PATTERN.matcher(message);
			StringBuffer buffer = new StringBuffer();
			
			while (matcher.find()) {
				matcher.appendReplacement(buffer, ChatColor.of(matcher.group()).toString());
			}

			return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
		}

		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String color(Player player, String message) {
		if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			if (ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1)) {
				Matcher matcher = HEX_COLOR_PATTERN.matcher(message);
				StringBuffer buffer = new StringBuffer();

				while (matcher.find()) {
					matcher.appendReplacement(buffer, ChatColor.of(matcher.group()).toString());
				}

				return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, matcher.appendTail(buffer).toString()));
			}
		}

		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String getPrefix() {
		return color(settingsManager.getMessages().getString("Message.Prefix"));
	}
	
	public static String noPermission() {
		return color(settingsManager.getMessages().getString("Message.No_Permission").replace("{Prefix}", settingsManager.getMessages().getString("Message.Prefix")));
	}
	
	public static boolean getMuted() {
	    return CommandMuteChat.muted;
	}
	
	public boolean isUppercase(String string) {
		return ListenerCaps.upperCase.contains(string);
	}
	
	public static void tellConsole(String message){
	    new BukkitRunnable() {
			public void run() {
				plugin.getServer().getConsoleSender().sendMessage(message);
			}
		}.runTask(plugin);
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
	
	public static boolean doesPluginExist(String pluginName) {
		boolean hooked = plugin.getServer().getPluginManager().getPlugin(pluginName) != null;
		if (hooked) plugin.getLogger().info("Hooked into: " + pluginName + " v" + plugin.getServer().getPluginManager().getPlugin(pluginName).getDescription().getVersion());

		return hooked;
	}
}