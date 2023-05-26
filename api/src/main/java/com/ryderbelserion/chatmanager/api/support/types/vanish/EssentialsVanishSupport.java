package com.ryderbelserion.chatmanager.api.support.types.vanish;

import com.ryderbelserion.chatmanager.api.support.types.vanish.interfaces.VanishSupport;
import org.bukkit.entity.Player;

public class EssentialsVanishSupport implements VanishSupport {

    @Override
    public boolean isVanished(Player player) {
        //if (crazyManager.getEssentialsSupport().isEssentialsReady()) return false;

        //User user = crazyManager.getEssentialsSupport().getEssentials().getUser(player);

        //if (user == null) return false;

       // return user.isVanished();
        return false;
    }
}