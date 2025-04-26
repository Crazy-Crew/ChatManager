package com.ryderbelserion.chatmanager.enums.commands;

public enum SpyType {

    COMMAND_SPY("command_spy"),
    SOCIAL_SPY("social_spy");

    private final String type;

    SpyType(final String type) {
        this.type = type;
    }

    public final String getType() {
        return this.type;
    }
}