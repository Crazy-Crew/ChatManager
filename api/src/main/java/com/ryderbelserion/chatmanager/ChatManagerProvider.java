package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.api.ChatManager;

public final class ChatManagerProvider {

    private static ChatManager chatManager = null;

    public static void register(final ChatManager chatManager) {
        ChatManagerProvider.chatManager = chatManager;
    }

    public static ChatManager get() {
        return chatManager;
    }
}