package com.ryderbelserion.chatmanager.configs.impl.messages.commands;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ChatKeys implements SettingsHolder {

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "All messages related to chatradius.",
        };

        conf.setComment("commands.chatradius", header);
    }

    @Comment({
            "A list of available placeholders:",
            "",
            "{state} which returns whatever argument was supplied in /chatmanager chatradius <state>",
            "",
            "{status} returns enabled or disabled, which can be configured below."
    })
    public static final Property<String> chatradius_toggle = newProperty("commands.chatradius.toggle_message", "{prefix}<green>{state} has been {status}");

    @Comment({
            "A list of available placeholders:",
            "",
            "{state} which returns whatever argument was supplied in /chatmanager chatradius <state>",
            "",
            "{status} returns enabled or disabled, which can be configured below."
    })
    public static final Property<String> chatradius_already_enabled = newProperty("commands.chatradius.already_enabled", "{prefix}<green>{state} is already {status}");

    public static final Property<String> chatradius_enabled = newProperty("commands.chatradius.enabled", "<green>enabled");

    public static final Property<String> chatradius_disabled = newProperty("commands.chatradius.disabled", "<red>disabled");

    public static final Property<List<String>> chatradius_help = newListProperty("commands.chatradius.help", List.of(
            "<dark_gray>===================================================</dark_gray>",
            " <aqua>Chat Radius Help Menu",
            "",
            " <white>/chatradius help <yellow>- Shows a list of commands for chat radius.",
            " <white>/chatradius local <yellow>- Enables local chat.",
            " <white>/chatradius global <yellow>- Enables global chat.",
            " <white>/chatradius world <yellow>- Enables world chat.",
            "<dark_gray>===================================================</dark_gray>"
    ));

    @Comment("A list of available placeholders: {value}, {type}")
    public static final Property<String> filter_value_added = newProperty("commands.filter.add", "{prefix}<gray>You added the word <red>{value} <gray>to the list of filter {type}.");

    @Comment("A list of available placeholders: {value}, {type}")
    public static final Property<String> filter_value_remove = newProperty("commands.filter.remove", "{prefix}<gray>You removed the word <red>{value} <gray>from the list of filter {type}.");

    @Comment("A list of available placeholders: {value}, {type}")
    public static final Property<String> filter_value_exists = newProperty("commands.filter.command.exists", "{prefix}<gray>The word <red>{value} <gray>is already added to the list of filter {type}.");

    @Comment("A list of available placeholders: {value}, {type}")
    public static final Property<String> filter_value_not_found = newProperty("commands.filter.not-found", "{prefix}<gray>The word <red>{value} <gray>is not in the list of filter {type}.");

    @Comment("A list of available placeholders: {command}")
    public static final Property<String> filter_command_notify_staff = newProperty("commands.filter.command-notify-staff", "<gray>[Blocked-Cmd] <white>{player}: <gray>{command}");

    @Comment("A list of available placeholders: {word}")
    public static final Property<String> filter_word_notify_staff = newProperty("commands.filter.word-notify-staff", "<gray>[Blocked-Word] <white>{player}: <gray>{word}");

    public static final Property<List<String>> filter_command_help = newListProperty("commands.filter.help", List.of(
            "<dark_gray>===================================================</dark_gray>",
            " <aqua>Filter Help Menu",
            "",
            " <white>/chatmanager filter help <yellow>- Shows help menu for /chatmanager filter.",
            " <white>/chatmanager filter add <gold>[command/word] [value] <yellow>- Adds a command/word to the filter.",
            " <white>/chatmanager filter remove <gold>[command/word] [value] <yellow>- Removes a command/word from the filter.",
            "",
            "<dark_gray>===================================================</dark_gray>"
    ));
}