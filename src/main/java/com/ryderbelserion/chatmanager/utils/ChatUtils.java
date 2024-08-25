package com.ryderbelserion.chatmanager.utils;

import com.ryderbelserion.vital.common.api.interfaces.IPlugin;
import java.util.ArrayList;
import java.util.List;

public class ChatUtils {

    public static List<IPlugin> plugins = new ArrayList<>();

    public static void add(final IPlugin plugin) {
        plugins.add(plugin);
    }

    public static void remove(final IPlugin plugin) {
        IPlugin found = null;

        for (IPlugin key : plugins) {
            if (key.getName().equals(plugin.getName())) {
                found = key;

                break;
            }
        }

        if (found == null) return;

        plugins.remove(found);
    }

    public static List<IPlugin> getPlugins() {
        return plugins;
    }
}