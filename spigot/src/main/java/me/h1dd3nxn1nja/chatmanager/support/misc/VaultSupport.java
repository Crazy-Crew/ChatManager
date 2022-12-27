package me.h1dd3nxn1nja.chatmanager.support.misc;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultSupport {

    private Permission permission;
    private Chat chat;

    public VaultSupport() {
        permission = null;
        chat = null;
    }

    public void configure() {
        setupChat();
        setupPermissions();
    }

    public String getPlayerPrefix(Player player) {
        if (chat == null) return "";
        if (chat.getPlayerPrefix(player) == null) return "";

        return chat.getPlayerPrefix(player);
    }

    public String getPlayerSuffix(Player player) {
        if (chat == null) return "";
        if (chat.getPlayerSuffix(player) == null) return "";

        return chat.getPlayerSuffix(player);
    }

    public String getPlayerGroup(Player player) {
        if (chat.getPrimaryGroup(player) == null) return "";

        return chat.getPrimaryGroup(player);
    }

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);

        if (permissionProvider != null) permission = permissionProvider.getProvider();

    }

    private void setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);

        if (chatProvider != null) chat = chatProvider.getProvider();
    }

    public boolean isChatReady() {
        return chat != null;
    }

    public boolean isPermissionReady() {
        return permission != null;
    }

    public Permission getPermission() {
        return permission;
    }
}