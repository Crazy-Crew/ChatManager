package me.h1dd3nxn1nja.chatmanager.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Ping {

	public static int getPing(Player player) {

		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		
		if (!player.getClass().getName().equals("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer")) {
            player = Bukkit.getPlayer(player.getUniqueId());
		}

		try {
			
			if (Version.getCurrentVersion().isOlder(Version.v1_16_R2)) {
				Class<?> CPClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
				Object craftPlayer = CPClass.cast(player);

				Method getHandle = craftPlayer.getClass().getMethod("getHandle", new Class[0]);
				Object EntityPlayer = getHandle.invoke(craftPlayer, new Object[0]);

				Field ping = EntityPlayer.getClass().getDeclaredField("ping");

				return ping.getInt(EntityPlayer);
			} else {
				int ping = player.getPing();
				return ping;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}