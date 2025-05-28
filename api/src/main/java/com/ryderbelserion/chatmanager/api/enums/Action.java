package com.ryderbelserion.chatmanager.api.enums;

public enum Action {

    send_message("send_message"),
    send_actionbar("send_actionbar");

    private final String name;

    Action(final String name) {
        this.name = name;
    }

    public static Action getAction(final String name) {
        Action action = Action.send_message;

        for (final Action key : Action.values()) {
            if (key.name.equalsIgnoreCase(name)) {
                action = key;

                break;
            }
        }

        return action;
    }

    public String getName() {
        return this.name;
    }
}