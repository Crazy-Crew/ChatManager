package com.ryderbelserion.chatmanager.api.enums.chat;

import org.apache.commons.lang.WordUtils;

public enum ToggleType {

    toggle_private_messages("toggle_private_messages"),
    toggle_mentions("toggle_mentions"),
    toggle_chat("toggle_chat");
    //mute_chat("mute_chat");

    private final String chat;

    ToggleType(final String chat) {
        this.chat = chat;
    }

    public final String getPrettyName() {
        return WordUtils.capitalize(getName().replaceAll("_", " "));
    }

    public final String getName() {
        return this.chat;
    }

    public static ToggleType getToggleType(final String value) {
        ToggleType type = null;

        if (value.isEmpty()) {
            return type;
        }

        for (final ToggleType key : ToggleType.values()) {
            if (key.getName().equals(value)) {
                type = key;

                break;
            }
        }

        return type;
    }
}