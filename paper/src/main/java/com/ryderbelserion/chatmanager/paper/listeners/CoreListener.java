package com.ryderbelserion.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.api.objects.ChatManagerBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CoreListener extends ChatManagerBase implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.userManager.addUser(event.getPlayer());
    }
}