package me.h1dd3nxn1nja.chatmanager;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.fusion.core.managers.PluginExtension;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {

	@NotNull
	private static final ChatManager plugin = ChatManager.get();

	private static final Server server = plugin.getServer();

	private static final PluginExtension extension = plugin.getPluginExtension();

	public static void playSound(final FileConfiguration config, final String path) {
		final boolean isEnabled = config.getBoolean(path + ".toggle", false);

		if (isEnabled) {
			for (final Player online : server.getOnlinePlayers()) {
				playSound(online, config, path);
			}
		}
	}

	public static void playSound(final Player player, final FileConfiguration config, final String path) {
		String sound = config.getString(path + ".value", "");

		if (sound.isEmpty()) return;

		boolean isEnabled = config.getBoolean(path + ".toggle", false);
		double volume = config.getDouble(path + ".volume", 1.0);
		double pitch = config.getDouble(path + ".pitch", 1.0);

		if (isEnabled) {
			player.playSound(player.getLocation(), Sound.valueOf(sound), (float) volume, (float) pitch);
		}
	}

	public static void convert() {
		final FileConfiguration config = Files.CONFIG.getConfiguration();

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

	private static void moveValues(final FileConfiguration autoBroadcast, final String oldSound, final String path) {
		if (oldSound == null || oldSound.isEmpty()) autoBroadcast.set(path + ".toggle", false); else autoBroadcast.set(path + ".toggle", true);

		autoBroadcast.set(path + ".value", oldSound);
		autoBroadcast.set(path + ".pitch", 1.0);
		autoBroadcast.set(path + ".volume", 1.0);
	}

	public static String color(final String message) {
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
	
	public static void tellConsole(final String message, final boolean prefix) {
		sendMessage(server.getConsoleSender(), message, prefix);
	}
	
	public static boolean inRange(final UUID uuid, final UUID receiver, final int radius) {
		Player player = server.getPlayer(uuid);
		Player other = server.getPlayer(receiver);

		if (other.getLocation().getWorld().equals(player.getLocation().getWorld())) {
			return other.getLocation().distanceSquared(player.getLocation()) <= radius * radius;
		}

		return false;
	}
	
	public static boolean inWorld(final UUID uuid, final UUID receiver) {
		Player player = server.getPlayer(uuid);
		Player other = server.getPlayer(receiver);

		return other.getLocation().getWorld().equals(player.getLocation().getWorld());
	}

	public static void sendMessage(final CommandSender commandSender, final String message, final boolean ignorePrefix) {
		sendMessage(commandSender, getPrefix(), message, ignorePrefix, false, true);
	}

	public static void sendMessage(final CommandSender commandSender, final String prefix, final String message, final boolean ignorePrefix) {
		sendMessage(commandSender, prefix, message, ignorePrefix, false, true);
	}

	public static void sendMessage(final CommandSender commandSender, final String prefix, final String message, final boolean ignorePrefix, final boolean isStaffChat, final boolean parsePapi) {
		if (message == null || message.isEmpty()) return;

		if (commandSender instanceof Player player) {
			player.sendMessage(placeholders(isStaffChat, ignorePrefix, parsePapi, prefix, player, message));

			return;
		}

		commandSender.sendMessage(placeholders(ignorePrefix, prefix, commandSender, message));
	}

	public static void sendMessage(final CommandSender commandSender, final String message) {
		sendMessage(commandSender, "", message, true, true, true);
	}

	public static void broadcast(final Player player, final String message) {
		if (message == null || message.isEmpty()) return;

		server.broadcastMessage(placeholders(getPrefix().isEmpty(), player, color(message)));
	}

	public static String placeholders(final boolean isStaffChat, final boolean ignorePrefix, final boolean parsePapi, final String prefix, final CommandSender sender, final String message) {
		String clonedMessage = message;

		if (!ignorePrefix) {
			clonedMessage = clonedMessage.replace("{Prefix}", prefix).replaceAll("\\{prefix}", prefix);
		}

		if (sender instanceof Player player) {
			if (parsePapi && extension.isEnabled("PlaceholderAPI")) {
				clonedMessage = PlaceholderAPI.setPlaceholders(player, clonedMessage);
			}

			if (isStaffChat) {
				return color(clonedMessage);
			}

			return color(clonedMessage.replaceAll("\\{player}", player.getName()));
		}

		return color(clonedMessage);
	}

	public static String placeholders(final boolean ignorePrefix, final String prefix, final CommandSender sender, final String message) {
		return placeholders(false, ignorePrefix, true, prefix, sender, message);
	}

	public static String placeholders(boolean ignorePrefix, final CommandSender sender, final String message) {
		return placeholders(false, ignorePrefix, true, getPrefix(), sender, message);
	}
}