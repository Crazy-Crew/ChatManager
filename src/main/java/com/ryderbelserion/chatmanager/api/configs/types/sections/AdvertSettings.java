package com.ryderbelserion.chatmanager.api.configs.types.sections;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class AdvertSettings implements SettingsHolder {

    private AdvertSettings() {}

    private static final String path = "anti-advertising.";

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "This section is related to advertising in chat/commands and signs.",
        };

        conf.setComment("anti-advertising", header);
    }

    // Chat.
    @Comment("Whether to block advertising in chat or not.")
    public static final Property<Boolean> BLOCK_ADVERTISING_CHAT = newProperty(path + "chat.enable", false);

    @Comment("Should the anti advertising checks be more sensitives? Warning: It will cause false positives.")
    public static final Property<Boolean> INCREASE_SENSITIVITY_CHAT = newProperty(path + "chat.increase-sensitivity", false);

    @Comment("Should staff be notified when a player advertises?")
    public static final Property<Boolean> NOTIFY_STAFF_CHAT = newProperty(path + "chat.notify-staff", true);

    @Comment("Should a command be executed when a player advertises?")
    public static final Property<Boolean> COMMAND_EXECUTE_TOGGLE_CHAT = newProperty(path + "chat.commands.toggle", false);

    @Comment("The command that is executed when a player advertises.")
    public static final Property<String> VALUE_CHAT = newProperty(path + "chat.commands.value", "kick {player} Please do not advertise in chat.");
    
    @Comment("Whenever a player advertises in chat, The message will get logged in advertisements.txt")
    public static final Property<Boolean> LOG_ADVERTISEMENTS_CHAT = newProperty(path + "chat.commands.log", false);
    
    
    // Commands.
    @Comment("Whether to block advertising in commands or not.")
    public static final Property<Boolean> BLOCK_ADVERTISING_COMMAND = newProperty(path + "commands.enable", false);

    @Comment("Should the anti advertising checks be more sensitives? Warning: It will cause false positives.")
    public static final Property<Boolean> INCREASE_SENSITIVITY_COMMAND = newProperty(path + "commands.increase-sensitivity", false);

    @Comment("Should staff be notified when a player advertises?")
    public static final Property<Boolean> NOTIFY_STAFF_COMMAND = newProperty(path + "commands.notify-staff", true);

    @Comment("Should a command be executed when a player advertises?")
    public static final Property<Boolean> COMMAND_EXECUTE_TOGGLE_COMMAND = newProperty(path + "commands.toggle", false);

    @Comment("The command that is executed when a player advertises.")
    public static final Property<String> VALUE_COMMAND = newProperty(path + "commands.value", "kick {player} Please do not advertise in commands.");

    @Comment("Whenever a player advertises in while using commands, The message will get logged in advertisements.txt")
    public static final Property<Boolean> LOG_ADVERTISEMENTS_COMMAND = newProperty(path + "commands.log", false);

    // Signs.
    @Comment("Whether to block advertising in signs or not.")
    public static final Property<Boolean> BLOCK_ADVERTISING_SIGNS = newProperty(path + "signs.enable", false);

    @Comment("Should the anti advertising checks be more sensitives? Warning: It will cause false positives.")
    public static final Property<Boolean> INCREASE_SENSITIVITY_SIGNS = newProperty(path + "signs.increase-sensitivity", false);

    @Comment("Should staff be notified when a player advertises?")
    public static final Property<Boolean> NOTIFY_STAFF_SIGNS = newProperty(path + "signs.notify-staff", true);

    @Comment("Should a command be executed when a player advertises?")
    public static final Property<Boolean> COMMAND_EXECUTE_TOGGLE_SIGNS = newProperty(path + "signs.toggle", false);

    @Comment("The command that is executed when a player advertises.")
    public static final Property<String> VALUE_SIGNS = newProperty(path + "signs.value", "kick {player} Please do not advertise in signs.");

    @Comment("Whenever a player advertises in a sign, The message will get logged in advertisements.txt")
    public static final Property<Boolean> LOG_ADVERTISEMENTS_SIGNS = newProperty(path + "signs.log", false);

    @Comment("Put any websites here that you don't want blocked in chat/commands or signs.")
    public static final Property<List<String>> WEBSITE_WHITELIST = newListProperty(path + "whitelist", List.of("google.com", "duckduckgo.com"));
}