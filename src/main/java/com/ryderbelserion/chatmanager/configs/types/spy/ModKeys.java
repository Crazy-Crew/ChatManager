package com.ryderbelserion.chatmanager.configs.types.spy;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ModKeys implements SettingsHolder {

    @Comment("Turn on command spy when players with the correct permission join the server.")
    public static final Property<Boolean> toggle_command_spy = newProperty("Command_Spy.Enable_On_Join", false);

    @Comment("Commands that won't be shown in command spy.")
    public static final Property<List<String>> command_spy_commands = newListProperty("Command_Spy.Blacklist_Commands", List.of(
            "/login",
            "/register"
    ));

    @Comment("Turn on social spy when players with the correct permission join the server.")
    public static final Property<Boolean> toggle_social_spy = newProperty("Social_Spy.Enable_On_Join", false);

}