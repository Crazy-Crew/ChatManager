package com.ryderbelserion.api;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import com.ryderbelserion.support.PluginManager;

public class CrazyManager {

    private final ChatManager plugin = ChatManager.getPlugin();

    private final PluginManager pluginManager = plugin.getPluginManager();

    private PlaceholderManager placeholderManager;

    /**
     * Loads config changes on Server Reload or /chatmanager reload.
     *
     * @param serverReload Whether to reload certain code or not.
     */
    public void load(boolean serverReload) {
        // Always runs this at the very top but do not run this in places such as /chatmanager reload.
        // Set serverReload to false if using it in such a place.
        if (serverReload) pluginManager.load();

        // Rest of your code goes here.
        placeholderManager = new PlaceholderManager();
    }

    public PlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }
}