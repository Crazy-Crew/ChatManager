package com.ryderbelserion.chatmanager.api.enums.chat;

import org.apache.commons.lang.WordUtils;

public enum ToggleState {

    toggle_private_messages("toggle_private_messages"),
    toggle_mentions("toggle_mentions"),
    toggle_chat("toggle_chat");
    //mute_chat("mute_chat");

    private final String chat;

    ToggleState(final String chat) {
        this.chat = chat;
    }

    public final String getPrettyName() {
        return WordUtils.capitalize(getName().replaceAll("_", " "));
    }

    public final String getName() {
        return this.chat;
    }

    public static ToggleState getToggleState(final String value) {
        ToggleState state = null;

        if (value.isEmpty()) {
            return state;
        }

        for (final ToggleState key : ToggleState.values()) {
            if (key.getName().equals(value)) {
                state = key;

                break;
            }
        }

        return state;
    }
}