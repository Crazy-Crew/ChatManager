package me.h1dd3nxn1nja.chatmanager.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.entity.Player;

public class Ping {

	private final static ChatManager plugin = ChatManager.getPlugin();

	public static int getPing(Player player) {

		String version = plugin.getServer().getClass().getPackage().getName().split("\\.")[3];
		
		if (!player.getClass().getName().equals("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer")) player = plugin.getServer().getPlayer(player.getUniqueId());

		try {
			
			if ((ServerProtocol.isOlder(ServerProtocol.v1_16_R1))) {
				Class<?> CPClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
				Object craftPlayer = CPClass.cast(player);

				Method getHandle = craftPlayer.getClass().getMethod("getHandle", new Class[0]);
				Object EntityPlayer = getHandle.invoke(craftPlayer, new Object[0]);

				Field ping = EntityPlayer.getClass().getDeclaredField("ping");

				return ping.getInt(EntityPlayer);
			} else {
				return player.getPing();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
}