package me.h1dd3nxn1nja.chatmanager.paper.support.vanish;

import com.earth2me.essentials.User;
import com.ryderbelserion.chatmanager.paper.api.interfaces.VanishController;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.support.EssentialsSupport;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class EssentialsVanishSupport implements VanishController {

    @NotNull
    private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    @NotNull
    private final EssentialsSupport essentialsSupport = this.plugin.getPluginManager().getEssentialsSupport();

    @Override
    public boolean isVanished(Player player) {
        if (this.essentialsSupport.isEssentialsReady()) return false;

        User user = this.essentialsSupport.getEssentials().getUser(player.getUniqueId());

        if (user == null) return false;

        return user.isVanished();
    }
}