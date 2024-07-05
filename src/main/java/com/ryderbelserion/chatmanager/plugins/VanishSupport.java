package com.ryderbelserion.chatmanager.plugins;

import com.ryderbelserion.vital.paper.plugins.interfaces.Plugin;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.UUID;

public class VanishSupport implements Plugin {

    private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    @Override
    public final boolean isEnabled() {
        return true;
    }

    @Override
    public final String getName() {
        return "GenericVanish";
    }

    @Override
    public boolean isVanished(final UUID uuid) {
        final Player user = this.plugin.getServer().getPlayer(uuid);

        if (user == null) return false;

        boolean isVanished = false;

        for (MetadataValue value : user.getMetadata("vanished")) {
            if (value.asBoolean()) {
                isVanished = true;

                break;
            }
        }

        return isVanished;
    }
}