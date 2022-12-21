package io.flydev.chatmanager.strategy;

import io.flydev.chatmanager.processor.ChatProcessor;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Niels Warrens - 21/12/2022
 */
public class ChatProcessingStrategy {

    private final Collection<ChatProcessor> chatProcessors;

    public ChatProcessingStrategy(Collection<ChatProcessor> chatProcessors) {
        this.chatProcessors = Collections.unmodifiableCollection(chatProcessors);
    }

    public Collection<ChatProcessor> getChatProcessors() {
        return chatProcessors;
    }

}
