package com.ryderbelserion.chatmanager.api.enums.chat;

public enum ChatState {

    local_chat("local_chat"),
    global_chat("global_chat");

    private final String chat;

    ChatState(final String chat) {
        this.chat = chat;
    }

    public final String getChat() {
        return this.chat;
    }
}