package com.ryderbelserion.chatmanager;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.ApiManager;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager extends JavaPlugin {

    private static ChatManager plugin;

    private final ApiManager apiManager;

    private final PlaceholderManager placeholderManager;

    public ChatManager(ApiManager apiManager) {
        this.apiManager = apiManager;

        this.placeholderManager = new PlaceholderManager();
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(
                new CommandAPIBukkitConfig(
                        this
                ).shouldHookPaperReload(true)
        );
    }

    @Override
    public void onEnable() {
        plugin = this;

        CommandAPI.onEnable();
    }

    @Override
    public void onDisable() {

    }

    public static ChatManager getPlugin() {
        return plugin;
    }

    public ApiManager getApiManager() {
        return this.apiManager;
    }

    // Placeholders
    public PlaceholderManager getPlaceholderManager() {
        return this.placeholderManager;
    }

    // Config Settings
    public SettingsManager getConfigSettings() {
        return getApiManager().getConfigSettings();
    }

    public SettingsManager getPluginSettings() {
        return getApiManager().getPluginSettings();
    }
}