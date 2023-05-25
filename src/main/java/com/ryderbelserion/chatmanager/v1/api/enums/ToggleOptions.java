package com.ryderbelserion.chatmanager.v1.api.enums;

public enum ToggleOptions {

    MENTIONS("mentions"),
    CHAT("chat"),
    PM("pm");

    private final String name;

    ToggleOptions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}