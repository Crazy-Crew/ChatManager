package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.api.cache.listeners.CacheListener;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.commands.CommandManager;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.listeners.TrafficListener;
import com.ryderbelserion.chatmanager.listeners.chat.ChatListener;
import com.ryderbelserion.chatmanager.listeners.chat.DelayListener;
import com.ryderbelserion.chatmanager.listeners.staff.SpyListener;
import com.ryderbelserion.chatmanager.listeners.staff.StaffListener;
import com.ryderbelserion.chatmanager.utils.LogUtils;
import com.ryderbelserion.chatmanager.utils.TaskUtils;
import com.ryderbelserion.chatmanager.utils.support.EssentialsSupport;
import com.ryderbelserion.chatmanager.utils.support.PlaceholderAPISupport;
import com.ryderbelserion.chatmanager.utils.support.VaultSupport;
import com.ryderbelserion.chatmanager.utils.support.generic.GenericVanish;
import com.ryderbelserion.vital.common.managers.PluginManager;
import com.ryderbelserion.vital.paper.Vital;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.List;
import java.util.Locale;

public class ChatManager extends Vital {

    public static ChatManager get() {
        return JavaPlugin.getPlugin(ChatManager.class);
    }

    private final UserManager userManager;
    private final long startTime;

    public ChatManager() {
        this.startTime = System.nanoTime();

        this.userManager = new UserManager();
    }

    @Override
    public void onEnable() {
        getFileManager().addFile(new File(getDataFolder(), "rules.yml")).init();

        ConfigManager.load();

        LogUtils.create(true);

        List.of(
                new VaultSupport(),
                new GenericVanish(),
                new EssentialsSupport(),
                new PlaceholderAPISupport()
        ).forEach(PluginManager::registerPlugin);

        PluginManager.printPlugins();

        List.of(
                new CacheListener(),

                new TrafficListener(),
                new DelayListener(),
                new StaffListener(),
                new ChatListener(),
                new SpyListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        // Monitor staff changes
        TaskUtils.startMonitoringTask();

        // Register commands.
        CommandManager.load();

        // Start metrics
        new CustomMetrics().start();

        if (isVerbose()) {
            getComponentLogger().info("Done ({})!", String.format(Locale.ROOT, "%.3fs", (double) (System.nanoTime() - this.startTime) / 1.0E9D));
        }
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