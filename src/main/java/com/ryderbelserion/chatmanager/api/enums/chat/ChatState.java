package com.ryderbelserion.chatmanager.api.enums.chat;

public enum ChatState {

    local_chat("local_chat"),
    world_chat("world_chat"),
    global_chat("global_chat");

    private final String chat;

    ChatState(final String chat) {
        this.chat = chat;
    }

    public final String getChatState() {
        return this.chat;
    }

    public static ChatState getChatState(final String value) {
        ChatState state = ChatState.global_chat;

        if (value.isEmpty()) {
            return state;
        }

        for (final ChatState key : ChatState.values()) {
            if (key.getChatState().equals(value)) {
                state = key;

                break;
            }
        }

        return state;
    }
}