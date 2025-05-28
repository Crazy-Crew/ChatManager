package com.ryderbelserion.chatmanager.api;

import com.ryderbelserion.chatmanager.api.interfaces.IChatManager;
import org.jetbrains.annotations.NotNull;

public class ChatManagerProvider {

    private ChatManagerProvider() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static IChatManager instance;

    public static IChatManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ChatManager API has not been initialized yet");
        }

        return instance;
    }

    public static void register(@NotNull final IChatManager instance) {
        ChatManagerProvider.instance = instance;
    }

    public static void unregister() {
        ChatManagerProvider.instance = null;
    }
}