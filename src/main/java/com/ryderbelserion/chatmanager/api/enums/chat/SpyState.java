package com.ryderbelserion.chatmanager.api.enums.chat;

import org.apache.commons.lang.WordUtils;

public enum SpyState {

    social_spy("social_spy"),
    command_spy("command_spy");

    private final String spyState;

    SpyState(final String spyState) {
        this.spyState = spyState;
    }

    public final String getPrettyName() {
        return WordUtils.capitalize(getName().replace("_", " "));
    }

    public final String getName() {
        return this.spyState;
    }

    public static SpyState getSpyState(final String value) {
        SpyState state = SpyState.social_spy;

        if (value.isEmpty()) {
            return state;
        }

        for (final SpyState key : SpyState.values()) {
            if (key.getName().equals(value)) {
                state = key;

                break;
            }
        }

        return state;
    }
}