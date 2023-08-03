package me.h1dd3nxn1nja.chatmanager.paper.support.vanish;

import com.ryderbelserion.chatmanager.paper.api.interfaces.VanishController;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public class GenericVanishSupport implements VanishController {

	public boolean isVanished(Player player) {
		for (MetadataValue localMetadataValue : player.getMetadata("vanished")) {
			if (localMetadataValue.asBoolean()) return true;
		}

		return false;
	}
}