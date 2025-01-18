package com.ryderbelserion.chatmanager.paper.listeners.chat;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.core.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.core.managers.configs.config.chat.ChatKeys;
import com.ryderbelserion.chatmanager.paper.api.renderers.ChatRender;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    private final SettingsManager chat = ConfigManager.getChat();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncChatEvent event) {
        if (!this.chat.getProperty(ChatKeys.chat_format_toggle)) return;

        event.renderer(new ChatRender(event.getPlayer(), this.chat.getProperty(ChatKeys.chat_format_default), event.signedMessage()));
    }
}