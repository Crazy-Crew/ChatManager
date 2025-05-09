package com.ryderbelserion.chatmanager.enums.core;

public enum ServerState {

    MUTED("muted", "Marks the server as muted which means no players can speak, unless they have bypass permissions.");

    private final String description;
    private final String name;

    ServerState(final String name, final String description) {
        this.description = description;
        this.name = name;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getName() {
        return this.name;
    }

}