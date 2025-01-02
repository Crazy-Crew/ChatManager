package com.ryderbelserion.chatmanager.plugins.papi;

import com.ryderbelserion.core.api.support.interfaces.Plugin;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPISupport implements Plugin {

    private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    @Override
    public boolean isEnabled() {
        return this.plugin.getServer().getPluginManager().isPluginEnabled(getName());
    }

    private PlaceholderAPIExpansion expansion;

    @Override
    public Plugin init() {
        if (isEnabled()) {
            this.expansion = new PlaceholderAPIExpansion();
            this.expansion.register();
        }

        return this;
    }

    @Override
    public void stop() {
        if (this.expansion != null) {
            this.expansion.unregister();
        }
    }

    @Override
    public @NotNull String getName() {
        return "PlaceholderAPI";
    }
}