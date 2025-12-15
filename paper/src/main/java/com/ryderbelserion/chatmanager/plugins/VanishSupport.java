package com.ryderbelserion.chatmanager.plugins;

import com.ryderbelserion.fusion.kyori.mods.objects.Mod;
import com.ryderbelserion.fusion.paper.FusionPaper;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class VanishSupport extends Mod {

    private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    public VanishSupport(@NotNull final FusionPaper fusion) {
        super(fusion);
    }

    @Override
    public final boolean isEnabled() {
        return true;
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