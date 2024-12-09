package com.ryderbelserion.chatmanager.listeners.chat;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManagerPaper;
import com.ryderbelserion.chatmanager.api.renderers.ChatRender;
import com.ryderbelserion.chatmanager.common.managers.configs.config.chat.ChatKeys;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    private final SettingsManager chat;

    public ChatListener(final ChatManagerPaper instance) {
        this.chat = instance.getChat();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncChatEvent event) {
        if (!this.chat.getProperty(ChatKeys.chat_format_toggle)) return;

        event.renderer(new ChatRender(event.getPlayer(), this.chat.getProperty(ChatKeys.chat_format_default), event.signedMessage()));
    }
}