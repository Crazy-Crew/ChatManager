package io.flydev.chatmanager.processors.spam;

import io.flydev.chatmanager.processors.ChatProcessor;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * @author Niels Warrens - 23/12/2022
 */
public class RepeatingCommandProcessor implements ChatProcessor<PlayerCommandPreprocessEvent> {

	private static final String BYPASS_PERMISSION = "chatmanager.bypass.dupe.command";

	@Override
	public void process(PlayerCommandPreprocessEvent event) {
		if (event.getPlayer().hasPermission(BYPASS_PERMISSION)) return;


	}

}
