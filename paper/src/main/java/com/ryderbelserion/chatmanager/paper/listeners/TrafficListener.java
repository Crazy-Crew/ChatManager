package com.ryderbelserion.chatmanager.paper.listeners;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.core.enums.Messages;
import com.ryderbelserion.chatmanager.core.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.core.managers.configs.config.ConfigKeys;
import com.ryderbelserion.chatmanager.paper.api.objects.ChatManagerBase;
import com.ryderbelserion.core.util.StringUtils;
import com.ryderbelserion.paper.enums.Scheduler;
import com.ryderbelserion.paper.util.scheduler.FoliaScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TrafficListener extends ChatManagerBase implements Listener {

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        if (!this.config.getProperty(ConfigKeys.motd_toggle)) return;

        final int delay = this.config.getProperty(ConfigKeys.motd_delay);

        final Player player = event.getPlayer();

        new FoliaScheduler(Scheduler.global_scheduler) {
            @Override
            public void run() {
                Messages.motd.sendMessage(player, "{player}", StringUtils.fromComponent(player.displayName()));
            }
        }.runDelayed(delay);
    }
}