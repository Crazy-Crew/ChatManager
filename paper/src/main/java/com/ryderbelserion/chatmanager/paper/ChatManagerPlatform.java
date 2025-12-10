package com.ryderbelserion.chatmanager.paper;

import com.ryderbelserion.chatmanager.common.ChatManager;
import com.ryderbelserion.chatmanager.paper.listeners.CacheListener;
import com.ryderbelserion.chatmanager.paper.listeners.chat.ChatListener;
import com.ryderbelserion.fusion.core.api.interfaces.permissions.enums.Mode;
import com.ryderbelserion.fusion.paper.FusionPaper;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Map;

public class ChatManagerPlatform extends ChatManager {

    private final ChatManagerPlugin plugin;
    private final FusionPaper fusion;
    private final Server server;

    public ChatManagerPlatform(@NotNull final ChatManagerPlugin plugin, @NotNull final FusionPaper fusion) {
        super(fusion, plugin.getDataPath());

        this.plugin = plugin;
        this.fusion = fusion;

        this.fileManager = this.fusion.getFileManager();

        this.server = plugin.getServer();
    }

    @Override
    public void start(@NotNull final Audience audience) {
        this.fusion.init();

        super.start(audience);

        getUserRegistry().addUser(this.server.getConsoleSender());

        final PluginManager pluginManager = this.server.getPluginManager();

        List.of(
                new CacheListener(this),
                new ChatListener(this)
        ).forEach(listener -> pluginManager.registerEvents(listener, this.plugin));

        //this.fusion.getCommandManager().enable(new BaseCommand(this), "Chat me up man!", List.of());
        registerCommands();
    }

    @Override
    public void reload() {
        this.fusion.reload();

        super.reload();
    }

    @Override
    public void stop() {
        super.stop();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);
    }

    @Override
    public void registerCommands() {
        /*getPermissionRegistry().start();

        this.command = new BaseCommand(PaperCommandManager.builder()
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this.plugin));*/
    }

    @Override
    public void broadcast(@NotNull final Component component, @NotNull final String permission) {
        if (permission.isEmpty()) {
            this.server.broadcast(component);

            return;
        }

        this.server.broadcast(component, permission);
    }

    @Override
    public final boolean hasPermission(@NotNull final Audience audience, @NotNull final String permission) {
        final CommandSender sender = (CommandSender) audience;

        return sender.hasPermission(permission);
    }

    @Override
    public final boolean isConsoleSender(@NotNull final Audience audience) {
        return audience instanceof ConsoleCommandSender;
    }

    @Override
    public void registerPermission(@NotNull final Mode mode, @NotNull final String parent, @NotNull final String description, @NotNull final Map<String, Boolean> children) {
        PermissionDefault permissionDefault;

        switch (mode) {
            case NOT_OP -> permissionDefault = PermissionDefault.NOT_OP;
            case TRUE -> permissionDefault = PermissionDefault.TRUE;
            case FALSE -> permissionDefault = PermissionDefault.FALSE;
            default -> permissionDefault = PermissionDefault.OP;
        }

        if (isPermissionRegistered(parent)) return;

        final PluginManager pluginManager = this.server.getPluginManager();

        final Permission permission = new Permission(
                parent,
                description,
                permissionDefault,
                children
        );

        pluginManager.addPermission(permission);
    }

    @Override
    public void unregisterPermission(@NotNull final String parent) {
        if (!isPermissionRegistered(parent)) return;

        final PluginManager pluginManager = this.server.getPluginManager();

        pluginManager.removePermission(parent);
    }

    @Override
    public boolean isPermissionRegistered(@NotNull final String parent) {
        final PluginManager pluginManager = this.server.getPluginManager();

        return pluginManager.getPermission(parent) != null;
    }

    @Override
    public void broadcast(@NotNull Component component) {
        broadcast(component, "");
    }

    @Override
    public @NotNull FusionPaper getFusion() {
        return this.fusion;
    }

    public @NotNull final ChatManagerPlugin getPlugin() {
        return this.plugin;
    }
}