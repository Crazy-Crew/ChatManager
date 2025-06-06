package com.ryderbelserion.chatmanager.api.configs.types.locale;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ErrorKeys implements SettingsHolder {

    public static final Property<String> internal_error = newProperty("messages.errors.internal-error", "{prefix}<red>An internal error has occurred. Please check the console for the full error.");

    public static final Property<String> message_empty = newProperty("messages.errors.message-empty", "{prefix}<red>The message cannot be empty.");

}