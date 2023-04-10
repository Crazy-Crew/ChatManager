package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.api.Universal;
import com.ryderbelserion.chatmanager.commands.CommandManager;
import com.ryderbelserion.chatmanager.configs.SettingsHandler;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CrazyManager implements Universal {

    private PlaceholderManager placeholderManager;

    private SettingsHandler settingsHandler;

    private ApiLoader api;

    public void load(boolean serverStart) {
        // Always runs this at the very top but do not run this in places such as /chatmanager reload.
        // Set serverStart to false if using it in such a place.
        if (serverStart) {
            settingsHandler = new SettingsHandler();
            settingsHandler.load();

            // Load plugin support.
            pluginManager.load();

            // Load the api.
            api = new ApiLoader();
            api.load();

            // Load the commands.
            CommandManager.setup();

            // Create the placeholder manager.
            placeholderManager = new PlaceholderManager();
        }
    }

    public void stop(boolean serverShutdown) {
        // Always runs this at the very top but do not run this in places such as /chatmanager reload.
        // Set serverShutdown to false if using it in such a place.
        if (serverShutdown) {
            // DO STUFF
        }

        Bukkit.getServer().getScheduler().cancelTasks(plugin);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            api.getChatCooldowns().removeUser(player.getUniqueId());
            api.getCooldownTask().removeUser(player.getUniqueId());
            api.getCmdCooldowns().removeUser(player.getUniqueId());

            BossBarUtil bossBar = new BossBarUtil();
            bossBar.removeAllBossBars(player);
        }
    }

    public SettingsHandler getSettingsHandler() {
        return this.settingsHandler;
    }

    public PlaceholderManager getPlaceholderManager() {
        return this.placeholderManager;
    }

    public ApiLoader api() {
        return this.api;
    }
}