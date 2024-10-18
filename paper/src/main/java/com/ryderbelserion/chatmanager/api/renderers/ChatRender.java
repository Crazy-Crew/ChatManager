package com.ryderbelserion.chatmanager.api.renderers;

import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatRender implements ChatRenderer {

    @Override
    public @NotNull final Component render(@NotNull Player source, @NotNull Component displayName, @NotNull Component message, @NotNull Audience viewer) {
        return displayName;
    }
}