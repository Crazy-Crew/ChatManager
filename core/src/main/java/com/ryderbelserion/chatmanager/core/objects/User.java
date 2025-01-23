package com.ryderbelserion.chatmanager.core.objects;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.core.managers.configs.ConfigManager;
import com.ryderbelserion.core.FusionLayout;
import com.ryderbelserion.core.FusionProvider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import java.util.Locale;

public class User {

    private final FusionLayout layout = FusionProvider.get();

    private final Audience audience;

    private String locale = "en-US";

    public User(final Audience audience) {
        this.audience = audience;

        setLocale(this.audience.getOrDefault(Identity.LOCALE, Locale.ENGLISH));
    }

    public void setLocale(final Locale locale) {
        final String country = locale.getCountry();
        final String language = locale.getLanguage();

        this.locale = language + "-" + country;

        if (this.layout.isVerbose()) {
            this.layout.getLogger().warn("Country: {}, Language: {}", country, language);
        }
    }

    public SettingsManager getLocale() {
        return ConfigManager.getLocale(this.locale + ".yml");
    }

    public final Audience getAudience() {
        return this.audience;
    }
}