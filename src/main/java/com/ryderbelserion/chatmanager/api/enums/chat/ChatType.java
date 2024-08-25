package com.ryderbelserion.chatmanager.api.enums.chat;

import org.apache.commons.lang.WordUtils;

public enum ChatType {

    local_chat("local_chat"),
    world_chat("world_chat"),
    global_chat("global_chat"),
    help("help");

    private final String chat;

    ChatType(final String chat) {
        this.chat = chat;
    }

    public final String getPrettyName() {
        return WordUtils.capitalize(getName().replace("_", " "));
    }

    public final String getName() {
        return this.chat;
    }

    public static ChatType getChatType(final String value) {
        ChatType type = ChatType.global_chat;

        if (value.isEmpty()) {
            return type;
        }

        for (final ChatType key : ChatType.values()) {
            if (key.getName().equals(value)) {
                type = key;

                break;
            }
        }

        return type;
    }
}