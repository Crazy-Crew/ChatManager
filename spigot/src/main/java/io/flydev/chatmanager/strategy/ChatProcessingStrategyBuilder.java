package io.flydev.chatmanager.strategy;

import io.flydev.chatmanager.processor.ChatProcessor;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Niels Warrens - 21/12/2022
 */
@SuppressWarnings("UnusedReturnValue")
public class ChatProcessingStrategyBuilder {

    private final Collection<ChatProcessor> chatProcessors = new HashSet<>();

    public ChatProcessingStrategyBuilder addChatProcessor(ChatProcessor chatProcessor) {
        this.chatProcessors.add(chatProcessor);
        return this;
    }

    public ChatProcessingStrategy build() {
        return new ChatProcessingStrategy(this.chatProcessors);
    }

}
