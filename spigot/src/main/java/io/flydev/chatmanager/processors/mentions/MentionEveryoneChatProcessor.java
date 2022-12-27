package io.flydev.chatmanager.processors.mentions;

import io.flydev.chatmanager.processors.FormattedChatProcessor;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Niels Warrens - 21/12/2022
 */
public class MentionEveryoneChatProcessor extends FormattedChatProcessor<AsyncPlayerChatEvent> {

	private static final String MENTION_EVERYONE_PERMISSION = "chatmanager.mention.everyone";

	@Override
	public void process(AsyncPlayerChatEvent event) {
		// Check if the player has the permissions to mention players
		if (!event.getPlayer().hasPermission(MENTION_EVERYONE_PERMISSION)) return;


	}

}
