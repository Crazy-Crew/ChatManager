package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.cache.CacheListener;
import com.ryderbelserion.chatmanager.cache.UserManager;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.listeners.chat.ChatListener;
import com.ryderbelserion.chatmanager.listeners.chat.DelayListener;
import com.ryderbelserion.vital.paper.Vital;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager extends Vital {

    public static ChatManager get() {
        return JavaPlugin.getPlugin(ChatManager.class);
    }

    private final UserManager userManager;

    public ChatManager() {
        this.userManager = new UserManager();
    }

    @Override
    public void onEnable() {
        // Load configuration file!
        ConfigManager.load();

        // Register cache listener!
        getServer().getPluginManager().registerEvents(new CacheListener(), this);

        getServer().getPluginManager().registerEvents(new DelayListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        new CustomMetrics().start();
    }

    @Override
    public void onDisable() {
        // Nuke all data on shutdown.
        getUserManager().purge();
    }

    public final UserManager getUserManager() {
        return this.userManager;
    }
}