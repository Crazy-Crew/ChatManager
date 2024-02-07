package me.h1dd3nxn1nja.chatmanager.paper.support;

import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public enum PluginSupport {

    ESSENTIALS("Essentials"),
    VAULT("Vault"),
    SUPER_VANISH("SuperVanish"),
    PLACEHOLDERAPI("PlaceholderAPI"),
    LUCKPERMS("LuckPerms"),
    PREMIUM_VANISH("PremiumVanish");

    private final String name;

    @NotNull
    private final ChatManager plugin = ChatManager.get();

    PluginSupport(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean isPluginEnabled() {
        return this.plugin.getServer().getPluginManager().isPluginEnabled(this.name);
    }

    public Plugin getPlugin() {
        return this.plugin.getServer().getPluginManager().getPlugin(this.name);
    }
}