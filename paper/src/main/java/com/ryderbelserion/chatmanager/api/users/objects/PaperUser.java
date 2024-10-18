package com.ryderbelserion.chatmanager.api.users.objects;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import java.util.UUID;

public class PaperUser extends User {

    private final Player player;

    public PaperUser(final Player player) {
        this.player = player;
    }

    @Override
    public @NotNull final Component getDisplayName() {
        return this.player.displayName();
    }

    @Override
    public @NotNull final Player getAudience() {
        return this.player;
    }

    @Override
    public @NotNull final Locale getLocale() {
        return this.player.locale();
    }

    @Override
    public @NotNull final String getName() {
        return this.player.getName();
    }

    @Override
    public @NotNull final UUID getUUID() {
        return this.player.getUniqueId();
    }
}