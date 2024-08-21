package com.ryderbelserion.chatmanager.configs.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class SpamKeys implements SettingsHolder {

    @Comment("Block players from executing commands until they move.")
    public static final Property<Boolean> block_commands_until_moved = newProperty("anti-bot.block_commands_until_moved", false);

    @Comment("Block players from typing in chat until they move.")
    public static final Property<Boolean> block_chat_until_moved = newProperty("anti-bot.block_chat_until_moved", false);

}