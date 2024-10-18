package com.ryderbelserion.chatmanager.common.managers.configs.config;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.common.enums.MessageState;

import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigKeys implements SettingsHolder {

    protected ConfigKeys() {}

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "Support: https://discord.gg/SekseczrWz",
                "Github: https://github.com/Crazy-Crew",
                "",
                "Issues: https://github.com/Crazy-Crew/ChatManager/issues",
                "Features: https://github.com/Crazy-Crew/ChatManager/issues"
        };

        conf.setComment("root", header);
    }

    @Comment("The prefix that appears in front of commands!")
    public static final Property<String> command_prefix = newProperty("root.prefix", "<blue>[<gold>ChatManager<blue>] <reset>");

    @Comment({
            "This option will tell the plugin to send all messages as action bars or messages in chat.",
            "",
            "send_message -> sends messages in chat.",
            "send_actionbar -> sends messages in actionbar.",
            ""
    })
    public static final Property<MessageState> message_state = newBeanProperty(MessageState.class, "root.message-state", MessageState.send_message);

    @Comment("Should default chat formatting be enabled?")
    public static final Property<Boolean> chat_format_toggle = newProperty("chat.format.toggle", false);

    @Comment({
            "The default format, if no per group formats are found.",
            "",
            "A list of available placeholders: {player}",
            "PlaceholderAPI is also fully supported."
    })
    public static final Property<String> chat_format_default = newProperty("chat.format.default", "%luckperms_prefix% {player} <gold>-> <reset>{message}");

}