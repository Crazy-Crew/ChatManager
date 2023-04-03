package me.h1dd3nxn1nja.chatmanager.support.vanish;

import com.earth2me.essentials.User;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.api.interfaces.VanishController;
import me.h1dd3nxn1nja.chatmanager.support.PluginManager;
import org.bukkit.entity.Player;

public class EssentialsVanishSupport implements VanishController {

    private final ChatManager plugin = ChatManager.getPlugin();

    private final PluginManager crazyManager = plugin.getPluginManager();

    @Override
    public boolean isVanished(Player player) {
        if (crazyManager.getEssentialsSupport().isEssentialsReady()) return false;

        User user = crazyManager.getEssentialsSupport().getEssentials().getUser(player);

        if (user == null) return false;

        return user.isVanished();
    }
}