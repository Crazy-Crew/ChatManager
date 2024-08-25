package com.ryderbelserion.chatmanager.utils.support.generic;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.utils.ChatUtils;
import com.ryderbelserion.vital.common.api.interfaces.IPlugin;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class GenericVanish implements IPlugin {

    private final ChatManager plugin = ChatManager.get();

    @Override
    public @NotNull final String getName() {
        return "GenericVanish";
    }

    @Override
    public void init() {
        ChatUtils.add(this);
    }

    @Override
    public void stop() {
        if (!isEnabled()) {
            ChatUtils.remove(this);

            return;
        }

        ChatUtils.remove(this);
    }

    @Override
    public final boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isVanished(@NotNull UUID uuid) {
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