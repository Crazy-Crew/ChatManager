package me.me.h1dd3nxn1nja.chatmanager.hooks;

import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class SuperVanishHook {

	public SuperVanishHook() {}

	public String getPluginSuper() {
		return "SuperVanish";
	}
	
	public String getPluginPremium() {
		return "PremiumVanish";
	}

	public static boolean isVanished(Player player) {
		for (MetadataValue localMetadataValue : player.getMetadata("vanished")) {
			if (localMetadataValue.asBoolean()) {
				return true;
			}
		}
		return false;
	}
}
