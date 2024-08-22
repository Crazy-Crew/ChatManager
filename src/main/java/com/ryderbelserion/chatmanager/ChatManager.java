package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.api.cache.listeners.CacheListener;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.commands.CommandManager;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.listeners.chat.ChatListener;
import com.ryderbelserion.chatmanager.listeners.chat.DelayListener;
import com.ryderbelserion.chatmanager.listeners.staff.SpyListener;
import com.ryderbelserion.chatmanager.listeners.staff.StaffListener;
import com.ryderbelserion.chatmanager.utils.TaskUtils;
import com.ryderbelserion.vital.paper.Vital;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

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
        getFileManager().addFile(new File(getDataFolder(), "rules.yml"));

        // Load configuration file!
        ConfigManager.load();

        // Register cache listener!
        getServer().getPluginManager().registerEvents(new CacheListener(), this);

        // Register core listeners!
        getServer().getPluginManager().registerEvents(new DelayListener(), this);
        getServer().getPluginManager().registerEvents(new StaffListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new SpyListener(), this);

        // Monitor staff changes
        TaskUtils.startMonitoringTask();

        // Register commands.
        CommandManager.load();

        // Start metrics
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