package com.ryderbelserion.chatmanager.configs.impl.messages.commands;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ToggleKeys implements SettingsHolder {

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "All messages related to chatmanager toggle.",
        };

        conf.setComment("commands.toggle", header);
    }

    @Comment({
            "A list of available placeholders:",
            "",
            "{state} which returns whatever argument was supplied in /chatmanager toggle <state>",
            "",
            "{status} returns enabled or disabled, which can be configured below."
    })
    public static final Property<String> chat_toggle = newProperty("commands.toggle.toggle_message", "{prefix}<green>{state} has been {status}");

    public static final Property<String> toggle_enabled = newProperty("commands.toggle.enabled", "<green>enabled");

    public static final Property<String> toggle_disabled = newProperty("commands.toggle.disabled", "<red>disabled");
}