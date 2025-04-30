package com.ryderbelserion.chatmanager.listeners;

import com.ryderbelserion.chatmanager.managers.UserManager;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TrafficListener implements Listener {

    private final ChatManager plugin = ChatManager.get();

    private final UserManager userManager = this.plugin.getUserManager();

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.userManager.addUser(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        this.userManager.removeUser(event.getPlayer());
    }
}