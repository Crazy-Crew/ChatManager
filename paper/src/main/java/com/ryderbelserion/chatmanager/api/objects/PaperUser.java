package com.ryderbelserion.chatmanager.api.objects;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.managers.ConfigManager;
import com.ryderbelserion.fusion.core.FusionCore;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaperUser {

    private final FusionCore layout = FusionCore.Provider.get();

    private final List<PlayerState> states = new ArrayList<>();

    private final Audience audience;

    private String locale = "en-US";

    public PaperUser(@NotNull final Audience audience) {
        this.audience = audience;
    }

    public void addState(@NotNull final PlayerState state) {
        this.states.add(state);
    }

    public void removeState(@NotNull final PlayerState state) {
        this.states.remove(state);
    }

    public boolean hasState(@NotNull final PlayerState state) {
        return this.states.contains(state);
    }

    public SettingsManager locale() {
        return ConfigManager.getLocale(getLocale());
    }

    public void setLocale(final Locale locale) {
        final String country = locale.getCountry();
        final String language = locale.getLanguage();

        this.locale = language + "-" + country;

        if (this.layout.isVerbose()) {
            this.layout.getLogger().warn("Country: {}, Language: {}", country, language);
        }
    }

    public final Audience getAudience() {
        return this.audience;
    }

    public final String getLocale() {
        return this.locale;
    }
}