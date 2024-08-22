package com.ryderbelserion.chatmanager.api.enums.chat;

import org.apache.commons.lang.WordUtils;

public enum ChatState {

    local_chat("local_chat"),
    world_chat("world_chat"),
    global_chat("global_chat"),
    help("help");

    private final String chat;

    ChatState(final String chat) {
        this.chat = chat;
    }

    public final String getPrettyName() {
        return WordUtils.capitalize(getName().replace("_", " "));
    }

    public final String getName() {
        return this.chat;
    }

    public static ChatState getChatState(final String value) {
        ChatState state = ChatState.global_chat;

        if (value.isEmpty()) {
            return state;
        }

        for (final ChatState key : ChatState.values()) {
            if (key.getName().equals(value)) {
                state = key;

                break;
            }
        }

        return state;
    }
}