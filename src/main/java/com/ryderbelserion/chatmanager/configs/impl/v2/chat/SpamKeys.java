package com.ryderbelserion.chatmanager.configs.impl.v2.chat;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.ListProperty;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class SpamKeys implements SettingsHolder {

    @Comment("Stops players from talking in chat, until they have walked a block.")
    public static final Property<Boolean> block_chat_until_moved = newProperty("feature.bot-protection.block_chat_until_moved", false);

    @Comment("Stops players from running commands, until they have walked a block.")
    public static final Property<Boolean> block_commands_until_moved = newProperty("feature.bot-protection.block_commands_until_moved", false);

    @Comment("This will prevent players from using excessive caps, if set to true.")
    public static final Property<Boolean> block_caps_enabled = newProperty("feature.bot-protection.caps.enabled", false);

    @Comment("This will prevent players from using excessive caps in commands, if set to true.")
    public static final Property<Boolean> block_caps_in_commands = newProperty("feature.bot-protection.caps.in_commands", false);

    @Comment("The minimum size a message has to be, in order for the detection to prevent caps.")
    public static final Property<Integer> block_caps_message_length = newProperty("feature.bot-protection.caps.minimum_length", 5);

    @Comment("The percentage a message has to be capitalized, in order to get blocked.")
    public static final Property<Integer> block_caps_percentage = newProperty("feature.bot-protection.caps.percentage", 50);

    @Comment("How many seconds does the player have to wait till they send their next message? Set to -1 to disable.")
    public static final Property<Integer> block_chat_spam_delay = newProperty("feature.bot-protection.chat.spam.delay", -1);

    @Comment("Prevent players from repeating the same messages.")
    public static final Property<Boolean> block_chat_spam_repeating_messages = newProperty("feature.bot-protection.chat.spam.repeating_messages", false);

    @Comment("How many seconds does the player have to wait till they send their next command? Set to 0 to disable.")
    public static final Property<Integer> block_chat_spam_command_delay = newProperty("feature.bot-protection.command.spam.delay", 3);

    @Comment("Prevent players from repeating the same commands.")
    public static final Property<Boolean> block_chat_spam_repeating_commands = newProperty("feature.bot-protection.command.spam.repeating_commands", false);

    @Comment("Whitelisted commands that won't be affected by the anti-spam.")
    public static final ListProperty<String> block_chat_spam_whitelist_commands = newListProperty("feature.bot-protection.command.spam.whitelist", List.of("/spawn"));

}