package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.api.ChatManager;
import com.ryderbelserion.chatmanager.api.exceptions.UnavailableException;
import org.jetbrains.annotations.NotNull;

public final class ChatManagerProvider {

    private static ChatManager chatManager = null;

    public static @NotNull ChatManager get() {
        ChatManager instance = ChatManagerProvider.chatManager;

        if (instance == null) {
            throw new UnavailableException();
        }

        return instance;
    }

    public static void register(final ChatManager chatManager) {
        ChatManagerProvider.chatManager = chatManager;
    }

    public static void unregister() {
        ChatManagerProvider.chatManager = null;
    }
}