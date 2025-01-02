package com.ryderbelserion.chatmanager.plugins;

import com.ryderbelserion.core.api.support.interfaces.Plugin;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class VaultSupport implements Plugin {

    private static final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    @Override
    public final boolean isEnabled() {
        return plugin.getServer().getPluginManager().isPluginEnabled(getName());
    }

    private static Permission permission;
    private static Chat chat;

    @Override
    public Plugin init() {
        if (!isEnabled()) {
            return this;
        }

        setupChat();
        setupPermissions();

        return this;
    }

    @Override
    public final @NotNull String getName() {
        return "Vault";
    }

    public static String getPlayerPrefix(Player player) {
        if (chat.getPlayerPrefix(player) == null) return "";

        return chat.getPlayerPrefix(player);
    }

    public static String getPlayerSuffix(Player player) {
        if (chat.getPlayerSuffix(player) == null) return "";

        return chat.getPlayerSuffix(player);
    }

    public static String getPlayerGroup(Player player) {
        if (chat.getPrimaryGroup(player) == null) return "";

        return chat.getPrimaryGroup(player);
    }

    private static void setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(Permission.class);

        if (permissionProvider != null) permission = permissionProvider.getProvider();

    }

    private static void setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = plugin.getServer().getServicesManager().getRegistration(Chat.class);

        if (chatProvider != null) chat = chatProvider.getProvider();
    }

    public static boolean isChatReady() {
        return chat != null;
    }

    public static boolean isPermissionReady() {
        return permission != null;
    }

    public static Permission getPermission() {
        return permission;
    }
}