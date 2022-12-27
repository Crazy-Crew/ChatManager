package io.flydev.chatmanager.processors;

import org.bukkit.event.Event;

/**
 * @author Niels Warrens - 21/12/2022
 */
public interface ChatProcessor<T extends Event> {

	void process(T event);

}
