package com.ryderbelserion.chatmanager.enums.commands;

public enum FilterType {

    WHITELIST("whitelist"),
    BLACKLIST("blacklist");

    private final String type;

    FilterType(final String type) {
        this.type = type;
    }

    public final String getType() {
        return this.type;
    }
}