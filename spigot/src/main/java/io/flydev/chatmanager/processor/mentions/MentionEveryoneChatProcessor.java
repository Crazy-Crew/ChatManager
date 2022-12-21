package io.flydev.chatmanager.processor.mentions;

import io.flydev.chatmanager.processor.ChatProcessor;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Niels Warrens - 21/12/2022
 */
public class MentionEveryoneChatProcessor implements ChatProcessor {

    @Override
    public void process(AsyncPlayerChatEvent event) {
        // Check if the player has the permissions to mention players
        if (!event.getPlayer().hasPermission("chatmanager.mention.everyone")) return;


    }

}
