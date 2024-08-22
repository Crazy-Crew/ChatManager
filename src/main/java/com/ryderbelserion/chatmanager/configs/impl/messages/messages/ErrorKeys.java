package com.ryderbelserion.chatmanager.configs.impl.messages.messages;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ErrorKeys implements SettingsHolder {

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "All messages related to errors."
        };

        conf.setComment("errors", header);
    }

    public static final Property<String> internal_error = newProperty("errors.internal-error", "{prefix}<red>An internal error has occurred. Please check the console for the full error.");

}