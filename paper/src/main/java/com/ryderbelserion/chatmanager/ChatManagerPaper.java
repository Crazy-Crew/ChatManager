package com.ryderbelserion.chatmanager;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.ChatManager;
import com.ryderbelserion.chatmanager.api.users.UserManager;
import com.ryderbelserion.chatmanager.common.config.ConfigManager;
import com.ryderbelserion.chatmanager.common.plugin.AbstractChatPlugin;
import com.ryderbelserion.chatmanager.common.plugin.logger.AbstractLogger;
import com.ryderbelserion.chatmanager.common.plugin.logger.PluginLogger;
import com.ryderbelserion.chatmanager.listeners.chat.ChatListener;
import com.ryderbelserion.chatmanager.loader.ChatManagerPlugin;
import com.ryderbelserion.chatmanager.api.users.PaperUserManager;
import com.ryderbelserion.chatmanager.utils.MiscUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ChatManagerPaper extends AbstractChatPlugin {

    private PaperUserManager userManager;

    private final ChatManagerPlugin plugin;
    private final PluginLogger logger;
    private final long startTime;
    private final Server server;

    private final ConfigManager configManager;

    public ChatManagerPaper(final ChatManagerPlugin plugin) {
        this.startTime = System.nanoTime(); // measure start time

        super.enable(); // enable api

        this.plugin = plugin;
        this.server = plugin.getServer();

        this.logger = new AbstractLogger(this.plugin.getComponentLogger());

        this.configManager = new ConfigManager();
    }

    @Override
    public void onLoad() {
        this.configManager.load();
    }

    @Override
    public void onEnable() {
        this.userManager = new PaperUserManager(this);

        getLogger().info("Done ({})!", String.format(Locale.ROOT, "%.3fs", (double) (System.nanoTime() - this.startTime) / 1.0E9D));
    }

    @Override
    public void onDisable() {
        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);
    }

    @Override
    protected void registerPlatformAPI(final ChatManager api) {
        this.server.getServicesManager().register(ChatManager.class, api, this.plugin, ServicePriority.Normal);
    }

    @Override
    protected void registerListeners() {
        final PluginManager pluginManager = this.server.getPluginManager();

        pluginManager.registerEvents(new ChatListener(), this.plugin);
    }

    @Override
    public @NotNull final String parse(@NotNull final Audience audience, @NotNull final String value, @NotNull final Map<String, String> placeholders) {
        @NotNull final Optional<UUID> uuid = audience.get(Identity.UUID);

        if (uuid.isPresent()) {
            final Player player = this.server.getPlayer(uuid.get());

            return MiscUtils.populatePlaceholders(player, value, placeholders);
        }

        return "";
    }

    @Override
    public @NotNull final UserManager getUserManager() {
        return this.userManager;
    }

    @Override
    public @NotNull final SettingsManager getConfig() {
        return this.configManager.getConfig();
    }

    @Override
    public @NotNull final PluginLogger getLogger() {
        return this.logger;
    }

    @Override
    public @NotNull final File getDataFolder() {
        return this.plugin.getDataFolder();
    }

    public @NotNull final ChatManagerPlugin getPlugin() {
        return this.plugin;
    }
}