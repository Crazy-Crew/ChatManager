package com.ryderbelserion.chatmanager.plugins;

import com.ryderbelserion.fusion.kyori.mods.objects.Mod;
import com.ryderbelserion.fusion.paper.FusionPaper;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class VaultSupport extends Mod {

    private static final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    private static Permission permission;
    private static Chat chat;

    public VaultSupport(@NotNull final FusionPaper fusion) {
        super(fusion);
    }

    @Override
    public Mod start() {
        if (!isEnabled()) {
            return this;
        }

        setupChat();
        setupPermissions();

        return this;
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