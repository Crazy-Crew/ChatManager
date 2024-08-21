package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.api.enums.Permissions;
import com.ryderbelserion.chatmanager.cache.CacheListener;
import com.ryderbelserion.chatmanager.cache.UserManager;
import com.ryderbelserion.chatmanager.cache.objects.User;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.listeners.chat.ChatListener;
import com.ryderbelserion.chatmanager.listeners.chat.DelayListener;
import com.ryderbelserion.chatmanager.listeners.staff.SpyListener;
import com.ryderbelserion.chatmanager.listeners.staff.StaffListener;
import com.ryderbelserion.vital.paper.Vital;
import com.ryderbelserion.vital.paper.util.scheduler.FoliaRunnable;
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

        // Register core listeners!
        getServer().getPluginManager().registerEvents(new DelayListener(), this);
        getServer().getPluginManager().registerEvents(new StaffListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new SpyListener(), this);

        // run task every minute, simply to check if a player can use staff chat.
        new FoliaRunnable(getServer().getGlobalRegionScheduler()) {
            @Override
            public void run() {
                getServer().getOnlinePlayers().forEach(player -> {
                    if (!Permissions.TOGGLE_STAFF_CHAT.hasPermission(player)) {
                        final User user = userManager.getUser(player);

                        user.isStaffChat = false;
                    }
                });
            }
        }.runAtFixedRate(this, 0, 1200); // 60 seconds

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