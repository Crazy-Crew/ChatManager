package com.ryderbelserion.chatmanager.api.objects;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.enums.commands.RadiusType;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.managers.ConfigManager;
import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PaperUser {

    private final FusionCore layout = FusionCore.Provider.get();

    private final List<PlayerState> states = new ArrayList<>();

    private final Map<String, Integer> delays = new HashMap<>();

    private final Map<String, String> spam = new HashMap<>();

    private final Audience audience;

    private RadiusType radius = RadiusType.GLOBAL_CHAT;

    private ScheduledTask task = null;

    private UUID lastReply = null;

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

    public void setRadius(@NotNull final RadiusType radius) {
        this.radius = radius;
    }

    public void addDelay(final String name, final int delay) {
        this.delays.put(name, delay);
    }

    public void removeDelay(final String name) {
        this.delays.remove(name);
    }

    public boolean hasDelay(final String name) {
        return this.delays.containsKey(name);
    }

    public int getDelay(final String name) {
        return this.delays.get(name);
    }

    public void addSpam(final String name, final String message) {
        this.spam.put(name, message);
    }

    public void removeSpam(final String name) {
        this.spam.remove(name);
    }

    public boolean hasSpam(final String name) {
        return this.spam.containsKey(name);
    }

    public String getSpam(final String name) {
        return this.spam.get(name);
    }

    public void setLastReply(final UUID lastReply) {
        this.lastReply = lastReply;
    }

    public boolean hasLastReply() {
        return this.lastReply != null;
    }

    public UUID getLastReply() {
        return this.lastReply;
    }

    public void purge() {
        this.lastReply = null;
        this.delays.clear();
        this.spam.clear();

        stopTask();
    }

    public void runTaskInterval(final Location location, final int delay, final int interval, final Consumer<FoliaScheduler> task) {
        this.task = new FoliaScheduler(location) {
            @Override
            public void run() {
                task.accept(this);
            }
        }.runAtFixedRate(delay, interval);
    }

    public void stopTask() {
        if (this.task != null) {
            this.task.cancel();
        }

        this.task = null;
    }

    public RadiusType getRadius() {
        return this.radius;
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