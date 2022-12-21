package io.flydev.chatmanager.processor;

import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Niels Warrens - 21/12/2022
 */
public interface ChatProcessor {

    void process(AsyncPlayerChatEvent event);

}
