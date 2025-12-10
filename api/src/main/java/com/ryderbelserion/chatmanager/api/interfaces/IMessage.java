package com.ryderbelserion.chatmanager.api.interfaces;

import com.ryderbelserion.chatmanager.api.interfaces.platform.IChatManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;

public interface IMessage {

    void broadcast(@NotNull final IChatManager instance, @NotNull final Audience audience, @NotNull final Map<String, String> placeholders);

    default void broadcast(@NotNull final IChatManager instance, @NotNull final Audience audience) {
        broadcast(instance, audience, new HashMap<>());
    }

    void send(@NotNull final Audience audience, @NotNull final Map<String, String> placeholders);

    default void send(@NotNull final Audience audience) {
        send(audience, new HashMap<>());
    }

    Component getComponent(@NotNull final Audience audience, @NotNull final Map<String, String> placeholders);

    default Component getComponent(@NotNull final Audience audience) {
        return getComponent(audience, new HashMap<>());
    }

    String getString(@NotNull final Audience audience);
}