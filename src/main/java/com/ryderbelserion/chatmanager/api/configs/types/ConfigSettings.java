package com.ryderbelserion.chatmanager.api.configs.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.*;

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

        String[] chatFormat = {
                "You require Vault to use per-group chat formats.",
                "",
                "Wiki: https://github.com/Crazy-Crew/ChatManager/wiki/Chat-Format",
                "Placeholders: https://github.com/Crazy-Crew/ChatManager/wiki/Chat-Format#placeholders-top"
        };

        conf.setComment("settings", header);

        conf.setComment("chat-format", chatFormat);
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

    // Anti Unicode.
    @Comment("Block special characters in chat.")
    public static final Property<Boolean> ANTI_UNICODE_ENABLED = newProperty("anti-unicode.enable", true);

    @Comment("Should staff get notified when a player uses special characters?")
    public static final Property<Boolean> ANTI_UNICODE_NOTIFY_STAFF = newProperty("anti-unicode.notify-staff", true);

    @Comment("Should a command be executed when a player uses special characters?")
    public static final Property<Boolean> ANTI_UNICODE_EXECUTE_COMMANDS = newProperty("anti-unicode.toggle", true);

    @Comment("The command that is executed when a player uses special characters in chat.")
    public static final Property<List<String>> ANTI_UNICODE_EXECUTED_COMMANDS = newListProperty("anti-unicode.values", List.of(
            "kick {player} Please do not use special characters in chat."
    ));

    @Comment("Anything that's in this list won’t be blocked by the anti unicode checker.")
    public static final Property<List<String>> ANTI_UNICODE_WHITELIST = newListProperty("anti-unicode.whitelist", List.of(
            "«",
            "»"
    ));

    // Banned Commands
    @Comment("Block special characters in chat.")
    public static final Property<Boolean> BANNED_COMMANDS_ENABLED = newProperty("banned-commands.enable", true);

    @Comment("Should the banned commands checks be more sensitives? Warning: It will cause false positives.")
    public static final Property<Boolean> INCREASE_SENSITIVITY_BANNED_COMMANDS = newProperty("banned-commands.increase-sensitivity", false);

    @Comment("Should staff get notified when a player uses special characters?")
    public static final Property<Boolean> BANNED_COMMANDS_NOTIFY_STAFF = newProperty("banned-commands.notify-staff", true);

    @Comment("Should a command be executed when a player uses special characters?")
    public static final Property<Boolean> BANNED_COMMANDS_EXECUTE_COMMANDS = newProperty("banned-commands.toggle", true);

    @Comment("The command that is executed when a player uses special characters in chat.")
    public static final Property<List<String>> BANNED_COMMANDS_EXECUTED_COMMANDS = newListProperty("banned-commands.values", List.of(
            "kick {player} You are not allowed to use that command!"
    ));

    // Groups

    @Comment("Whether to enable the chat format or not.")
    public static final Property<Boolean> CHAT_FORMAT_TOGGLE = newProperty("chat-format.toggle", true);

    @Comment("This will only be used if the players permission group isn't on the bottom of this section.")
    public static final Property<String> CHAT_DEFAULT_FORMAT = newProperty("chat-format.default-format", "");
}