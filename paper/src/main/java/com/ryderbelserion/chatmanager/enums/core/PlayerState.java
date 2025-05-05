package com.ryderbelserion.chatmanager.enums.core;

public enum PlayerState {

    DIRECT_MESSAGES("direct_messages", "Controls whether a player can send and receive private messages."),
    COMMAND_SPY("command_spy", "Allows a player to see commands used by others."),
    SOCIAL_SPY("social_spy", "Lets a player see private messages between other players."),
    STAFF_CHAT("staff_chat", "Grants access to the staff-only chat."),
    FLAGGED("flagged", "Marks a player when they're flagged by the antibot system."),
    CHAT("chat", "Enables or disables a player's ability to send messages in chat.");

    private final String description;
    private final String name;

    PlayerState(final String name, final String description) {
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