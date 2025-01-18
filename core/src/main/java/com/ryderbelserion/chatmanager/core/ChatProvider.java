package com.ryderbelserion.chatmanager.core;

import com.ryderbelserion.chatmanager.core.api.IChatManager;
import org.jetbrains.annotations.NotNull;

public class ChatProvider {

    private ChatProvider() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static IChatManager chatManager;

    public static IChatManager getChatManager() {
        if (chatManager == null) {
            throw new IllegalStateException("ChatManager API has not been initialized");
        }

        return chatManager;
    }

    public static void register(@NotNull final IChatManager chatManager) {
        ChatProvider.chatManager = chatManager;
    }

    public static void unregister() {
        ChatProvider.chatManager = null;
    }
}