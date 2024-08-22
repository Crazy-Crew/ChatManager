package com.ryderbelserion.chatmanager.configs.impl.messages.commands;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class SpyKeys implements SettingsHolder {

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "All messages related to spying on people.",
                "Welcome to the CIA, Watson!"
        };

        conf.setComment("commands.spy", header);
    }

    @Comment({
            "A list of available placeholders:",
            "",
            "{state} which returns whatever argument was supplied in /chatmanager spy <state>",
            "",
            "{status} returns enabled or disabled, which can be configured below."
    })
    public static final Property<String> spy_toggle = newProperty("commands.spy.toggle_message", "{prefix}<green>{state} has been {status}");

    public static final Property<String> spy_enabled = newProperty("commands.spy.enabled", "<green>Enabled");

    public static final Property<String> spy_disabled = newProperty("commands.spy.disabled", "<red>Disabled");

    @Comment({
            "Shows in chat, to staff with the proper permission to see direct messages.",
            "This should also account for plugins like CMI/EssentialsX if you use those plugins."
    })
    public static final Property<String> spy_chat_format = newProperty("commands.spy.formats.chat", "<aqua><b>(*)<aqua>Spy <white><b>[<yellow>{player} <light_purple>-> <yellow>{receiver}<white><b>] <aqua>{message}");

    @Comment("Shows in chat, to staff with the proper permission to see executed commands.")
    public static final Property<String> spy_command_format = newProperty("commands.spy.formats.command", "<gray>[Command-Spy] {player}: <aqua>{command}");

}