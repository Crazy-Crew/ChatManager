package com.ryderbelserion.chatmanager.plugins;

import com.ryderbelserion.fusion.core.api.interfaces.IPlugin;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class VanishSupport implements IPlugin {

    private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    @Override
    public final boolean isEnabled() {
        return true;
    }

    @Override
    public final @NotNull String getName() {
        return "GenericVanish";
    }

    @Override
    public boolean isVanished(@NotNull final UUID uuid) {
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