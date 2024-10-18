package com.ryderbelserion.chatmanager.common.config.impl;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigKeys implements SettingsHolder {

    protected ConfigKeys() {}

    @Comment("Should default chat formatting be enabled?")
    public static final Property<Boolean> chat_format_toggle = newProperty("chat.format.toggle", false);

    @Comment({
            "The default format, if no per group formats are found.",
            "",
            "A list of available placeholders: {player}",
            "PlaceholderAPI is also fully supported."
    })
    public static final Property<String> chat_format_default = newProperty("chat.format.default", "%luckperms_prefix% {player} <gold>-> <reset>{message}");

}