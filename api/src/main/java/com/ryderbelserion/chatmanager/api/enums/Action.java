package com.ryderbelserion.chatmanager.api.enums;

import org.jetbrains.annotations.NotNull;

public enum Action {

    send_message("send_message"),
    send_actionbar("send_actionbar");

    private final String name;

    Action(@NotNull final String name) {
        this.name = name;
    }

    public static @NotNull Action getAction(@NotNull final String name) {
        Action action = Action.send_message;

        for (final Action key : Action.values()) {
            if (key.name.equalsIgnoreCase(name)) {
                action = key;

                break;
            }
        }

        return action;
    }

    public @NotNull final String getName() {
        return this.name;
    }
}