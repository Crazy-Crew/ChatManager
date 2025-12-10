package com.ryderbelserion.chatmanager.api.interfaces;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public interface IUser {

    void sendMessage(@NotNull final Key key, @NotNull final Map<String, String> placeholders);

    default void sendMessage(@NotNull final Key key) {
        sendMessage(key, new HashMap<>());
    }

    Component getComponent(@NotNull final Key key, @NotNull final Map<String, String> placeholders);

    default Component getComponent(@NotNull final Key key) {
        return getComponent(key, new HashMap<>());
    }

    boolean hasPermission(@NotNull final String permission);

    void setLocale(@NotNull final Locale locale);

    Audience getAudience();

    Key getLocale();

}