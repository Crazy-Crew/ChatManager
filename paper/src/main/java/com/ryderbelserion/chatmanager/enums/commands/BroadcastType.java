package com.ryderbelserion.chatmanager.enums.commands;

public enum BroadcastType {

    BROADCAST("broadcast"),
    ANNOUNCEMENT("announcement"),
    WARNING("warning");

    private final String type;

    BroadcastType(final String type) {
        this.type = type;
    }

    public final String getType() {
        return this.type;
    }
}