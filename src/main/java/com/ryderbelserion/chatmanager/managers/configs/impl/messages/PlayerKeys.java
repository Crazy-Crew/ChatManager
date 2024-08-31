package com.ryderbelserion.chatmanager.managers.configs.impl.messages;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.ListProperty;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class PlayerKeys implements SettingsHolder {

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "All messages related to players."
        };

        conf.setComment("player", header);
    }

    public static final Property<String> must_be_a_player = newProperty("player.requirements.must-be-player", "{prefix}<red>You must be a player to use this command.");

    public static final Property<String> must_be_console_sender = newProperty("player.requirements.must-be-console-sender", "{prefix}<red>You must be using console to use this command.");

    @Comment("A list of available placeholders: {player}")
    public static final Property<String> not_online = newProperty("player.target-not-online", "{prefix}<red>{player} <gray>is not online.");

    public static final Property<String> same_player = newProperty("player.target-same-player", "{prefix}<red>You cannot use this command on yourself.");

    public static final Property<String> no_permission = newProperty("player.no-permission", "{prefix}<red>You do not have permission to use that command!");

    public static final Property<String> inventory_not_empty = newProperty("player.inventory-not-empty", "{prefix}<red>Inventory is not empty, Please clear up some room.");

    public static final Property<String> staff_chat_enabled = newProperty("player.staff.chat.enabled", "{prefix}<green>Staff chat has been enabled.");
    public static final Property<String> staff_chat_disabled = newProperty("player.staff.chat.disabled", "{prefix}<red>Staff chat has been disabled.");

    public static final Property<String> private_message_recipient_not_found = newProperty("player.private_message.recipient_not_found", "{prefix}<red>You have nobody to reply to.");
    public static final Property<String> private_message_toggled = newProperty("player.private_message.toggled", "{prefix}<red>{target} cannot receive messages right now.");
    public static final Property<String> private_message_ignored = newProperty("player.private_message.ignored", "{prefix}<red>{target} <gray>is currently ignoring you and cant receive any private messages.");
    public static final Property<String> private_message_self = newProperty("player.private_message.self", "{prefix}<red>You cannot message yourself.");
    public static final Property<String> private_message_afk = newProperty("player.private_message.afk", "{prefix}<red>{target} <gray>is currently afk.");

    public static final Property<String> chat_radius_local_chat_enabled = newProperty("player.chat.radius.local_chat.enabled", "{prefix}<gray>You've entered <red>Local Chat<gray>! Do <red>/chatmanager chatradius global <gray>or <red>/chatmanager chatradius world <gray>to leave Local Chat!");
    public static final Property<String> chat_radius_local_chat_already_enabled = newProperty("player.chat.radius.local_chat.already_enabled", "{prefix}<gray>You are already in local chat.");
    public static final Property<String> chat_radius_global_chat_enabled = newProperty("player.chat.radius.global_chat.enabled", "{prefix}<gray>You've entered <red>Global Chat<gray>! Do <red>/chatmanager chatradius local <gray>or <red>/chatmanager chatradius world <gray>to leave global Chat!");
    public static final Property<String> chat_radius_global_chat_already_enabled = newProperty("player.chat.radius.global_chat.already_enabled", "{prefix}<gray>You are already in global chat.");
    public static final Property<String> chat_radius_world_chat_enabled = newProperty("player.chat.radius.world_chat.enabled", "{prefix}<gray>You've entered <red>World Chat<gray>! Do <red>/chatmanager chatradius local <gray>or <red>/chatmanager chatradius global <gray>to leave world Chat!");
    public static final Property<String> chat_radius_world_chat_already_enabled = newProperty("player.chat.radius.world_chat.already_enabled", "{prefix}<gray>You are already in world chat.");
    public static final Property<String> chat_radius_spy_enabled = newProperty("player.chat.radius.spy.enabled", "{prefix}<gray>Chat Radius spy has been <green>Enabled.");
    public static final Property<String> chat_radius_spy_disabled = newProperty("player.chat.radius.spy.disabled", "{prefix}<gray>Chat Radius spy has been <red>Disabled.");

    public static final Property<String> anti_advertising_chat_message = newProperty("player.chat.antiadvertising.message", "{prefix}<red>Advertising is not allowed in chat. Staff has been notified.");
    public static final Property<String> anti_advertising_chat_notify_staff = newProperty("player.chat.antiadvertising.notify_staff_format", "<gray>[Anti-Advertise Chat] <white>{player}: <gray>{message}");

    public static final Property<String> anti_advertising_commands_message = newProperty("player.antiadvertising.commands.message", "{prefix}<red>Advertising is not allowed in commands. Staff has been notified.");
    public static final Property<String> anti_advertising_commands_notify_staff = newProperty("player.antiadvertising.commands.notify_staff_format", "<gray>[Anti-Advertise Commands] <white>{player}: <gray>{message}");

    public static final Property<String> anti_advertising_signs_message = newProperty("player.antiadvertising.signs.message", "{prefix}<red>Advertising is not allowed on signs. Staff has been notified.");
    public static final Property<String> anti_advertising_signs_notify_staff = newProperty("player.antiadvertising.signs.notify_staff_format", "<gray>[Anti-Advertise Signs] <white>{player}: <gray>{message}");

    public static final Property<String> anti_bot_deny_chat = newProperty("player.antibot.message_chat", "{prefix}<red>You must move one block before talking in chat.");
    public static final Property<String> anti_bot_deny_command = newProperty("player.antibot.message_commands", "{prefix}<red>You must move one block before executing commands.");

    public static final Property<String> anti_caps_message_chat = newProperty("player.anticaps.message_chat", "{prefix}<red>Please do not use caps in chat.");
    public static final Property<String> anti_caps_message_command = newProperty("player.anticaps.message_commands", "{prefix}<red>Please do not use caps in commands.");

    public static final Property<String> anti_spam_chat_repeated_message = newProperty("player.antispam.chat.repetitive_message", "{prefix}<red>Please do not repeat the same message.");
    public static final Property<String> anti_spam_chat_delay_message = newProperty("player.antispam.chat.delay_message", "{prefix}<gray>Please wait <red>{Time} seconds <gray>before sending another message.");

    public static final Property<String> anti_spam_command_repeated_message = newProperty("player.antispam.command.repetitive_message", "{prefix}<red>Please do not repeat the same command.");
    public static final Property<String> anti_spam_command_delay_message = newProperty("player.antispam.command.delay_message", "{prefix}<gray>Please wait <red>{Time} seconds <gray>before sending another command.");

    public static final Property<String> anti_swear_chat_message = newProperty("player.antiswear.chat.message", "{prefix}<red>Please do not curse in chat.");
    public static final Property<String> anti_swear_chat_notify_staff = newProperty("player.antiswear.chat.notify_staff_format", "<gray>[Anti-Swear Chat] <white>{player}: <gray>{message}");

    public static final Property<String> anti_swear_commands_message = newProperty("player.antiswear.commands.message", "{prefix}<red>Please do not curse in commands.");
    public static final Property<String> anti_swear_commands_notify_staff = newProperty("player.antiswear.commands.notify_staff_format", "<gray>[Anti-Swear Commands] <white>{player}: <gray>{message}");

    public static final Property<String> anti_swear_signs_message = newProperty("player.antiswear.signs.message", "{prefix}<red>Please do not curse on signs.");
    public static final Property<String> anti_swear_signs_notify_staff = newProperty("player.antiswear.signs.notify_staff_format", "<gray>[Anti-Swear Signs] <white>{player}: <gray>{message}");

    public static final Property<String> blacklist_added = newProperty("commands.antiswear.blacklist.added", "{prefix}<gray>You added the word <red>{word} <gray>to the anti swears blacklist.");
    public static final Property<String> blacklist_exists = newProperty("commands.antiswear.blacklist.exists", "{prefix}<gray>The word <red>{word} <gray>is already added to the anti swears blacklist.");
    public static final Property<String> blacklist_removed = newProperty("commands.antiswear.blacklist.removed", "{prefix}<gray>You removed the word <red>{word} <gray>from the anti swears blacklist.");
    public static final Property<String> blacklist_not_found = newProperty("commands.antiswear.blacklist.not_found", "{prefix}<gray>The word <red>{word} <gray>is not in the anti swears blacklist.");

    public static final Property<String> whitelist_added = newProperty("commands.antiswear.whitelist.added", "{prefix}<gray>You added the word <red>{word} <gray>to the anti swears whitelist.");
    public static final Property<String> whitelist_exists = newProperty("commands.antiswear.whitelist.exists", "{prefix}<gray>The word <red>{word} <gray>is already added to the anti swears whitelist.");
    public static final Property<String> whitelist_removed = newProperty("commands.antiswear.whitelist.removed", "{prefix}<gray>You removed the word <red>{word} <gray>from the anti swears whitelist.");
    public static final Property<String> whitelist_not_found = newProperty("commands.antiswear.whitelist.not_found", "{prefix}<gray>The word <red>{word} <gray>is not in the anti swears whitelist.");

    public static final Property<String> anti_unicode_message = newProperty("antiunicode.message", "{prefix}<red>Please do not use special characters in chat.");
    public static final Property<String> anti_unicode_notify_staff = newProperty("anti_unicode.notify_staff", "<gray>[Anti-Unicode] <white>{player}: <gray>{message}");

    public static final Property<String> auto_broadcast_list = newProperty("commands.autobroadcast.list", "<red>{section}'s <gray>auto-broadcast messages:");
    public static final Property<String> auto_broadcast_added = newProperty("commands.autobroadcast.added", "{prefix}<reset>{message} <gray>has been added to the <red>{section} <gray>messages<gray>!");
    public static final Property<String> auto_broadcast_created = newProperty("commands.autobroadcast.created", "{prefix}<gray>Created the world <red>'%world%' <gray>with the message <reset>{message}<gray>.");

    public static final Property<String> clear_chat_staff_message = newProperty("commands.clearchat.staff_message", "{prefix}<yellow>Chat has been cleared by {player}.");
    public static final ListProperty<String> clear_chat_broadcast_message = newListProperty("commands.clearchat.broadcast_message", List.of(
            "<white>*<red>--------------------------------------------<white>*",
            "<yellow>The chat has been cleared by {player}",
            "<white>*<red>--------------------------------------------<white>*"
    ));

    public static final Property<String> mute_chat_denied_message = newProperty("commands.mutechat.denied_message", "{prefix}<red>You are not able to talk in chat right now.");
    public static final Property<String> mute_chat_broadcast_enabled = newProperty("commands.mutechat.broadcast_messages.enabled", "{prefix}<green>Chat has been enabled by {player}.");
    public static final Property<String> mute_chat_broadcast_disabled = newProperty("commands.mutechat.broadcast_messages.disabled", "{prefix}<red>Chat has been disabled by {player}.");
    public static final Property<String> mute_chat_blocked_commands_message = newProperty("commands.mutechat.blocked_commands.message", "{prefix}<red>You are not allowed to use that command while the chat is muted.");

    public static final Property<String> per_world_chat_bypass_enabled = newProperty("commands.per_world_chat.bypass_enabled", "{prefix}<green>Per-world chat bypass has been enabled.");
    public static final Property<String> per_world_chat_bypass_disabled = newProperty("commands.per_world_chat.bypass_disabled", "{prefix}<red>Per-world chat bypass has been disabled.");

}