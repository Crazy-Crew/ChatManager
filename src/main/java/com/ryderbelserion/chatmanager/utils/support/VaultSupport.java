package com.ryderbelserion.chatmanager.utils.support;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.vital.common.api.interfaces.IPlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

public class VaultSupport implements IPlugin {

    private static final ChatManager plugin = ChatManager.get();
    private static final Server server = plugin.getServer();

    private static Permission permission;
    private static Chat chat;

    @Override
    public void init() {
        if (!isEnabled()) return;
    }

    @Override
    public void stop() {
        if (!isEnabled()) return;
    }

    @Override
    public final boolean isEnabled() {
        return server.getPluginManager().isPluginEnabled(getName());
    }

    @Override
    public @NotNull final String getName() {
        return "Vault";
    }

    public static String getPlayerPrefix(final Player player) {
        if (chat.getPlayerPrefix(player) == null) return "";

        return chat.getPlayerPrefix(player);
    }

    public static String getPlayerSuffix(final Player player) {
        if (chat.getPlayerSuffix(player) == null) return "";

        return chat.getPlayerSuffix(player);
    }

    public static String getPlayerGroup(final Player player) {
        if (chat.getPrimaryGroup(player) == null) return "";

        return chat.getPrimaryGroup(player);
    }

    private static void setupPermissions() {
        final RegisteredServiceProvider<Permission> permissionProvider = server.getServicesManager().getRegistration(Permission.class);

        if (permissionProvider != null) permission = permissionProvider.getProvider();
    }

    private static void setupChat() {
        final RegisteredServiceProvider<Chat> chatProvider = server.getServicesManager().getRegistration(Chat.class);

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