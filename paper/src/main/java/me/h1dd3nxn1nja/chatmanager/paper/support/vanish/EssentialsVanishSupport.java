package me.h1dd3nxn1nja.chatmanager.paper.support.vanish;

import com.earth2me.essentials.User;
import com.ryderbelserion.chatmanager.paper.api.interfaces.VanishController;
import org.bukkit.entity.Player;

public class EssentialsVanishSupport implements VanishController {

    @Override
    public boolean isVanished(Player player) {
        if (essentialsSupport.isEssentialsReady()) return false;

        User user = essentialsSupport.getEssentials().getUser(player);

        if (user == null) return false;

        return user.isVanished();
    }
}