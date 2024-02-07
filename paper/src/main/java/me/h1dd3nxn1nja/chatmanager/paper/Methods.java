package me.h1dd3nxn1nja.chatmanager.paper;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ryderbelserion.chatmanager.paper.files.enums.Files;
import me.h1dd3nxn1nja.chatmanager.paper.support.PluginSupport;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Methods {

	@NotNull
	private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

	private final String format = Files.CONFIG.getFile().getString("Hex_Color_Format");
	private final Pattern HEX_PATTERN = Pattern.compile(format + "([A-Fa-f0-9]{6})");

	public String color(String message) {
		Matcher matcher = this.HEX_PATTERN.matcher(message);
		StringBuilder buffer = new StringBuilder();

		while (matcher.find()) {
			matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
		}

		return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
	}

	public void playSound(FileConfiguration config, String path) {
		String sound = config.getString(path + ".value");
		boolean isEnabled = config.contains(path + ".toggle") && config.getBoolean(path + ".toggle");
		double volume = config.contains(path + ".volume") ? config.getDouble(path + ".volume") : 1.0;
		double pitch = config.contains(path + ".pitch") ? config.getDouble(path + ".pitch") : 1.0;

		if (isEnabled) {
			for (Player online : this.plugin.getServer().getOnlinePlayers()) {
				try {
					online.playSound(online.getLocation(), Sound.valueOf(sound), (float) volume, (float) pitch);
				} catch (IllegalArgumentException ignored) {}
			}
		}
	}

	public void playSound(Player player, FileConfiguration config, String path) {
		String sound = config.getString(path + ".value");
		boolean isEnabled = config.contains(path + ".toggle") && config.getBoolean(path + ".toggle");
		double volume = config.contains(path + ".volume") ? config.getDouble(path + ".volume") : 1.0;
		double pitch = config.contains(path + ".pitch") ? config.getDouble(path + ".pitch") : 1.0;

		if (isEnabled) {
			player.playSound(player.getLocation(), Sound.valueOf(sound), (float) volume, (float) pitch);
		}
	}

	public void convert() {
		FileConfiguration config = Files.CONFIG.getFile();

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

							Files.CONFIG.saveFile();
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

			Files.CONFIG.saveFile();
		}

		if (config.contains("Mentions.Sound")) {
			String oldSound = config.getString("Mentions.Sound");

			String path = "Mentions.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Mentions.Sound", null);

			Files.CONFIG.saveFile();
		}

		if (config.contains("Broadcast_Commands.Command.Broadcast.Sound")) {
			String oldSound = config.getString("Broadcast_Commands.Command.Broadcast.Sound");

			String path = "Broadcast_Commands.Command.Broadcast.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Broadcast_Commands.Command.Broadcast.Sound", null);

			Files.CONFIG.saveFile();
		}

		if (config.contains("Broadcast_Commands.Command.Announcement.Sound")) {
			String oldSound = config.getString("Broadcast_Commands.Command.Announcement.Sound");

			String path = "Broadcast_Commands.Command.Announcement.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Broadcast_Commands.Command.Announcement.Sound", null);

			Files.CONFIG.saveFile();
		}

		if (config.contains("Broadcast_Commands.Command.Warning.Sound")) {
			String oldSound = config.getString("Broadcast_Commands.Command.Warning.Sound");

			String path = "Broadcast_Commands.Command.Warning.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Broadcast_Commands.Command.Warning.Sound", null);

			Files.CONFIG.saveFile();
		}

		if (config.contains("Messages.First_Join.Welcome_Message.Sound")) {
			String oldSound = config.getString("Messages.First_Join.Welcome_Message.Sound");

			String path = "Messages.First_Join.Welcome_Message.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Messages.First_Join.Welcome_Message.Sound", null);

			Files.CONFIG.saveFile();
		}

		if (config.contains("Messages.Join_Quit_Messages.Join_Message.Sound")) {
			String oldSound = config.getString("Messages.Join_Quit_Messages.Join_Message.Sound");

			String path = "Messages.Join_Quit_Messages.Join_Message.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Messages.Join_Quit_Messages.Join_Message.Sound", null);

			Files.CONFIG.saveFile();
		}

		if (config.contains("Messages.Join_Quit_Messages.Quit_Message.Sound")) {
			String oldSound = config.getString("Messages.Join_Quit_Messages.Quit_Message.Sound");

			String path = "Messages.Join_Quit_Messages.Quit_Message.sound";

			assert oldSound != null;
			moveValues(config, oldSound, path);

			config.set("Messages.Join_Quit_Messages.Quit_Message.Sound", null);

			Files.CONFIG.saveFile();
		}

		FileConfiguration autoBroadcast = Files.AUTO_BROADCAST.getFile();

		if (autoBroadcast.contains("Auto_Broadcast.Global_Messages.Sound")) {
			String oldSound = autoBroadcast.getString("Auto_Broadcast.Global_Messages.Sound");

			String path = "Auto_Broadcast.Global_Messages.sound";

			assert oldSound != null;
			moveValues(autoBroadcast, oldSound, path);

			autoBroadcast.set("Auto_Broadcast.Global_Messages.Sound", null);

			Files.AUTO_BROADCAST.saveFile();
		}

		if (autoBroadcast.contains("Auto_Broadcast.Per_World_Messages.Sound")) {
			String oldSound = autoBroadcast.getString("Auto_Broadcast.Per_World_Messages.Sound");

			String path = "Auto_Broadcast.Per_World_Messages.sound";

			assert oldSound != null;
			moveValues(autoBroadcast, oldSound, path);

			autoBroadcast.set("Auto_Broadcast.Per_World_Messages.Sound", null);

			Files.AUTO_BROADCAST.saveFile();
		}

		if (autoBroadcast.contains("Auto_Broadcast.Actionbar_Messages.Sound")) {
			String oldSound = autoBroadcast.getString("Auto_Broadcast.Actionbar_Messages.Sound");

			String path = "Auto_Broadcast.Actionbar_Messages.sound";

			assert oldSound != null;
			moveValues(autoBroadcast, oldSound, path);

			autoBroadcast.set("Auto_Broadcast.Actionbar_Messages.Sound", null);

			Files.AUTO_BROADCAST.saveFile();
		}

		if (autoBroadcast.contains("Auto_Broadcast.Title_Messages.Sound")) {
			String oldSound = autoBroadcast.getString("Auto_Broadcast.Title_Messages.Sound");

			String path = "Auto_Broadcast.Title_Messages.sound";

			assert oldSound != null;
			moveValues(autoBroadcast, oldSound, path);

			autoBroadcast.set("Auto_Broadcast.Title_Messages.Sound", null);

			Files.AUTO_BROADCAST.saveFile();
		}

		if (autoBroadcast.contains("Auto_Broadcast.Bossbar_Messages.Sound")) {
			String oldSound = autoBroadcast.getString("Auto_Broadcast.Bossbar_Messages.Sound");

			String path = "Auto_Broadcast.Bossbar_Messages.sound";

            assert oldSound != null;
            moveValues(autoBroadcast, oldSound, path);

			autoBroadcast.set("Auto_Broadcast.Bossbar_Messages.Sound", null);

			Files.AUTO_BROADCAST.saveFile();
		}
	}

	private void moveValues(FileConfiguration autoBroadcast, String oldSound, String path) {
		assert oldSound != null;
		if (oldSound.isEmpty()) autoBroadcast.set(path + ".toggle", false); else autoBroadcast.set(path + ".toggle", true);

		autoBroadcast.set(path + ".value", oldSound);
		autoBroadcast.set(path + ".pitch", 1.0);
		autoBroadcast.set(path + ".volume", 1.0);
	}

	public String color(UUID uuid, String message) {
		Matcher matcher = this.HEX_PATTERN.matcher(message);
		StringBuilder buffer = new StringBuilder();

		while (matcher.find()) {
			matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
		}

		Player player = this.plugin.getServer().getPlayer(uuid);

		return PluginSupport.PLACEHOLDERAPI.isPluginEnabled() ? ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, matcher.appendTail(buffer).toString())) : ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
	}
	
	public String getPrefix() {
		return color(Files.MESSAGES.getFile().getString("Message.Prefix"));
	}
	
	public String noPermission() {
		return color(Files.MESSAGES.getFile().getString("Message.No_Permission").replace("{Prefix}", getPrefix()));
	}

	private static boolean isMuted;

	public boolean isMuted() {
	    return isMuted;
	}

	public void setMuted() {
		isMuted = !isMuted;
	}
	
	public void tellConsole(String message, boolean prefix) {
		if (prefix) {
			sendMessage(this.plugin.getServer().getConsoleSender(), message, true);
			return;
		}

		sendMessage(this.plugin.getServer().getConsoleSender(), message, false);
	}
	
	public boolean inRange(UUID uuid, UUID receiver, int radius) {
		Player player = this.plugin.getServer().getPlayer(uuid);
		Player other = this.plugin.getServer().getPlayer(receiver);

		if (other.getLocation().getWorld().equals(player.getLocation().getWorld())) {
			return other.getLocation().distanceSquared(player.getLocation()) <= radius * radius;
		}

		return false;
	}
	
	public boolean inWorld(UUID uuid, UUID receiver) {
		Player player = this.plugin.getServer().getPlayer(uuid);
		Player other = this.plugin.getServer().getPlayer(receiver);

		return other.getLocation().getWorld().equals(player.getLocation().getWorld());
	}

	public void sendMessage(CommandSender commandSender, String message, boolean prefixToggle) {
		if (message == null || message.isEmpty()) return;

		String prefix = getPrefix();

		if (commandSender instanceof Player player) {
			if (!prefix.isEmpty() && prefixToggle) player.sendMessage(color(message.replace("{Prefix}", prefix))); else player.sendMessage(color(message));

			return;
		}

		if (!prefix.isEmpty() && prefixToggle) commandSender.sendMessage(color(message.replace("{Prefix}", prefix))); else commandSender.sendMessage(color(message));
	}

	public void broadcast(String message) {
		if (message == null || message.isEmpty()) return;

		String prefix = getPrefix();

		if (!prefix.isEmpty()) this.plugin.getServer().broadcastMessage(color(message.replace("{Prefix}", prefix))); else this.plugin.getServer().broadcastMessage(color(message));
	}
}