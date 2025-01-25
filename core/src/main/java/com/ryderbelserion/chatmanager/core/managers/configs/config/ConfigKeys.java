package com.ryderbelserion.chatmanager.core.managers.configs.config;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.core.enums.other.Action;
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
    public static final Property<Action> message_action = newBeanProperty(Action.class, "root.message-action", Action.send_message);

    @Comment({
            "Should the motd be sent when a player joins?",
            "",
            "If you wish to customize the motd output, you must go to the locale folder",
            "You can customize the motd for each language."
    })
    public static final Property<Boolean> motd_toggle = newProperty("features.motd.toggle", false);

    @Comment("How long should we wait until the motd is displayed after logging in?")
    public static final Property<Integer> motd_delay = newProperty("features.motd.delay", 0);

}