package me.corecraft.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.common.constants.Messages;
import com.ryderbelserion.chatmanager.common.enums.Files;
import com.ryderbelserion.chatmanager.common.registry.MessageRegistry;
import com.ryderbelserion.chatmanager.common.registry.UserRegistry;
import com.ryderbelserion.fusion.paper.scheduler.FoliaScheduler;
import com.ryderbelserion.fusion.paper.scheduler.Scheduler;
import me.corecraft.chatmanager.paper.ChatManagerPlatform;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.util.Map;

public class CacheListener implements Listener {

    private final MessageRegistry messageRegistry;
    private final UserRegistry userRegistry;
    private final ChatManager plugin;

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

        event.joinMessage(this.messageRegistry.getMessage(Messages.join_message).getComponent(player, Map.of(
                "{player}", player.getName()
        )));

        final CommentedConfigurationNode config = Files.config.getYamlConfig();

        if (config.node("root", "motd", "toggle").getBoolean(false)) {
            final int delay = config.node("root", "motd", "delay").getInt(0);

            if (delay != -1) {
                new FoliaScheduler(this.plugin, Scheduler.global_scheduler) {
                    @Override
                    public void run() {
                        messageRegistry.getMessage(Messages.message_of_the_day).send(player, Map.of(
                                "{player}", name
                        ));
                    }
                }.runDelayed(delay);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        event.quitMessage(this.messageRegistry.getMessage(Messages.quit_message).getComponent(player, Map.of(
                "{player}", player.getName()
        )));

        this.userRegistry.removeUser(player);
    }
}