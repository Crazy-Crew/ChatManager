package me.h1dd3nxn1nja.chatmanager;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.vital.common.api.managers.PluginManager;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;
import org.jetbrains.annotations.NotNull;

public class Methods {

	@NotNull
	private static final ChatManager plugin = ChatManager.get();

	public static void playSound(FileConfiguration config, String path) {
		String sound = config.getString(path + ".value");
		boolean isEnabled = config.contains(path + ".toggle") && config.getBoolean(path + ".toggle");
		double volume = config.contains(path + ".volume") ? config.getDouble(path + ".volume") : 1.0;
		double pitch = config.contains(path + ".pitch") ? config.getDouble(path + ".pitch") : 1.0;

		if (isEnabled) {
			for (Player online : plugin.getServer().getOnlinePlayers()) {
				try {
					online.playSound(online.getLocation(), Sound.valueOf(sound), (float) volume, (float) pitch);
				} catch (IllegalArgumentException ignored) {}
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

	public static void convert() {
		FileConfiguration config = Files.CONFIG.getConfiguration();

		if (config.contains("Messages.Join_Quit_Messages.Group_Messages")) {
			if (config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages") != null) {
                if (!config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false).isEmpty()) {
					config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false).forEach(key -> {
						if (key.equals("Enable")) return;

						String root = "Messages.Join_Quit_Messages.Group_Messages." + key;

						String oldSoundPath = root + ".Sound";

						if (config.contains(oldSoundPath)) {
							String oldSound = config.getString(oldSoundPath);

							if (oldSound != null && oldSound.isEmpty()) {
								config.set(root + ".sound.toggle", false);
							} else {
								config.set(root + ".sound.toggle", true);
							}

							config.set(root + ".sound.value", oldSound);
							config.set(root + ".sound.pitch", 1.0);
							config.set(root + ".sound.volume", 1.0);

							config.set(oldSoundPath, null);

							Files.CONFIG.save();
						}
					});
				}
			}
		}

		if (config.contains("Private_Messages.Sound")) {
			String oldSound = config.getString("Private_Messages.Sound");

			String path = "Private_Messages.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			Files.CONFIG.save();
		}

		if (config.contains("Mentions.Sound")) {
			String oldSound = config.getString("Mentions.Sound");

			String path = "Mentions.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Mentions.Sound", null);

			Files.CONFIG.save();
		}

		if (config.contains("Broadcast_Commands.Command.Broadcast.Sound")) {
			String oldSound = config.getString("Broadcast_Commands.Command.Broadcast.Sound");

			String path = "Broadcast_Commands.Command.Broadcast.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Broadcast_Commands.Command.Broadcast.Sound", null);

			Files.CONFIG.save();
		}

		if (config.contains("Broadcast_Commands.Command.Announcement.Sound")) {
			String oldSound = config.getString("Broadcast_Commands.Command.Announcement.Sound");

			String path = "Broadcast_Commands.Command.Announcement.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Broadcast_Commands.Command.Announcement.Sound", null);

			Files.CONFIG.save();
		}

		if (config.contains("Broadcast_Commands.Command.Warning.Sound")) {
			String oldSound = config.getString("Broadcast_Commands.Command.Warning.Sound");

			String path = "Broadcast_Commands.Command.Warning.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Broadcast_Commands.Command.Warning.Sound", null);

			Files.CONFIG.save();
		}

		if (config.contains("Messages.First_Join.Welcome_Message.Sound")) {
			String oldSound = config.getString("Messages.First_Join.Welcome_Message.Sound");

			String path = "Messages.First_Join.Welcome_Message.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Messages.First_Join.Welcome_Message.Sound", null);

			Files.CONFIG.save();
		}

		if (config.contains("Messages.Join_Quit_Messages.Join_Message.Sound")) {
			String oldSound = config.getString("Messages.Join_Quit_Messages.Join_Message.Sound");

			String path = "Messages.Join_Quit_Messages.Join_Message.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Messages.Join_Quit_Messages.Join_Message.Sound", null);

			Files.CONFIG.save();
		}

		if (config.contains("Messages.Join_Quit_Messages.Quit_Message.Sound")) {
			String oldSound = config.getString("Messages.Join_Quit_Messages.Quit_Message.Sound");

			String path = "Messages.Join_Quit_Messages.Quit_Message.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Messages.Join_Quit_Messages.Quit_Message.Sound", null);

			Files.CONFIG.save();
		}

		FileConfiguration autoBroadcast = Files.AUTO_BROADCAST.getConfiguration();

		if (autoBroadcast.contains("Auto_Broadcast.Global_Messages.Sound")) {
			String oldSound = autoBroadcast.getString("Auto_Broadcast.Global_Messages.Sound");

			String path = "Auto_Broadcast.Global_Messages.sound";

			assert oldSound != null;
			moveValues(autoBroadcast, oldSound, path);

			autoBroadcast.set("Auto_Broadcast.Global_Messages.Sound", null);

			Files.AUTO_BROADCAST.save();
		}

		if (autoBroadcast.contains("Auto_Broadcast.Per_World_Messages.Sound")) {
			String oldSound = autoBroadcast.getString("Auto_Broadcast.Per_World_Messages.Sound");

			String path = "Auto_Broadcast.Per_World_Messages.sound";

			assert oldSound != null;
			moveValues(autoBroadcast, oldSound, path);

			autoBroadcast.set("Auto_Broadcast.Per_World_Messages.Sound", null);

			Files.AUTO_BROADCAST.save();
		}

		if (autoBroadcast.contains("Auto_Broadcast.Actionbar_Messages.Sound")) {
			String oldSound = autoBroadcast.getString("Auto_Broadcast.Actionbar_Messages.Sound");

			String path = "Auto_Broadcast.Actionbar_Messages.sound";

			assert oldSound != null;
			moveValues(autoBroadcast, oldSound, path);

			autoBroadcast.set("Auto_Broadcast.Actionbar_Messages.Sound", null);

			Files.AUTO_BROADCAST.save();
		}

		if (autoBroadcast.contains("Auto_Broadcast.Title_Messages.Sound")) {
			String oldSound = autoBroadcast.getString("Auto_Broadcast.Title_Messages.Sound");

			String path = "Auto_Broadcast.Title_Messages.sound";

			assert oldSound != null;
			moveValues(autoBroadcast, oldSound, path);

			autoBroadcast.set("Auto_Broadcast.Title_Messages.Sound", null);

			Files.AUTO_BROADCAST.save();
		}

		if (autoBroadcast.contains("Auto_Broadcast.Bossbar_Messages.Sound")) {
			String oldSound = autoBroadcast.getString("Auto_Broadcast.Bossbar_Messages.Sound");

			String path = "Auto_Broadcast.Bossbar_Messages.sound";

            assert oldSound != null;
            moveValues(autoBroadcast, oldSound, path);

			autoBroadcast.set("Auto_Broadcast.Bossbar_Messages.Sound", null);

			Files.AUTO_BROADCAST.save();
		}
	}

	private static void moveValues(FileConfiguration autoBroadcast, String oldSound, String path) {
		if (oldSound == null || oldSound.isEmpty()) autoBroadcast.set(path + ".toggle", false); else autoBroadcast.set(path + ".toggle", true);

		autoBroadcast.set(path + ".value", oldSound);
		autoBroadcast.set(path + ".pitch", 1.0);
		autoBroadcast.set(path + ".volume", 1.0);
	}

	public static String color(String message) {
		Matcher matcher = Pattern.compile("#[a-fA-F\\d]{6}").matcher(message);
		StringBuilder buffer = new StringBuilder();

		while (matcher.find()) {
			matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
		}

		return org.bukkit.ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
	}
	
	public static String getPrefix() {
		return color(Files.MESSAGES.getConfiguration().getString("Message.Prefix"));
	}

	public static String getPrefix(String msg) {
		return getPrefix() + color(msg);
	}

	private static boolean isMuted;

	public static boolean isMuted() {
	    return isMuted;
	}

	public static void setMuted() {
		isMuted = !isMuted;
	}
	
	public static void tellConsole(String message, boolean prefix) {
		sendMessage(plugin.getServer().getConsoleSender(), message, prefix);
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
		sendMessage(commandSender, getPrefix(), message, ignorePrefix, false);
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

		commandSender.sendMessage(placeholders(ignorePrefix, prefix, commandSender, message));
	}

	public static void sendMessage(CommandSender commandSender, String message) {
		sendMessage(commandSender, "", message, true, true);
	}

	public static void broadcast(Player player, String message) {
		if (message == null || message.isEmpty()) return;

		plugin.getServer().broadcastMessage(placeholders(getPrefix().isEmpty(), player, color(message)));
	}

	public static String placeholders(final boolean isStaffChat, final boolean ignorePrefix, final String prefix, final CommandSender sender, final String message) {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

		String clonedMessage = message;

		if (!ignorePrefix) {
			clonedMessage = clonedMessage.replace("{Prefix}", prefix).replaceAll("\\{prefix}", prefix);
		}

		if (sender instanceof Player player) {
			if (PluginManager.isEnabled("PlaceholderAPI")) {
				clonedMessage = PlaceholderAPI.setPlaceholders(player, clonedMessage);
			}

			if (isStaffChat) {
				return color(clonedMessage).replaceAll("\\{server_name}", config.getString("Server_Name", "Server Name not found."));
			}

			return color(clonedMessage.replaceAll("\\{player}", player.getName()).replaceAll("\\{server_name}", config.getString("Server_Name", "Server Name not found.")));
		}

		return color(clonedMessage.replaceAll("\\{server_name}", config.getString("Server_Name", "Server Name not found.")));
	}

	public static String placeholders(final boolean ignorePrefix, final String prefix, final CommandSender sender, final String message) {
		return placeholders(false, ignorePrefix, prefix, sender, message);
	}

	public static String placeholders(boolean ignorePrefix, final CommandSender sender, final String message) {
		return placeholders(false, ignorePrefix, getPrefix(), sender, message);
	}
}