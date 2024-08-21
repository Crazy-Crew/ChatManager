package com.ryderbelserion.chatmanager.cache;

import com.ryderbelserion.chatmanager.ChatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CacheListener implements Listener {

    private final ChatManager plugin = ChatManager.get();

    private final UserManager userManager = this.plugin.getUserManager();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerLoginEvent event) {
        this.userManager.addUser(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.userManager.removeUser(event.getPlayer());
    }
}