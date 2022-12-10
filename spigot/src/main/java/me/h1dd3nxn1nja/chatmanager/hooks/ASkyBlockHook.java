package me.h1dd3nxn1nja.chatmanager.hooks;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

public class ASkyBlockHook {
	
	ASkyBlockHook() {}
	
	public String getPlugin() {
		return "ASkyBlock";
	}
	
	public static String getIslandName(Player player) {
		return ASkyBlockAPI.getInstance().getIslandName(player.getUniqueId());
	}
	
	public static long getIslandLevel(Player player) {
		return ASkyBlockAPI.getInstance().getLongIslandLevel(player.getUniqueId());
	}
	
	public static World getIslandWorld(Player player) {
		return ASkyBlockAPI.getInstance().getIslandWorld();
	}
	
	public static int getIslandCount(Player player) {
		return ASkyBlockAPI.getInstance().getIslandCount();
	}

}