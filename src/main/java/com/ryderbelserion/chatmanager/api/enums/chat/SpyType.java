package com.ryderbelserion.chatmanager.api.enums.chat;

import org.apache.commons.lang.WordUtils;

public enum SpyType {

    social_spy("social_spy"),
    command_spy("command_spy");

    private final String spyState;

    SpyType(final String spyState) {
        this.spyState = spyState;
    }

    public final String getPrettyName() {
        return WordUtils.capitalize(getName().replace("_", " "));
    }

    public final String getName() {
        return this.spyState;
    }

    public static SpyType getSpyType(final String value) {
        SpyType type = SpyType.social_spy;

        if (value.isEmpty()) {
            return type;
        }

        for (final SpyType key : SpyType.values()) {
            if (key.getName().equals(value)) {
                type = key;

                break;
            }
        }

        return type;
    }
}