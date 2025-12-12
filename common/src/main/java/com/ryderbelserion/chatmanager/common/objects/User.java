package com.ryderbelserion.chatmanager.common.objects;

import com.ryderbelserion.chatmanager.api.interfaces.IUser;
import com.ryderbelserion.chatmanager.common.ChatManager;
import com.ryderbelserion.chatmanager.common.constants.Messages;
import com.ryderbelserion.chatmanager.common.registry.MessageRegistry;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class User implements IUser {

    private final FusionKyori fusion;

    private final MessageRegistry registry;
    private final ChatManager plugin;

    private final Audience audience;

    public User(@NotNull ChatManager plugin, @NotNull final Audience audience) {
        this.fusion = (FusionKyori) plugin.getFusion();
        this.registry = plugin.getMessageRegistry();
        this.audience = audience;
        this.plugin = plugin;
    }

    private Key locale = Messages.default_locale;

    @Override
    public Component getComponent(@NotNull final Key key, @NotNull final Map<String, String> placeholders) {
        return this.registry.getMessage(getLocale(), key).getComponent(getAudience(), placeholders);
    }

    @Override
    public void sendMessage(@NotNull final Key key, @NotNull final Map<String, String> placeholders) {
        this.registry.getMessage(getLocale(), key).send(getAudience(), placeholders);
    }

    @Override
    public final boolean hasPermission(@NotNull final String permission) {
        return this.plugin.hasPermission(getAudience(), permission);
    }

    @Override
    public void setLocale(@NotNull final Locale locale) {
        final String country = locale.getCountry();
        final String language = locale.getLanguage();

        final String value = "%s_%s.yml".formatted(language, country).toLowerCase();

        if (!value.equalsIgnoreCase("en_us.yml")) {
            this.locale = Key.key(ChatManager.namespace, value);
        }

        this.fusion.log("warn", "Locale Debug: Country: {}, Language: {}", country, language);
    }

    @Override
    public @NotNull final Audience getAudience() {
        return this.audience;
    }

    @Override
    public @NotNull final Key getLocale() {
        return this.locale;
    }
}