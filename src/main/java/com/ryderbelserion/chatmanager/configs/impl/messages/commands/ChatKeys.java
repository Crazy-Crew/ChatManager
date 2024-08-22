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
            "<dark_gray>=========================================================================</dark_gray>",
            " &3Chat Radius Help Menu",
            "",
            " &f/chatradius help &e- Shows a list of commands for chat radius.",
            " &f/chatradius local &e- Enables local chat.",
            " &f/chatradius global &e- Enables global chat.",
            " &f/chatradius world &e- Enables world chat.",
            "<dark_gray>=========================================================================</dark_gray>"
    ));
}