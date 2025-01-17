package com.ryderbelserion.chatmanager.paper;

import com.ryderbelserion.chatmanager.core.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.paper.listeners.chat.ChatListener;
import com.ryderbelserion.paper.FusionApi;
import com.ryderbelserion.paper.files.FileManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager extends JavaPlugin {

    private final FusionApi api = FusionApi.get();

    @Override
    public void onEnable() {
        this.api.enable(this);

        ConfigManager.load();

        final PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new ChatListener(), this);
    }

    @Override
    public void onDisable() {
        if (this.api != null) {
            this.api.disable();
        }

        getServer().getGlobalRegionScheduler().cancelTasks(this);
        getServer().getAsyncScheduler().cancelTasks(this);
    }

    public final FileManager getFileManager() {
        return getApi().getFileManager();
    }

    public final FusionApi getApi() {
        return this.api;
    }
}