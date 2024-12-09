package com.ryderbelserion.chatmanager.api.users.objects;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import java.util.Locale;
import java.util.UUID;

public class PaperUser extends User {

    private final Audience audience;
    private final UUID uniqueId;
    private final String name;

    public PaperUser(final Audience audience, final UUID uniqueId, final String name) {
        this.audience = audience;
        this.uniqueId = uniqueId;
        this.name = name;
    }

    @Override
    public Component getDisplayName() {
        return this.audience.getOrDefault(Identity.DISPLAY_NAME, Component.text(getName()));
    }

    @Override
    public Locale getLocale() {
        return this.audience.getOrDefault(Identity.LOCALE, Locale.ENGLISH);
    }

    @Override
    public UUID getUUID() {
        return this.uniqueId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Audience getAudience() {
        return this.audience;
    }
}