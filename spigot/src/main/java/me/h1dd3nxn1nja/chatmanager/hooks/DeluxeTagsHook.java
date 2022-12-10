package me.h1dd3nxn1nja.chatmanager.hooks;

import me.clip.deluxetags.tags.DeluxeTag;

import org.bukkit.entity.Player;

public class DeluxeTagsHook {
	
	public DeluxeTagsHook() {}
	
	public String getPlugin() {
		return "DeluxeTags";
	}
	
	public static String getPlayersDeluxeTag(Player player) {
		return DeluxeTag.getPlayerDisplayTag(player);
	}

}