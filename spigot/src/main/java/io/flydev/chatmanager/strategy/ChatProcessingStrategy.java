package io.flydev.chatmanager.strategy;

import io.flydev.chatmanager.processors.ChatProcessor;
import org.bukkit.event.Event;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Niels Warrens - 21/12/2022
 */
@SuppressWarnings("unchecked")
public class ChatProcessingStrategy {

	private final Map<Class<? extends Event>, Collection<ChatProcessor<? extends Event>>> processors;

	public ChatProcessingStrategy(Map<Class<? extends Event>, Collection<ChatProcessor<? extends Event>>> processors) {
		this.processors = processors;
	}

	public <T extends Event> Collection<ChatProcessor<T>> getChatProcessors(T event) {
		Collection<ChatProcessor<? extends Event>> chatProcessors = this.processors.get(event.getClass());
		if (chatProcessors == null) return Collections.emptySet();

		return chatProcessors.stream()
			.map(processor -> (ChatProcessor<T>) processor)
			.collect(Collectors.toCollection(HashSet::new));
	}


}
