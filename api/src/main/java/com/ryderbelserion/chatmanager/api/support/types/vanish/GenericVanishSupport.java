package com.ryderbelserion.chatmanager.api.support.types.vanish;

import com.ryderbelserion.chatmanager.api.support.types.vanish.interfaces.VanishSupport;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class GenericVanishSupport implements VanishSupport {

	public boolean isVanished(Player player) {
		for (MetadataValue localMetadataValue : player.getMetadata("vanished")) {
			if (localMetadataValue.asBoolean()) return true;
		}

		return false;
	}
}