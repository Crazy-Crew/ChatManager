package com.ryderbelserion.chatmanager.api.enums.chat;

public enum SpyState {

    social_spy("social_spy"),
    command_spy("command_spy");

    private final String spyState;

    SpyState(final String spyState) {
        this.spyState = spyState;
    }

    public final String getSpyState() {
        return this.spyState;
    }

    public static SpyState getSpyState(final String value) {
        SpyState state = SpyState.social_spy;

        if (value.isEmpty()) {
            return state;
        }

        for (final SpyState key : SpyState.values()) {
            if (key.getSpyState().equals(value)) {
                state = key;

                break;
            }
        }

        return state;
    }
}