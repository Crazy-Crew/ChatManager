package io.flydev.chatmanager.handlers;

import io.flydev.chatmanager.strategy.ChatProcessingStrategy;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Niels Warrens - 21/12/2022
 */
public class ChatProcessorHandler implements Listener {

	private final ChatProcessingStrategy chatProcessingStrategy;

	public ChatProcessorHandler(ChatProcessingStrategy chatProcessingStrategy) {
		this.chatProcessingStrategy = chatProcessingStrategy;
	}

	@EventHandler
	public void onPlayerAsynchronousChat(AsyncPlayerChatEvent event) {
		this.processAllProcessors(event);
	}

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		this.processAllProcessors(event);
	}


	private <T extends Event> void processAllProcessors(T event) {
		// Process the chat event using the pre-defined strategy
		Set<ForkJoinTask<?>> tasks = new HashSet<>();

		// Process the chat event using the pre-defined strategy
		this.chatProcessingStrategy.getChatProcessors(event)
			.parallelStream()
			.forEach(chatProcessor -> tasks.add(ForkJoinTask.adapt(() -> chatProcessor.process(event)).fork()));

		// Wait for all tasks to complete
		tasks.forEach(ForkJoinTask::join);
	}


}
