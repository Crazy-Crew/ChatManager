package io.flydev.chatmanager.strategy;

import io.flydev.chatmanager.processors.ChatProcessor;
import org.bukkit.event.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Niels Warrens - 21/12/2022
 */
@SuppressWarnings({"UnusedReturnValue", "unchecked"})
public class ChatProcessingStrategyBuilder {

	private final Collection<ChatProcessor<? extends Event>> chatProcessors = new HashSet<>();

	public ChatProcessingStrategyBuilder addChatProcessor(ChatProcessor<? extends Event> chatProcessor) {
		this.chatProcessors.add(chatProcessor);
		return this;
	}

	public ChatProcessingStrategy build() {
		HashMap<Class<? extends Event>, Collection<ChatProcessor<? extends Event>>> processors = new HashMap<>();
		this.chatProcessors.forEach(chatProcessor -> {
			Class<? extends Event> eventClass = this.getEventClass(chatProcessor);
			Collection<ChatProcessor<? extends Event>> chatProcessors = processors.getOrDefault(eventClass, new HashSet<>());
			chatProcessors.add(chatProcessor);
			processors.put(eventClass, chatProcessors);
		});

		return new ChatProcessingStrategy(processors);
	}

	private Class<? extends Event> getEventClass(ChatProcessor<? extends Event> chatProcessor) {
		try {
			// Get the generic type of the chat processor
			return (Class<? extends Event>)
				Class.forName(chatProcessor.getClass().getGenericInterfaces()[0].getTypeName().split("<")[1].split(">")[0]);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
