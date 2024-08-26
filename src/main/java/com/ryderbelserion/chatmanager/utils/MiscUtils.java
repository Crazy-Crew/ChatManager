package com.ryderbelserion.chatmanager.utils;

import com.ryderbelserion.chatmanager.ChatManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.util.UUID;

public class MiscUtils {

    private static final ChatManager plugin = ChatManager.get();

    public static void playSound(final YamlConfiguration config, final String path) {
        for (final Player player : plugin.getServer().getOnlinePlayers()) {
            playSound(player, config, path);
        }
    }

    public static void playSound(final Player player, final YamlConfiguration config, final String path) {
        final String sound = config.getString(path + ".value", "entity.player.levelup");
        final boolean isEnabled = config.contains(path + ".toggle") && config.getBoolean(path + ".toggle");
        final double volume = config.contains(path + ".volume") ? config.getDouble(path + ".volume") : 1.0;
        final double pitch = config.contains(path + ".pitch") ? config.getDouble(path + ".pitch") : 1.0;

        if (isEnabled) {
            playSound(player, sound, volume, pitch);
        }
    }

    public static void playSound(final Player player, final String name, final double volume, final double pitch) {
        final Sound sound = Sound.sound(Key.key(name), Sound.Source.PLAYER, (float) volume, (float) pitch);

        final Location location = player.getLocation();

        player.playSound(sound, location.x(), location.y(), location.z());
    }

    public static boolean inRange(final UUID uuid, final UUID receiver, final int radius) {
        final Player player = plugin.getServer().getPlayer(uuid);
        final Player other = plugin.getServer().getPlayer(receiver);

        if (player == null || other == null) return false;

        final Location one = player.getLocation();
        final Location two = other.getLocation();

        if (two.getWorld().getName().equals(one.getWorld().getName())) {
            return two.distanceSquared(one) <= radius * radius;
        }

        return false;
    }

    public static boolean inSameWorld(final UUID uuid, final UUID receiver) {
        final Player player = plugin.getServer().getPlayer(uuid);
        final Player other = plugin.getServer().getPlayer(receiver);

        if (player == null || other == null) return false;

        final Location one = player.getLocation();
        final Location two = other.getLocation();

        return two.getWorld().getName().equals(one.getWorld().getName());
    }
}