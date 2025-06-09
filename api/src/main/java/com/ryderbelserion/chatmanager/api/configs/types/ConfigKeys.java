package com.ryderbelserion.chatmanager.api.configs.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.api.enums.Action;
import org.jetbrains.annotations.NotNull;
import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigKeys implements SettingsHolder {

    protected ConfigKeys() {}

    @Override
    public void registerComments(@NotNull final CommentsConfiguration configuration) {
        String[] header = {
                "Support: https://discord.gg/badbones-s-live-chat-182615261403283459",
                "Github: https://github.com/Crazy-Crew",
                "",
                "Issues: https://github.com/Crazy-Crew/ChatManager/issues",
                "Features: https://github.com/Crazy-Crew/ChatManager/issues"
        };

        configuration.setComment("root", header);
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
    public static final Property<Action> message_action = newBeanProperty(Action.class, "root.message-action", Action.send_message);

    @Comment("Should the message of the day be sent?")
    public static final Property<Boolean> motd_enabled = newProperty("root.motd.enabled", false);

    @Comment("The time to wait in seconds before sending the message of the day.")
    public static final Property<Integer> motd_delay = newProperty("root.motd.delay", 0);

}