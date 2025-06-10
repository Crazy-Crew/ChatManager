package com.ryderbelserion.chatmanager.paper;

import com.ryderbelserion.chatmanager.common.ChatManager;
import com.ryderbelserion.chatmanager.paper.commands.brigadier.BaseCommand;
import com.ryderbelserion.chatmanager.paper.listeners.CacheListener;
import com.ryderbelserion.chatmanager.paper.listeners.chat.ChatListener;
import com.ryderbelserion.fusion.paper.FusionPaper;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ChatManagerPlatform extends ChatManager {

    private final ChatManagerPlugin plugin;
    private final FusionPaper fusion;
    private final Server server;

    public ChatManagerPlatform(@NotNull final ChatManagerPlugin plugin, @NotNull final FusionPaper fusion) {
        super(fusion.getPath(), fusion.getFileManager());

        this.fusion = fusion;

        this.plugin = plugin;

        fusion.enable(this.plugin);

        this.server = plugin.getServer();
    }

    @Override
    public void start() {
        super.start();

        final PluginManager pluginManager = this.server.getPluginManager();

        List.of(
                new CacheListener(this),
                new ChatListener(this)
        ).forEach(listener -> pluginManager.registerEvents(listener, this.plugin));

        this.fusion.getCommandManager().enable(new BaseCommand(this), "Chat me up man!", List.of());
    }

    @Override
    public void reload() {
        this.fusion.reload(false);

        super.reload();
    }

    @Override
    public void stop() {
        super.stop();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);
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
    public void broadcast(@NotNull Component component) {
        broadcast(component, "");
    }

    public @NotNull final ChatManagerPlugin getPlugin() {
        return this.plugin;
    }

    public @NotNull final FusionPaper getFusion() {
        return this.fusion;
    }
}