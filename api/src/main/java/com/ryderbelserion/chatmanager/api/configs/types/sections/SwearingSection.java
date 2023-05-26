package com.ryderbelserion.chatmanager.api.configs.types.sections;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;

import java.util.List;

import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class SwearingSection implements SettingsHolder {

    private SwearingSection() {}

    private static final String path = "anti-swear.";

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "This section is related to swearing in chat/commands and signs.",
        };

        conf.setComment("anti-swear", header);
    }

    // Chat.
    @Comment("Whether to block swears in chat or not.")
    public static final Property<Boolean> BLOCK_SWEARS_CHAT = newProperty(path + "chat.enable", false);

    @Comment("Should the anti swear checks be more sensitives? Warning: It will cause false positives.")
    public static final Property<Boolean> INCREASE_SENSITIVITY_SWEARS = newProperty(path + "chat.increase-sensitivity", false);

    @Comment("Should staff be notified when a player swears?")
    public static final Property<Boolean> NOTIFY_STAFF_CHAT = newProperty(path + "chat.notify-staff", true);

    @Comment("Should a command be executed when a player swears?")
    public static final Property<Boolean> EXECUTE_CHAT_SWEARS = newProperty(path + "chat.commands.toggle", false);

    @Comment("The command that is executed when a player swears.")
    public static final Property<List<String>> EXECUTED_CHAT_SWEARS = newListProperty(path + "chat.commands.values", List.of(
            "kick {player} Do not swear in chat",
            "warn {player} Do not swear in chat"
    ));
    
    @Comment("Whenever a player swears in chat, The message will get logged in swears.txt")
    public static final Property<Boolean> LOG_SWEARS_CHAT = newProperty(path + "chat.commands.log", false);

    // Commands.
    @Comment("Whether to block swears in commands or not.")
    public static final Property<Boolean> BLOCK_SWEARS_COMMANDS = newProperty(path + "commands.enable", false);

    @Comment("Should the anti swear checks be more sensitives? Warning: It will cause false positives.")
    public static final Property<Boolean> INCREASE_SENSITIVITY_COMMANDS = newProperty(path + "commands.increase-sensitivity", false);

    @Comment("Should staff be notified when a player swears?")
    public static final Property<Boolean> NOTIFY_STAFF_COMMANDS = newProperty(path + "commands.notify-staff", true);

    @Comment("Should a command be executed when a player swears?")
    public static final Property<Boolean> EXECUTE_COMMANDS_SWEARS = newProperty(path + "commands.toggle", false);

    @Comment("The commands that are executed when a player swears.")
    public static final Property<List<String>> EXECUTED_COMMANDS_SWEARS = newListProperty(path + "commands.values", List.of(
            "kick {player} Do not swear in chat",
            "warn {player} Do not swear in chat"
    ));

    @Comment("Whenever a player swears while using commands, The message will get logged in advertisements.txt")
    public static final Property<Boolean> LOG_SWEARS_COMMANDS = newProperty(path + "commands.log", false);

    // Signs.
    @Comment("Whether to block swears in signs or not.")
    public static final Property<Boolean> BLOCK_SWEARS_SIGNS = newProperty(path + "signs.enable", false);

    @Comment("Should the anti swears checks be more sensitives? Warning: It will cause false positives.")
    public static final Property<Boolean> INCREASE_SENSITIVITY_SIGNS = newProperty(path + "signs.increase-sensitivity", false);

    @Comment("Should staff be notified when a player swears?")
    public static final Property<Boolean> NOTIFY_STAFF_SIGNS_SWEARS = newProperty(path + "signs.notify-staff", true);

    @Comment("Should a command be executed when a player swears in signs?")
    public static final Property<Boolean> EXECUTE_COMMANDS_SIGNS = newProperty(path + "signs.toggle", false);

    @Comment("The command that is executed when a player advertises.")
    public static final Property<List<String>> EXECUTED_COMMANDS_SIGNS = newListProperty(path + "signs.values", List.of(
            "kick {player} Do not swear in chat",
            "warn {player} Do not swear in chat"
    ));

    @Comment("Whenever a player advertises in a sign, The message will get logged in advertisements.txt")
    public static final Property<Boolean> LOG_ADVERTISEMENTS_SIGNS = newProperty(path + "signs.log", false);
}