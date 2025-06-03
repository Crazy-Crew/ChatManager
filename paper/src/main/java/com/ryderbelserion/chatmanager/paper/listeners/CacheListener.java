package com.ryderbelserion.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.api.configs.locale.RootKeys;
import com.ryderbelserion.chatmanager.paper.api.objects.PaperBase;
import com.ryderbelserion.chatmanager.paper.api.objects.PaperUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.HashMap;

public class CacheListener extends PaperBase implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        this.userManager.addUser(player);

        final PaperUser user = this.userManager.getUser(player.getUniqueId());

        event.joinMessage(this.fusion.color(player, user.locale().getProperty(RootKeys.join_message), new HashMap<>() {{
            put("{player}", player.getName());
        }}));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        final PaperUser user = this.userManager.getUser(player.getUniqueId());

        event.quitMessage(this.fusion.color(player, user.locale().getProperty(RootKeys.quit_message), new HashMap<>() {{
            put("{player}", player.getName());
        }}));

        this.userManager.removeUser(player);
    }
}