package com.ryderbelserion.chatmanager.enums.commands;

public enum ListType {

    PLAYER_LIST("command_spy"),
    STAFF_LIST("social_spy");

    private final String type;

    ListType(final String type) {
        this.type = type;
    }

    public final String getType() {
        return this.type;
    }
}