package com.ryderbelserion.chatmanager.utils.old;

import java.util.UUID;
import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Methods {

	private static final ChatManager plugin = ChatManager.get();

	private static final SettingsManager config = ConfigManager.getConfig();

	public static void playSound(final YamlConfiguration config, final String path) {
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

	public static void playSound(final Player player, final YamlConfiguration config, final String path) {
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
}