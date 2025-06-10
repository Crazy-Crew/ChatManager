package com.ryderbelserion.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.common.constants.MessageKeys;
import com.ryderbelserion.chatmanager.common.enums.Files;
import com.ryderbelserion.chatmanager.common.registry.MessageRegistry;
import com.ryderbelserion.chatmanager.common.registry.UserRegistry;
import com.ryderbelserion.chatmanager.paper.ChatManagerPlatform;
import com.ryderbelserion.chatmanager.paper.ChatManagerPlugin;
import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.util.HashMap;

public class CacheListener implements Listener {

    private final MessageRegistry messageRegistry;
    private final UserRegistry userRegistry;
    private final ChatManagerPlugin plugin;

    public CacheListener(@NotNull final ChatManagerPlatform platform) {
        this.messageRegistry = platform.getMessageRegistry();
        this.userRegistry = platform.getUserRegistry();
        this.plugin = platform.getPlugin();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        this.userRegistry.addUser(player);

        final String name = player.getName();

        event.joinMessage(this.messageRegistry.getMessage(MessageKeys.join_message).getComponent(player, new HashMap<>() {{
            put("{player}", name);
        }}));

        final CommentedConfigurationNode config = Files.config.getConfig();

        if (config.node("root", "motd", "toggle").getBoolean(false)) {
            final int delay = config.node("root", "motd", "delay").getInt(0);

            new FoliaScheduler(this.plugin, Scheduler.global_scheduler) {
                @Override
                public void run() {
                    messageRegistry.getMessage(MessageKeys.message_of_the_day).send(player, new HashMap<>() {{
                        put("{player}", name);
                    }});
                }
            }.runDelayed(delay);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        event.quitMessage(this.messageRegistry.getMessage(MessageKeys.quit_message).getComponent(player, new HashMap<>() {{
            put("{player}", player.getName());
        }}));

        this.userRegistry.removeUser(player);
    }
}