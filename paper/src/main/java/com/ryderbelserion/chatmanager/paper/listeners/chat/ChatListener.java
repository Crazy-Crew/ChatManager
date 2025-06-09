package com.ryderbelserion.chatmanager.paper.listeners.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent event) {
        //if (!this.chat.getProperty(ChatKeys.chat_format_toggle)) return;

        //event.renderer(new ChatRender(event.getPlayer(), this.chat.getProperty(ChatKeys.chat_format_default), event.signedMessage()));
    }
}