package com.ryderbelserion.chatmanager.listeners.chat;

import com.ryderbelserion.chatmanager.api.renderers.ChatRender;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    private final ChatRender chatRender;

    public ChatListener() {
        this.chatRender = new ChatRender();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncChatEvent event) {
        event.renderer(this.chatRender);
    }
}