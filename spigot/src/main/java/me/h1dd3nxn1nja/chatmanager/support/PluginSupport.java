package me.h1dd3nxn1nja.chatmanager.support;

import me.h1dd3nxn1nja.chatmanager.ChatManager;

public enum PluginSupport {

    ESSENTIALS("Essentials"),
    VAULT("Vault"),
    SUPER_VANISH("SuperVanish"),
    PLACEHOLDERAPI("PlaceholderAPI"),
    LUCKPERMS("LuckPerms"),
    PREMIUM_VANISH("PremiumVanish");

    private final String name;

    private final ChatManager plugin = ChatManager.getPlugin();

    PluginSupport(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isPluginEnabled() {
        return plugin.getServer().getPluginManager().isPluginEnabled(name);
    }
}