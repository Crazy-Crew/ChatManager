package com.ryderbelserion.chatmanager.common.enums;

public enum MessageState {

    send_message("send_message"),
    send_actionbar("send_actionbar");

    private final String name;

    MessageState(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}