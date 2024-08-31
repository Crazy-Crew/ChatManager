package com.ryderbelserion.chatmanager.listeners;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.managers.configs.impl.v2.ConfigKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import com.ryderbelserion.vital.paper.util.scheduler.FoliaRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TrafficListener implements Listener {

    private final ChatManager plugin = ChatManager.get();

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!this.config.getProperty(ConfigKeys.motd_enabled)) return;

        final int delay = this.config.getProperty(ConfigKeys.motd_delay);

        if (delay == -1) {
            // send instantly.
            send(event.getPlayer());

            return;
        }

        new FoliaRunnable(this.plugin.getServer().getGlobalRegionScheduler()) {
            @Override
            public void run() {
                send(event.getPlayer());
            }
        }.runDelayed(this.plugin, 20L * delay);
    }

    public void send(final Player player) {
        this.config.getProperty(ConfigKeys.motd_message).forEach(motd -> MsgUtils.sendMessage(player, motd));
    }
}