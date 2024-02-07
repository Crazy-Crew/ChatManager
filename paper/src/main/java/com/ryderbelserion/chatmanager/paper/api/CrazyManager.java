package com.ryderbelserion.chatmanager.paper.api;

import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.paper.support.PluginManager;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.ArrayList;

public class CrazyManager {

    @NotNull
    private final ChatManager plugin = ChatManager.get();

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

    public ArrayList<String> getAllLogFiles() {
        ArrayList<String> files = new ArrayList<>();

        String[] file = new File(plugin.getDataFolder(), "/Logs").list();

        if (file != null) {
            for (String name: file) {
                if (!name.endsWith(".txt")) continue;

                files.add(name.replaceAll(".txt", ""));
            }
        }

        return files;
    }

    public PlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }
}