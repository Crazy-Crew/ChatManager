package com.ryderbelserion.chatmanager.plugins;

import com.ryderbelserion.vital.paper.plugins.interfaces.Plugin;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.plugin.java.JavaPlugin;

public class VaultSupport implements Plugin {

    private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    @Override
    public final boolean isEnabled() {
        return this.plugin.getServer().getPluginManager().isPluginEnabled(getName());
    }

    @Override
    public final String getName() {
        return "Vault";
    }
}