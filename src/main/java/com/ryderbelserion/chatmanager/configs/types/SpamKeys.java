package com.ryderbelserion.chatmanager.configs.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class SpamKeys implements SettingsHolder {

    @Comment("Block players from executing commands until they move.")
    public static final Property<Boolean> block_commands_until_moved = newProperty("anti-bot.block_commands_until_moved", false);

    @Comment("Block players from typing in chat until they move.")
    public static final Property<Boolean> block_chat_until_moved = newProperty("anti-bot.block_chat_until_moved", false);

    @Comment("Prevent players from repeating the same messages.")
    public static final Property<Boolean> block_repeated_messages = newProperty("anti-spam.chat.block_repeated_messages", false);

    @Comment({
            "How many seconds does the player have to wait till they send their next message?",
            "Set to -1, which will disable it."
    })
    public static final Property<Integer> chat_delay = newProperty("anti-spam.chat.delay", -1);

    @Comment("Prevent players from repeating the same commands.")
    public static final Property<Boolean> block_repeated_commands = newProperty("anti-spam.command.block_repeated_messages", false);

    public static final Property<List<String>> whitelisted_commands = newListProperty("anti-spam.command.whitelisted_commands", List.of(
            "spawn"
    ));

    @Comment({
            "How many seconds does the player have to wait till they send their next command?",
            "Set to -1, which will disable it."
    })
    public static final Property<Integer> command_delay = newProperty("anti-spam.command.delay", -1);

}