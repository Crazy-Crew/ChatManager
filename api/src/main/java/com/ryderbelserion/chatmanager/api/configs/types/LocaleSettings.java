package com.ryderbelserion.chatmanager.api.configs.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

/**
 * @author RyderBelserion
 *
 * Description: The lang-en.yml options.
 */
public class LocaleSettings implements SettingsHolder {

    // Empty constructor required by SettingsHolder
    public LocaleSettings() {}

    private static final String prefix = "command.";

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "Github: https://github.com/Crazy-Crew",
                "",
                "Issues: https://github.com/Crazy-Crew/ChatManager/issues",
                "Features: https://github.com/Crazy-Crew/ChatManager/issues",
                "Translations: https://github.com/Crazy-Crew/ChatManager/issues"
        };

        conf.setComment("misc", header);

        conf.setComment(prefix + "broadcast.sound", "Play a sound when /broadcast is used.");
        conf.setComment(prefix + "announcement.sound", "Play a sound when /announcement is used.");
        conf.setComment(prefix + "warning.sound", "Play a sound when /warning is used.");
    }

    public static final Property<String> UNKNOWN_COMMAND = newProperty("misc.unknown-command", "<red>This command is not known.</red>");

    public static final Property<String> OUT_OF_ORDER = newProperty("misc.out-of-order", "<red>This is currently out of order until further notice.</red>");

    public static final Property<String> SUCCESSFUL_CONVERSION = newProperty("misc.file-conversion.success", "<green>Successfully converted any possible files while doing %action%.</green>");

    public static final Property<String> FAILED_CONVERSION = newProperty("misc.file-conversion.failed", "<red>Failed to convert the files because: <gold>%reason%</gold></red>");

    public static final Property<String> FEATURE_DISABLED = newProperty("misc.feature-disabled", "<red>This feature is disabled.</red>");

    public static final Property<String> CORRECT_USAGE = newProperty("misc.correct-usage", "<red>The correct usage for this command is</red> <yellow>%usage%</yellow>");

    public static final Property<String> INVALID_PAGE = newProperty("help.invalid-page", "<red>The page</red> <yellow>%page%</yellow> <red>does not exist.</red>");

    public static final Property<String> PAGE_FORMAT = newProperty("help.page-format", "<gold>%command% %args%</gold> <gray>»</gray> <reset>%description%");

    public static final Property<String> HELP_HEADER = newProperty("help.header", "<gray>────────</gray> <gold>ChatManager Help %page%</gold> <gray>────────</gray>");

    public static final Property<String> HELP_FOOTER = newProperty("help.footer", "<gray>────────</gray> <gold>ChatManager Help %page%</gold> %button%");

    @Comment({
            "The only options that work here are run_command, suggest_command, copy_to_clipboard",
            "",
            "Warning: They are case-sensitive names so type them exactly as given above!",
            "",
            "This is what happens if you click the command in the /chatmanager help menu."
    })
    public static final Property<String> HOVER_ACTION = newProperty("help.hover.action", "copy_to_clipboard");

    public static final Property<String> HOVER_FORMAT = newProperty("help.hover.format", "<gray>Click me to run the command.</gray> <gold>%command%</gold>");

    public static final Property<String> PAGE_NEXT = newProperty("help.page-next", " <green>»»»</green>");

    public static final Property<String> PAGE_BACK = newProperty("help.page-back", " <red>«««</red>");

    public static final Property<String> GO_TO_PAGE = newProperty("help.go-to-page", "<gray>Go to page</gray> <gold>%page%</gold>");

    public static final Property<String> INTERNAL_ERROR = newProperty("errors.internal-error", "<red>An internal error has occurred. Please check the console for the full error.</red>");

    public static final Property<String> TOO_MANY_ARGS = newProperty("player.requirements.too-many-args", "<red>You put more arguments then I can handle.</red>");

    public static final Property<String> NOT_ENOUGH_ARGS = newProperty("player.requirements.not-enough-args", "<red>You did not supply enough arguments.</red>");

    public static final Property<String> MUST_BE_PLAYER = newProperty("player.requirements.must-be-player", "<red>You must be a player to use this command.</red>");

    public static final Property<String> MUST_BE_CONSOLE_SENDER = newProperty("player.requirements.must-be-console-sender", "<red>You must be using console to use this command.</red>");

    // Modules

    private static final String module = "modules.";

    public static final Property<String> ANTI_ADVERTISING_CHAT_MESSAGE = newProperty(module + "anti-advertising.chat.message", "<red>Advertising is not allowed in chat. Staff has been notified.</red>");

    public static final Property<String> ANTI_ADVERTISING_CHAT_NOTIFY_FORMAT = newProperty(module + "anti-advertising.chat.notify-staff-format", "<gray>[Anti-Advertise Chat]</gray> <white>%player%:</white> <gray>{message}</gray>");

    public static final Property<String> ANTI_ADVERTISING_COMMAND_MESSAGE = newProperty(module + "anti-advertising.command.message", "<red>Advertising is not allowed in commands. Staff has been notified.</red>");

    public static final Property<String> ANTI_ADVERTISING_COMMAND_NOTIFY_FORMAT = newProperty(module + "anti-advertising.command.notify-staff-format", "<gray>[Anti-Advertise Commands]</gray> <white>%player%:</white> <gray>{message}</gray>");

    public static final Property<String> ANTI_ADVERTISING_SIGN_MESSAGE = newProperty(module + "anti-advertising.sign.message", "<red>Advertising is not allowed in signs. Staff has been notified.</red>");

    public static final Property<String> ANTI_ADVERTISING_SIGN_NOTIFY_FORMAT = newProperty(module + "anti-advertising.sign.notify-staff-format", "<gray>[Anti-Advertise Signs]</gray> <white>%player%:</white> <gray>{message}</gray>");

    // Anti Bot
    public static final Property<String> ANTI_BOT_DENY_CHAT_MESSAGE = newProperty(module + "anti-bot.deny-chat-message", "<red>You must move one block before talking in chat.</red>");

    public static final Property<String> ANTI_BOT_DENY_COMMAND_MESSAGE = newProperty(module + "anti-bot.deny-command-message", "<red>You must move one block before executing commands.</red>");

    // Anti Caps
    public static final Property<String> ANTI_CAPS_DENY_CHAT_MESSAGE = newProperty(module + "anti-caps.deny-message-chat", "<red>Please do not use caps in chat.</red>");

    public static final Property<String> ANTI_CAPS_DENY_COMMAND_MESSAGE = newProperty(module + "anti-caps.deny-message-command", "<red>Please do not use caps in commands.</red>");

    // Anti Spam
    public static final Property<String> ANTI_SPAM_CHAT_REPETITIVE_MESSAGE = newProperty(module + "anti-spam.chat.repetitive-message", "<red>Please do not repeat the same message.</red>");

    public static final Property<String> ANTI_SPAM_CHAT_DELAY_MESSAGE = newProperty(module + "anti-spam.chat.delay-message", "<gray>Please wait</gray> <red>{time}</red> seconds <gray>before sending another message.</gray>");

    public static final Property<String> ANTI_SPAM_COMMAND_REPETITIVE_MESSAGE = newProperty(module + "anti-spam.command.repetitive-message", "<red>Please do not repeat the same command.</red>");

    public static final Property<String> ANTI_SPAM_COMMAND_DELAY_MESSAGE = newProperty(module + "anti-spam.command.delay-message", "<gray>Please wait</gray> <red>{time}</red> seconds <gray>before sending another command.</gray>");

    // Anti Swear
    public static final Property<String> ANTI_SWEAR_CHAT_MESSAGE = newProperty(module + "anti-swear.chat.message", "<red>Please do not curse in chat.</red>");

    public static final Property<String> ANTI_SWEAR_CHAT_NOTIFY_FORMAT = newProperty(module + "anti-swear.chat.notify-staff-format", "<gray>[Anti-Swear Chat]</gray> <white>%player%:</white> <gray>{message}</gray>");

    public static final Property<String> ANTI_SWEAR_COMMAND_MESSAGE = newProperty(module + "anti-swear.command.message", "<red>Please do not curse in commands.</red>");

    public static final Property<String> ANTI_SWEAR_COMMAND_NOTIFY_FORMAT = newProperty(module + "anti-swear.command.notify-staff-format", "<gray>[Anti-Swear Commands]</gray> <white>%player%:</white> <gray>{message}</gray>");

    public static final Property<String> ANTI_SWEAR_SIGN_MESSAGE = newProperty(module + "anti-swear.sign.message", "<red>Please do not curse in signs.</red>");

    public static final Property<String> ANTI_SWEAR_SIGN_NOTIFY_FORMAT = newProperty(module + "anti-swear.sign.notify-staff-format", "<gray>[Anti-Swear Signs]</gray> <white>%player%:</white> <gray>{message}</gray>");

    public static final Property<String> ANTI_SWEAR_BLACKLISTED_WORD_ADDED = newProperty(module + "anti-swear.blacklisted-word.added", "<gray>You added the word</gray> <red>{word}</red> <gray>to the anti-swears blacklist</gray>");

    public static final Property<String> ANTI_SWEAR_BLACKLISTED_WORD_EXISTS = newProperty(module + "anti-swear.blacklisted-word.exists", "<gray>The word</gray> <red>{word}</red> <gray>is already added to the anti-swears blacklist</gray>");

    public static final Property<String> ANTI_SWEAR_BLACKLISTED_WORD_REMOVED = newProperty(module + "anti-swear.blacklisted-word.removed", "<gray>You removed the word</gray> <red>{word}</red> <gray>from the anti-swears blacklist</gray>");

    public static final Property<String> ANTI_SWEAR_BLACKLISTED_WORD_NOT_FOUND = newProperty(module + "anti-swear.blacklisted-word.not-found", "<gray>The word</gray> <red>{word}</red> <gray>is not in the anti-swears blacklist</gray>");

    public static final Property<String> ANTI_SWEAR_WHITELISTED_WORD_ADDED = newProperty(module + "anti-swear.whitelisted-word.added", "<gray>You added the word</gray> <red>{word}</red> <gray>to the anti-swears whitelist</gray>");

    public static final Property<String> ANTI_SWEAR_WHITELISTED_WORD_EXISTS = newProperty(module + "anti-swear.whitelisted-word.exists", "<gray>The word</gray> <red>{word}</red> <gray>is already added to the anti-swears whitelist</gray>");

    public static final Property<String> ANTI_SWEAR_WHITELISTED_WORD_REMOVED = newProperty(module + "anti-swear.whitelisted-word.removed", "<gray>You removed the word</gray> <red>{word}</red> <gray>from the anti-swears whitelist</gray>");

    public static final Property<String> ANTI_SWEAR_WHITELISTED_WORD_NOT_FOUND = newProperty(module + "anti-swear.whitelisted-word.not-found", "<gray>The word</gray> <red>{word}</red> <gray>is not in the anti-swears whitelist</gray>");

    // Anti Unicode
    public static final Property<String> ANTI_UNICODE_DENY_MESSAGE = newProperty(module + "anti-unicode.message", "<red>Please do not use special characters in chat</red>");

    public static final Property<String> ANTI_UNICODE_NOTIFY_FORMAT = newProperty(module + "anti-unicode.notify-staff-format", "<gray>[Anti-Unicode]</gray> <white>%player%:</white> <gray>{message}</gray>");

    // Auto Broadcast
    public static final Property<String> AUTO_BROADCAST_LIST = newProperty(module + "other.auto-broadcast.list", "<red>{section}''s</red> <gray>auto-broadcast messages</gray>");

    public static final Property<String> AUTO_BROADCAST_ADDED = newProperty(module + "other.auto-broadcast.added", "<reset>{message}</reset> <gray>has been added to the <red>{section}</red> <gray>messages!</gray>");

    public static final Property<String> AUTO_BROADCAST_CREATED = newProperty(module + "other.auto-broadcast.created", "<gray>Created the world</gray> <red>{world}</red> <gray>with the message</gray> <reset>{message}.</reset>");

    // Banned Commands
    public static final Property<String> BANNED_COMMANDS_MESSAGE = newProperty(module + "other.banned-commands.message", "<red>You do not have permission to use the command</red> <gray>/{command}.</gray>");

    public static final Property<String> BANNED_COMMANDS_ADDED = newProperty(module + "other.banned-commands.added", "<gray>You added the command</gray> <red>{command}</red> <gray>to the list of banned commands</gray>");

    public static final Property<String> BANNED_COMMANDS_EXISTS = newProperty(module + "other.banned-commands.exists", "<gray>The command</gray> <red>{command}</red> <gray>is already added to the list of banned commands.</gray>");

    public static final Property<String> BANNED_COMMANDS_REMOVED = newProperty(module + "other.banned-commands.removed", "<gray>You removed the command</gray> <red>{command}</red> <gray>from the list of banned commands.</gray>");

    public static final Property<String> BANNED_COMMANDS_NOT_FOUND = newProperty(module + "other.banned-commands.not-found", "<gray>The command</gray> <red>{command}</red> <gray>is not in the list of banned commands.</gray>");

    public static final Property<String> BANNED_COMMANDS_NOTIFY_STAFF = newProperty(module + "other.banned-commands.notify-staff-format", "<gray>[Blocked-Command]</gray> <white>%player%:</white> <gray>{command}</gray>");

    // Chat Radius
    public static final Property<String> LOCAL_CHAT_ENABLED = newProperty(module + "other.chat-radius.local-chat.enabled", "<gray>You have entered</gray> <red>Local Chat</red> <gray>Do</gray> <red>/chatmanager chat-radius global</red> <gray>or</gray> <red>/chatmanager chat-radius world </red> <gray>to leave Local Chat</gray>");

    public static final Property<String> LOCAL_CHAT_ALREADY_ENABLED = newProperty(module + "other.chat-radius.local-chat.already-enabled", "<gray>You are already in Local Chat.</gray>");

    public static final Property<String> GLOBAL_CHAT_ENABLED = newProperty(module + "other.chat-radius.global-chat.enabled", "<gray>You have entered</gray> <red>Global Chat</red> <gray>Do</gray> <red>/chatmanager chat-radius global</red> <gray>or</gray> <red>/chatmanager chat-radius global </red> <gray>to leave Global Chat</gray>");

    public static final Property<String> GLOBAL_CHAT_ALREADY_ENABLED = newProperty(module + "other.chat-radius.global-chat.already-enabled", "<gray>You are already in Global Chat.</gray>");

    public static final Property<String> WORLD_CHAT_ENABLED = newProperty(module + "other.chat-radius.world-chat.enabled", "<gray>You have entered</gray> <red>World Chat</red> <gray>Do</gray> <red>/chatmanager chat-radius world</red> <gray>or</gray> <red>/chatmanager chat-radius world </red> <gray>to leave World Chat</gray>");

    public static final Property<String> WORLD_CHAT_ALREADY_ENABLED = newProperty(module + "other.chat-radius.world-chat.already-enabled", "<gray>You are already in World Chat.</gray>");

    public static final Property<String> PER_WORLD_CHAT_BYPASS_ENABLED = newProperty(module + "other.per-world-chat.bypass-enabled", "<green>Per-world chat bypass has been enabled.</green>");

    public static final Property<String> PER_WORLD_CHAT_BYPASS_DISABLED = newProperty(module + "other.per-world-chat.bypass-disabled", "<red>Per-world chat bypass has been disabled.</red>");

    public static final Property<String> RADIUS_CHAT_SPY_ENABLED = newProperty(module + "other.radius-spy.enabled", "<gray>Chat Radius Spy has been</gray> <green>enabled.</green>");

    public static final Property<String> RADIUS_CHAT_SPY_DISABLED = newProperty(module + "other.radius-spy.disabled", "<gray>Chat Radius Spy has been</gray> <red>disabled.</red>");

    // /chc broadcast <message>
    public static final Property<String> COMMAND_BROADCAST_PREFIX = newProperty(prefix + "broadcast.prefix", "<red>[</red><dark_red>Broadcast</dark_red><red>]</red><reset> ");

    @Comment("If the sound is enabled.")
    public static final Property<Boolean> COMMAND_BROADCAST_SOUND_ENABLED = newProperty(prefix + "broadcast.sound.enabled", false);

    @Comment({
            "The type of sound.",
            "https://jd.papermc.io/paper/1.19/org/bukkit/Sound.html"
    })
    public static final Property<String> COMMAND_BROADCAST_SOUND_TYPE = newProperty(prefix + "broadcast.sound.type", "AMBIENT_CAVE");

    // /chc announce <message>
    public static final Property<List<String>> COMMAND_ANNOUNCEMENT_MESSAGE = newListProperty(prefix + "announcement.message", List.of(
            "<black>--------------------------</black>",
            "<yellow><bold>ANNOUNCEMENT</bold></yellow>",
            "{message}",
            "<black>--------------------------</black>"
    ));

    @Comment("If the sound is enabled.")
    public static final Property<Boolean> COMMAND_ANNOUNCEMENT_SOUND_ENABLED = newProperty(prefix + "announcement.sound.enabled", false);

    @Comment({
            "The type of sound.",
            "https://jd.papermc.io/paper/1.19/org/bukkit/Sound.html"
    })
    public static final Property<String> COMMAND_ANNOUNCEMENT_SOUND_TYPE = newProperty(prefix + "announcement.sound.type", "AMBIENT_CAVE");

    // /chc warn <message>
    public static final Property<List<String>> COMMAND_WARNING_MESSAGE = newListProperty(prefix + "warning.message", List.of(
            "<black>--------------------------</black>",
            "<red><bold>WARNING</bold></red>",
            "{message}",
            "<black>--------------------------</black>"
    ));

    @Comment("If the sound is enabled.")
    public static final Property<Boolean> COMMAND_WARNING_SOUND_ENABLED = newProperty(prefix + "warning.sound.enabled", false);

    @Comment({
            "The type of sound.",
            "https://jd.papermc.io/paper/1.19/org/bukkit/Sound.html"
    })
    public static final Property<String> COMMAND_WARNING_SOUND_TYPE = newProperty(prefix + "warning.sound.type", "AMBIENT_CAVE");

    // /chc convert <options>
    public static final Property<String> NO_FILES_TO_CONVERT = newProperty(prefix + "convert.no-files-to-convert", "<red>No available plugins to convert files.</red>");

    public static final Property<String> ERROR_CONVERTING_FILES = newProperty(prefix + "convert.error-converting-files", "<red>An error has occurred while trying to convert files. We could not convert</red> <gold>%file%</gold> <red>so please check the console.</red>");

    public static final Property<String> SUCCESSFULLY_CONVERTED_FILES = newProperty(prefix + "convert.successfully-converted-files", "<blue>Plugin Conversion has succeeded!</blue>");

    // /chc reload
    public static final Property<String> COMMAND_CONFIRM_RELOAD = newProperty(prefix + "reload.confirm-reload", "<yellow>Are you sure you want to reload the plugin?</yellow>");

    public static final Property<String> COMMAND_RELOAD = newProperty(prefix + "reload.reload-completed", "<red>You have reloaded the plugin.</red>");

    // /chc clear-chat
    public static final Property<String> COMMAND_CLEAR_CHAT_MESSAGE = newProperty(prefix + "clear-chat.staff-message", "<yellow>Chat has been cleared by %player%.</yellow>");

    public static final Property<List<String>> COMMAND_CLEAR_CHAT_BROADCAST = newListProperty(prefix + "clear-chat.broadcast-message", List.of(
            "<white>*</white><red><underline>--------------------------------------------</underline></red><white>*</white>",
            "<yellow>The chat has been cleared by %player%</yellow>",
            "<white>*</white><red><underline>--------------------------------------------</underline></red><white>*</white>"
    ));

    // /chc command-spy
    public static final Property<String> COMMAND_SPY_FORMAT = newProperty(prefix + "command-spy.format", "<gray>[Command-Spy] %player%:</gray> <aqua>{command}</aqua>");

    public static final Property<String> COMMAND_SPY_ENABLED = newProperty(prefix + "command-spy.enabled", "<green>Command Spy has been enabled.</green>");

    public static final Property<String> COMMAND_SPY_DISABLED = newProperty(prefix + "command-spy.disabled", "<red>Command Spy has been disabled.</red>");

    // /chc social-spy
    public static final Property<String> COMMAND_SOCIAL_SPY_FORMAT = newProperty(prefix + "command-spy.format", "<blue><bold>(*)</bold></blue> <white><bold>[<yellow>%player%</yellow> <purple>!!</purple> <yellow>{receiver}</yellow>]</bold></white> <blue>{message}</blue>");

    public static final Property<String> COMMAND_SOCIAL_SPY_ENABLED = newProperty(prefix + "social-spy.enabled", "<green>Social Spy has been enabled.</green>");

    public static final Property<String> COMMAND_SOCIAL_SPY_DISABLED = newProperty(prefix + "social-spy.disabled", "<red>Social Spy has been disabled.</red>");
}