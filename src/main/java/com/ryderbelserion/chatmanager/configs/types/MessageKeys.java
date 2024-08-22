package com.ryderbelserion.chatmanager.configs.types;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.ListProperty;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class MessageKeys implements SettingsHolder {

    public static final Property<String> plugin_reload = newProperty("Message.Reload", "{prefix}<green>Config has been reloaded.");

    public static final Property<String> anti_advertising_chat_message = newProperty("Anti_Advertising.Chat.Message", "{prefix}<red>Advertising is not allowed in chat. Staff has been notified.");
    public static final Property<String> anti_advertising_chat_notify_staff = newProperty("Anti_Advertising.Chat.Notify_Staff_Format", "<gray>[Anti-Advertise Chat] <white>{player}: <gray>{message}");

    public static final Property<String> anti_advertising_commands_message = newProperty("Anti_Advertising.Commands.Message", "{prefix}<red>Advertising is not allowed in commands. Staff has been notified.");
    public static final Property<String> anti_advertising_commands_notify_staff = newProperty("Anti_Advertising.Commands.Notify_Staff_Format", "<gray>[Anti-Advertise Commands] <white>{player}: <gray>{message}");

    public static final Property<String> anti_advertising_signs_message = newProperty("Anti_Advertising.Signs.Message", "{prefix}<red>Advertising is not allowed on signs. Staff has been notified.");
    public static final Property<String> anti_advertising_signs_notify_staff = newProperty("Anti_Advertising.Signs.Notify_Staff_Format", "<gray>[Anti-Advertise Signs] <white>{player}: <gray>{message}");

    public static final Property<String> anti_bot_deny_chat = newProperty("Anti_Bot.Message_Chat", "{prefix}<red>You must move one block before talking in chat.");
    public static final Property<String> anti_bot_deny_command = newProperty("Anti_Bot.Message_Commands", "{prefix}<red>You must move one block before executing commands.");

    public static final Property<String> anti_caps_message_chat = newProperty("Anti_Caps.Message_Chat", "{prefix}<red>Please do not use caps in chat.");
    public static final Property<String> anti_caps_message_command = newProperty("Anti_Caps.Message_Commands", "{prefix}<red>Please do not use caps in commands.");

    public static final Property<String> anti_spam_chat_repeated_message = newProperty("Anti_Spam.Chat.Repetitive_Message", "{prefix}<red>Please do not repeat the same message.");
    public static final Property<String> anti_spam_chat_delay_message = newProperty("Anti_Spam.Chat.Delay_Message", "{prefix}<gray>Please wait <red>{Time} seconds <gray>before sending another message.");

    public static final Property<String> anti_spam_command_repeated_message = newProperty("Anti_Spam.Command.Repetitive_Message", "{prefix}<red>Please do not repeat the same command.");
    public static final Property<String> anti_spam_command_delay_message = newProperty("Anti_Spam.Command.Delay_Message", "{prefix}<gray>Please wait <red>{Time} seconds <gray>before sending another command.");

    public static final Property<String> anti_swear_chat_message = newProperty("Anti_Swear.Chat.Message", "{prefix}<red>Please do not curse in chat.");
    public static final Property<String> anti_swear_chat_notify_staff = newProperty("Anti_Swear.Chat.Notify_Staff_Format", "<gray>[Anti-Swear Chat] <white>{player}: <gray>{message}");

    public static final Property<String> anti_swear_commands_message = newProperty("Anti_Swear.Commands.Message", "{prefix}<red>Please do not curse in commands.");
    public static final Property<String> anti_swear_commands_notify_staff = newProperty("Anti_Swear.Commands.Notify_Staff_Format", "<gray>[Anti-Swear Commands] <white>{player}: <gray>{message}");

    public static final Property<String> anti_swear_signs_message = newProperty("Anti_Swear.Signs.Message", "{prefix}<red>Please do not curse on signs.");
    public static final Property<String> anti_swear_signs_notify_staff = newProperty("Anti_Swear.Signs.Notify_Staff_Format", "<gray>[Anti-Swear Signs] <white>{player}: <gray>{message}");

    public static final Property<String> blacklist_added = newProperty("Anti_Swear.Blacklisted_Word.Added", "{prefix}<gray>You added the word <red>{word} <gray>to the anti swears blacklist.");
    public static final Property<String> blacklist_exists = newProperty("Anti_Swear.Blacklisted_Word.Exists", "{prefix}<gray>The word <red>{word} <gray>is already added to the anti swears blacklist.");
    public static final Property<String> blacklist_removed = newProperty("Anti_Swear.Blacklisted_Word.Removed", "{prefix}<gray>You removed the word <red>{word} <gray>from the anti swears blacklist.");
    public static final Property<String> blacklist_not_found = newProperty("Anti_Swear.Blacklisted_Word.Not_Found", "{prefix}<gray>The word <red>{word} <gray>is not in the anti swears blacklist.");

    public static final Property<String> whitelist_added = newProperty("Anti_Swear.Whitelisted_Word.Added", "{prefix}<gray>You added the word <red>{word} <gray>to the anti swears whitelist.");
    public static final Property<String> whitelist_exists = newProperty("Anti_Swear.Whitelisted_Word.Exists", "{prefix}<gray>The word <red>{word} <gray>is already added to the anti swears whitelist.");
    public static final Property<String> whitelist_removed = newProperty("Anti_Swear.Whitelisted_Word.Removed", "{prefix}<gray>You removed the word <red>{word} <gray>from the anti swears whitelist.");
    public static final Property<String> whitelist_not_found = newProperty("Anti_Swear.Whitelisted_Word.Not_Found", "{prefix}<gray>The word <red>{word} <gray>is not in the anti swears whitelist.");

    public static final Property<String> anti_unicode_message = newProperty("Anti_Unicode.Message", "{prefix}<red>Please do not use special characters in chat.");
    public static final Property<String> anti_unicode_notify_staff = newProperty("Anti_Unicode.Notify_Staff", "<gray>[Anti-Unicode] <white>{player}: <gray>{message}");

    public static final Property<String> auto_broadcast_list = newProperty("Auto_Broadcast.List", "<red>{section}'s <gray>auto-broadcast messages:");
    public static final Property<String> auto_broadcast_added = newProperty("Auto_Broadcast.Added", "{prefix}<reset>{message} <gray>has been added to the <red>{section} <gray>messages<gray>!");
    public static final Property<String> auto_broadcast_created = newProperty("Auto_Broadcast.Created", "{prefix}<gray>Created the world <red>'%world%' <gray>with the message <reset>{message}<gray>.");

    public static final Property<String> banned_commands_message = newProperty("Banned_Commands.Message", "{prefix}<red>You do not have permission to use the command <gray>/{command}<red>.");
    public static final Property<String> banned_commands_command_added = newProperty("Banned_Commands.Command_Added", "{prefix}<gray>You added the command <red>{command} <gray>to the list of banned commands.");
    public static final Property<String> banned_commands_command_exists = newProperty("Banned_Commands.Command_Exists", "{prefix}<gray>The command <red>{command} <gray>is already added to the list of banned commands.");
    public static final Property<String> banned_commands_command_removed = newProperty("Banned_Commands.Command_Removed", "{prefix}<gray>You removed the command <red>{command} <gray>from the list of banned commands.");
    public static final Property<String> banned_commands_command_not_found = newProperty("Banned_Commands.Command_Not_Found", "{prefix}<gray>The command <red>{command} <gray>is not in the list of banned commands.");
    public static final Property<String> banned_commands_notify_staff_format = newProperty("Banned_Commands.Notify_Staff_Format", "<gray>[Blocked-Cmd] <white>{player}: <gray>{command}");

    public static final Property<String> chat_radius_local_chat_enabled = newProperty("Chat_Radius.Local_Chat.Enabled", "{prefix}<gray>You've entered <red>Local Chat<gray>! Do <red>/ChatRadius global <gray>or <red>/ChatRadius world <gray>to leave Local Chat!");
    public static final Property<String> chat_radius_local_chat_already_enabled = newProperty("Chat_Radius.Local_Chat.Already_Enabled", "{prefix}<gray>You are already in local chat.");
    public static final Property<String> chat_radius_global_chat_enabled = newProperty("Chat_Radius.Global_Chat.Enabled", "{prefix}<gray>You've entered <red>Global Chat<gray>! Do <red>/ChatRadius local <gray>or <red>/ChatRadius world <gray>to leave global Chat!");
    public static final Property<String> chat_radius_global_chat_already_enabled = newProperty("Chat_Radius.Global_Chat.Already_Enabled", "{prefix}<gray>You are already in global chat.");
    public static final Property<String> chat_radius_world_chat_enabled = newProperty("Chat_Radius.World_Chat.Enabled", "{prefix}<gray>You've entered <red>World Chat<gray>! Do <red>/ChatRadius local <gray>or <red>/ChatRadius global <gray>to leave world Chat!");
    public static final Property<String> chat_radius_world_chat_already_enabled = newProperty("Chat_Radius.World_Chat.Already_Enabled", "{prefix}<gray>You are already in world chat.");
    public static final Property<String> chat_radius_spy_enabled = newProperty("Chat_Radius.Spy.Enabled", "{prefix}<gray>Chat Radius spy has been <green>Enabled.");
    public static final Property<String> chat_radius_spy_disabled = newProperty("Chat_Radius.Spy.Disabled", "{prefix}<gray>Chat Radius spy has been <red>Disabled.");

    public static final Property<String> clear_chat_staff_message = newProperty("Clear_Chat.Staff_Message", "{prefix}<yellow>Chat has been cleared by {player}.");
    public static final ListProperty<String> clear_chat_broadcast_message = newListProperty("Clear_Chat.Broadcast_Message", List.of(
            "<white>*<red><st>--------------------------------------------</st><white>*",
            "<yellow>The chat has been cleared by {player}",
            "<white>*<red><st>--------------------------------------------</st><white>*"
    ));

    public static final Property<String> mute_chat_denied_message = newProperty("Mute_Chat.Denied_Message", "{prefix}<red>You are not able to talk in chat right now.");
    public static final Property<String> mute_chat_broadcast_enabled = newProperty("Mute_Chat.Broadcast_Messages.Enabled", "{prefix}<green>Chat has been Enabled by {player}.");
    public static final Property<String> mute_chat_broadcast_disabled = newProperty("Mute_Chat.Broadcast_Messages.Disabled", "{prefix}<red>Chat has been Disabled by {player}.");
    public static final Property<String> mute_chat_blocked_commands_message = newProperty("Mute_Chat.Blocked_Commands.Message", "{prefix}<red>You are not allowed to use that command while the chat is muted.");

    public static final Property<String> per_world_chat_bypass_enabled = newProperty("Per_World_Chat.Bypass_Enabled", "{prefix}<green>Per-world chat bypass has been enabled.");
    public static final Property<String> per_world_chat_bypass_disabled = newProperty("Per_World_Chat.Bypass_Disabled", "{prefix}<red>Per-world chat bypass has been disabled.");

    public static final Property<String> private_message_recipient_not_found = newProperty("Private_Message.Recipient_Not_Found", "{prefix}<red>You have nobody to reply to.");
    public static final Property<String> private_message_toggled = newProperty("Private_Message.Toggled", "{prefix}<red>That player cannot receive messages right now.");
    public static final Property<String> private_message_ignored = newProperty("Private_Message.Ignored", "{prefix}<red>{target} <gray>is currently ignoring you and cant receive any private messages.");
    public static final Property<String> private_message_self = newProperty("Private_Message.Self", "{prefix}<red>You cannot message yourself.");
    public static final Property<String> private_message_afk = newProperty("Private_Message.AFK", "{prefix}<red>{target} <gray>is currently afk.");

    public static final Property<String> staff_chat_enabled = newProperty("Staff_Chat.Enabled", "{prefix}<green>Staff chat has been enabled.");
    public static final Property<String> staff_chat_disabled = newProperty("Staff_Chat.Disabled", "{prefix}<red>Staff chat has been disabled.");

    public static final Property<String> toggle_chat_enabled = newProperty("Toggle_Chat.Enabled", "{prefix}<green>Toggle chat has been enabled, you will no longer receive chat messages.");
    public static final Property<String> toggle_chat_disabled = newProperty("Toggle_Chat.Disabled", "{prefix}<red>Toggle chat has been disabled, you will start receiving chat messages.");

    public static final Property<String> toggle_mentions_enabled = newProperty("Toggle_Mentions.Enabled", "{prefix}<gray>Toggle mentions has been <green>enabled<gray>, you will no longer receive mention messages.");
    public static final Property<String> toggle_mentions_disabled = newProperty("Toggle_Mentions.Disabled", "{prefix}<gray>Toggle mentions has been <red>disabled<gray>, you will start receiving mention messages.");

    public static final Property<String> toggle_pm_enabled = newProperty("TogglePM.Enabled", "{prefix}<green>TogglePM has been enabled.");
    public static final Property<String> toggle_pm_disabled = newProperty("TogglePM.Disabled", "{prefix}<red>TogglePM has been disabled.");

}