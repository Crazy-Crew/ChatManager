package io.flydev.chatmanager.processors;

import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 * @author Niels Warrens - 27/12/2022
 */
public abstract class FormattedChatProcessor<T extends Event> implements ChatProcessor<T> {

	public String format(Player player, String message) {
		return Methods.color(player, message);
	}

}
