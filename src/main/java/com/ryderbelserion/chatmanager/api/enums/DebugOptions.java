package com.ryderbelserion.chatmanager.api.enums;

public enum DebugOptions {
    
    CONFIG("config"),
    MESSAGES("messages"),
    BROADCAST("broadcast"),
    ALL("all");
    
    final String name;
    
    DebugOptions(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}