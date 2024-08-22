package com.ryderbelserion.chatmanager.configs.impl.messages.messages;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class MiscKeys implements SettingsHolder {

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "Support: https://discord.gg/SekseczrWz",
                "Github: https://github.com/Crazy-Crew",
                "",
                "Issues: https://github.com/Crazy-Crew/ChatManager/issues",
                "Features: https://github.com/Crazy-Crew/ChatManager/issues",
                "",
                "All messages allow the use of {prefix} unless stated otherwise.",
                ""
        };

        conf.setComment("misc", header);
    }

    @Comment("A list of available placeholders: {command}")
    public static final Property<String> unknown_command = newProperty("misc.unknown-command", "{prefix}<red>{command} is not a known command.");

    @Comment("A list of available placeholders: {usage}")
    public static final Property<String> correct_usage = newProperty("misc.correct-usage", "{prefix}<red>The correct usage for this command is <yellow>{usage}");

    public static final Property<String> feature_disabled = newProperty("misc.feature-disabled", "{prefix}<red>This feature is disabled.");

}