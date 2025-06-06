package com.ryderbelserion.chatmanager.api.objects;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.fusion.core.FusionCore;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;

public abstract class User {

    private final FusionCore layout = FusionCore.Provider.get();

    private final Audience audience;

    private String locale = "en-US";

    public User(@NotNull final Audience audience) {
        this.audience = audience;
    }

    public abstract SettingsManager locale();

    public void setLocale(@NotNull final Locale locale) {
        final String country = locale.getCountry();
        final String language = locale.getLanguage();

        this.locale = language + "-" + country;

        if (this.layout.isVerbose()) {
            this.layout.getLogger().warn("Country: {}, Language: {}", country, language);
        }
    }


    public @NotNull final Audience getAudience() {
        return this.audience;
    }

    public @NotNull final String getLocale() {
        return this.locale;
    }
}