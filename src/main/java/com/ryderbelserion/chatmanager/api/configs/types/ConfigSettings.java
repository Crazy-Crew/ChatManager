package com.ryderbelserion.chatmanager.api.configs.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;

import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigSettings implements SettingsHolder {

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "Github: https://github.com/Crazy-Crew",
                "",
                "Issues/Features: https://github.com/Crazy-Crew/ChatManager/issues",
                "Wiki: https://github.com/Crazy-Crew/ChatManager/wiki",
                "Discord Support: https://discord.gg/mh7Ydaf"
        };

        conf.setComment("settings", header);
    }

    @Comment("The server name used in %server_name% variable.")
    public static final Property<String> SERVER_NAME = newProperty("settings.server-name", "Server-Name");

    @Comment("Block players from typing in chat until they move.")
    public static final Property<Boolean> BLOCK_CHAT_UNTIL_MOVED = newProperty("anti-bot.block-chat-until-moved", false);

    @Comment("Block players from executing commands until they move.")
    public static final Property<Boolean> BLOCK_COMMANDS_UNTIL_MOVED = newProperty("anti-bot.block-chat-until-moved", false);

    // Anti Caps.
    @Comment("Whether caps should be prevented in messages or not.")
    public static final Property<Boolean> ANTI_CAPS_ENABLED = newProperty("anti-caps.enable", true);

    @Comment("Should players capitalize words in commands?")
    public static final Property<Boolean> ANTI_CAPS_IN_COMMANDS = newProperty("anti-caps.enable-in-commands", true);

    @Comment("The minimum size the message has to be in order to get blocked.")
    public static final Property<Integer> ANTI_CAPS_MESSAGE_LENGTH = newProperty("anti-caps.min-message-length", 5);

    @Comment("The percentage the message has to be in caps to get blocked.")
    public static final Property<Integer> ANTI_CAPS_PERCENTAGE = newProperty("anti-caps.required-percentage", 70);

}