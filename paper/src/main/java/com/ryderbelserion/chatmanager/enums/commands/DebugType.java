package com.ryderbelserion.chatmanager.enums.commands;

public enum DebugType {

    AUTO_BROADCAST("auto_broadcast"),
    CONFIG("config"),
    ALL("all");

    private final String type;

    DebugType(final String type) {
        this.type = type;
    }

    public final String getType() {
        return this.type;
    }
}