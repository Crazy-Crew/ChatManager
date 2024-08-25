package com.ryderbelserion.chatmanager.api.enums.chat;

import org.apache.commons.lang.WordUtils;

public enum FilterType {

    banned_commands("banned_commands"),
    allowed_words("allowed_words"),
    banned_words("banned_words");

    private final String type;

    FilterType(final String type) {
        this.type = type;
    }

    public final String getPrettyName() {
        return WordUtils.capitalize(getName().replace("_", " "));
    }

    public final String getName() {
        return this.type;
    }

    public static FilterType getType(final String value) {
        FilterType type = FilterType.banned_commands;

        if (value.isEmpty()) {
            return type;
        }

        for (final FilterType key : FilterType.values()) {
            if (key.getName().equals(value)) {
                type = key;

                break;
            }
        }

        return type;
    }
}