package com.ryderbelserion.chatmanager.api.support.types.other;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

@Deprecated(forRemoval = true)
public class VaultSupport {

    private Permission permission;
    private Chat chat;

    public void start(Server server) {
        RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> permissionProvider = server.getServicesManager().getRegistration(Permission.class);

        if (permissionProvider != null) this.permission = permissionProvider.getProvider();

        RegisteredServiceProvider<Chat> chatProvider = server.getServicesManager().getRegistration(Chat.class);

        if (chatProvider != null) this.chat = chatProvider.getProvider();
    }

    public String getPlayerPrefix(Player player) {
        if (this.chat.getPlayerPrefix(player) == null) return "";

        return this.chat.getPlayerPrefix(player);
    }

    public String getPlayerSuffix(Player player) {
        if (this.chat.getPlayerSuffix(player) == null) return "";

        return this.chat.getPlayerSuffix(player);
    }

    public String getPlayerGroup(Player player) {
        if (this.chat.getPrimaryGroup(player) == null) return "";

        return this.chat.getPrimaryGroup(player);
    }

    public boolean isChatReady() {
        return this.chat != null;
    }

    public boolean isPermissionReady() {
        return this.permission != null;
    }

    public Permission getPermission() {
        return this.permission;
    }
}