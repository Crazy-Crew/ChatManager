package com.ryderbelserion.chatmanager.api.configs.locale;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class RootKeys implements SettingsHolder {

    protected RootKeys() {}

    @Override
    public void registerComments(@NotNull CommentsConfiguration configuration) {
        configuration.setComment("Messages", """
                =================================================================================================
                                                 Main Messages File of Chat Manager
                =================================================================================================
                    If you need any plugin support, feel free to join our discord server
                    Discord Link: https://discord.gg/badbones-s-live-chat-182615261403283459
                =================================================================================================
                 Information:
                   Color Codes are supported with the "&" character.
                    - To view a list of COLOR CODES you can do /Colors in-game.
                    - To view a list of FORMAT CODES you can do /Formats in-game.
                =================================================================================================
                """);

        configuration.setComment("Anti_Advertising", """
                =================================================================================================
                Anti-Advertising Messages
                =================================================================================================
                """);

        configuration.setComment("Anti_Bot", """
                =================================================================================================
                Anti-Bot Messages
                =================================================================================================
                """);

        configuration.setComment("Anti_Caps", """
                =================================================================================================
                Anti-Caps Message
                =================================================================================================
                """);

        configuration.setComment("Anti_Spam", """
                =================================================================================================
                Anti-Spam Messages
                =================================================================================================
                """);

        configuration.setComment("Anti_Swear", """
                =================================================================================================
                Anti-Swear Messages
                =================================================================================================
                """);

        configuration.setComment("Anti_Unicode:", """
                =================================================================================================
                Anti-Unicode Messages
                =================================================================================================
                """);

        configuration.setComment("Banned_Commands", """
                =================================================================================================
                Banned Commands Messages
                =================================================================================================
                """);

        configuration.setComment("Chat_Radius", """
                =================================================================================================
                Chat Radius Messages
                =================================================================================================
                """);

        configuration.setComment("Clear_Chat", """
                =================================================================================================
                Clear Chat Messages
                =================================================================================================
                """);

        configuration.setComment("Command_Spy", """
                =================================================================================================
                Command Spy Messages
                =================================================================================================
                """);

        configuration.setComment("Mute_Chat", """
                =================================================================================================
                Mute Chat Messages
                =================================================================================================
                """);

        configuration.setComment("Per_World_Chat", """
                =================================================================================================
                Per-World Chat Messages
                =================================================================================================
                """);

        configuration.setComment("Ping", """
                =================================================================================================
                Ping Messages
                =================================================================================================
                """);

        configuration.setComment("Private_Message", """
                =================================================================================================
                Private Message Messages
                =================================================================================================
                """);

        configuration.setComment("Social_Spy", """
                =================================================================================================
                Social Spy Messages
                =================================================================================================
                """);

        configuration.setComment("Staff_Chat", """
                =================================================================================================
                Staff Chat Messages
                =================================================================================================
                """);

        configuration.setComment("Toggle_Chat", """
                =================================================================================================
                Toggle Chat Messages
                =================================================================================================
                """);

        configuration.setComment("Toggle_Mentions", """
                =================================================================================================
                Toggle Mentions Messages
                =================================================================================================
                """);

        configuration.setComment("TogglePM", """
                =================================================================================================
                TogglePM Messages
                =================================================================================================
                """);
    }

    public static final Property<String> message_prefix = newProperty("Messages.Prefix", "&b[&6ChatManager&b] &r");

    public static final Property<String> no_permission = newProperty("Messages.No_Permission", "{prefix}&cYou don't have the permissions to do this.");

    public static final Property<String> player_not_found = newProperty("Messages.Player-Not-Found", "{prefix}&7The player &c{target} &7cannot be found.");

    public static final Property<String> reload = newProperty("Messages.Reload", "{prefix}&aConfig has been reloaded.");

    public static final Property<String> invalid_usage = newProperty("Messages.Invalid_Usage", "{prefix}&cThis is not a valid usage of the command, Correct usage &e{usage}.");

    public static final Property<String> anti_advertising_chat_message = newProperty("Anti_Advertising.Chat.Message", "{prefix}&cAdvertising is not allowed in chat. Staff has been notified.");

    public static final Property<String> anti_advertising_chat_notify_staff = newProperty("Anti_Advertising.Chat.Notify_Staff_Format", "&7[Anti-Advertise Chat] &f{player}: &7{message}");

    public static final Property<String> anti_advertising_commands_message = newProperty("Anti_Advertising.Commands.Message", "{prefix}&cAdvertising is not allowed in commands. Staff has been notified.");

    public static final Property<String> anti_advertising_commands_notify_staff = newProperty("Anti_Advertising.Commands.Notify_Staff_Format", "&7[Anti-Advertise Commands] &f{player}: &7{message}");

    public static final Property<String> anti_advertising_signs_message = newProperty("Anti_Advertising.Signs.Message", "{prefix}&cAdvertising is not allowed on signs. Staff has been notified.");

    public static final Property<String> anti_advertising_signs_notify_staff = newProperty("Anti_Advertising.Signs.Notify_Staff_Format", "&7[Anti-Advertise Signs] &f{player}: &7{message}");

    public static final Property<String> anti_bot_deny_chat_message = newProperty("Anti_Bot.Deny_Chat_Message", "{prefix}&cYou must move one block before talking in chat.");

    public static final Property<String> anti_bot_deny_command_message = newProperty("Anti_Bot.Deny_Command_Message", "{prefix}&cYou must move one block before executing commands.");

    public static final Property<String> anti_caps_message_chat = newProperty("Anti_Caps.Message_Chat", "{prefix}&cPlease do not use caps in chat.");

    public static final Property<String> anti_caps_message_commands = newProperty("Anti_Caps.Message_Commands", "{prefix}&cPlease do not use caps in commands.");

    public static final Property<String> anti_spam_chat_repetition = newProperty("Anti_Spam.Chat.Repetitive_Message", "{prefix}&cPlease do not repeat the same message.");

    public static final Property<String> anti_spam_chat_delay = newProperty("Anti_Spam.Chat.Delay_Message", "{prefix}&7Please wait &c{Time} seconds &7before sending another message.");

    public static final Property<String> anti_spam_command_repetition = newProperty("Anti_Spam.Command.Repetitive_Message", "{prefix}&cPlease do not repeat the same command.");

    public static final Property<String> anti_spam_command_delay = newProperty("Anti_Spam.Command.Delay_Message", "{prefix}&7Please wait &c{Time} seconds &7before sending another command.");

    public static final Property<String> anti_swear_chat_message = newProperty("Anti_Swear.Chat.Message", "{prefix}&cPlease do not curse in chat");

    public static final Property<String> anti_swear_chat_notify_staff = newProperty("Anti_Swear.Chat.Notify_staff_Format", "&7[Anti-Swear Chat] &f{player}: &7{message}");

    public static final Property<String> anti_swear_commands_message = newProperty("Anti_Swear.Commands.Message", "{prefix}&cPlease do not curse in commands.");

    public static final Property<String> anti_swear_commands_notify_staff = newProperty("Anti_Swear.Commands.Notify_Staff_Format", "&7[Anti-Swear Commands] &f{player}: &7{message}");

    public static final Property<String> anti_swear_signs_message = newProperty("Anti_Swear.Signs.Message", "{prefix}&cPlease do not curse on signs.");

    public static final Property<String> anti_swear_signs_notify_staff = newProperty("Anti_Swear.Signs.Notify_Staff_Format", "&7[Anti-Swear Signs] &f{player}: &7{message}");

    public static final Property<String> anti_unicode_message = newProperty("Anti_Unicode.Message", "{prefix}&cPlease do not use special characters in chat.");

    public static final Property<String> anti_unicode_notify_staff = newProperty("Anti_Unicode.Notify_Staff_Format", "&7[Anti-Unicode] &f{player}: &7{message}");

    public static final Property<String> banned_commands_message = newProperty("Banned_Commands.Message", "{prefix}&cYou do not have permission to use the command &7/{command}&c.");

    public static final Property<String> banned_commands_notify_staff = newProperty("Banned_Commands.Notify_Staff_Format", "&7[Blocked-Cmd] &f{player}: &7{command}");

    public static final Property<String> chat_radius_local_chat_enabled = newProperty("Chat_Radius.Local_Chat.Enabled", "{prefix}&7You've entered &cLocal Chat&7! Do &c/ChatRadius global &7or &c/ChatRadius world &7to leave Local Chat!");

    public static final Property<String> chat_radius_local_chat_already_enabled = newProperty("Chat_Radius.Local_Chat.Already_Enabled", "{prefix}&7You are already in local chat.");

    public static final Property<String> chat_radius_global_chat_enabled = newProperty("Chat_Radius.Global_Chat.Enabled", "{prefix}&7You've entered &cGlobal Chat&7! Do &c/ChatRadius local &7or &c/ChatRadius world &7to leave global Chat!");

    public static final Property<String> chat_radius_global_chat_already_enabled = newProperty("Chat_Radius.Global_Chat.Already_Enabled", "{prefix}&7You are already in global chat.");

    public static final Property<String> chat_radius_world_chat_enabled = newProperty("Chat_Radius.World_Chat.Enabled", "{prefix}&7You've entered &cWorld Chat&7! Do &c/ChatRadius local &7or &c/ChatRadius global &7to leave world Chat!");

    public static final Property<String> chat_radius_world_chat_already_enabled = newProperty("Chat_Radius.World_Chat.Already_Enabled", "{prefix}&7You are already in world chat.");

    public static final Property<String> chat_radius_spy_enabled = newProperty("Chat_Radius.Spy.Enabled", "{prefix}&7Chat Radius spy has been &aEnabled.");

    public static final Property<String> chat_radius_spy_disabled = newProperty("Chat_Radius.Spy.Disabled", "{prefix}&7Chat Radius spy has been &cDisabled.");

    public static final Property<String> clear_chat_staff_message = newProperty("Clear_Chat.Staff_Message", "{prefix}&eChat has been cleared by {player}.");

    public static final Property<List<String>> clear_chat_broadcast_message = newListProperty("Clear_Chat.Broadcast_Message", List.of(
            "&f*&c&m--------------------------------------------&f*",
            "&eThe chat has been cleared by {player}",
            "&f*&c&m--------------------------------------------&f*"
    ));

    public static final Property<String> command_spy_format = newProperty("Command_Spy.Format", "&7[Command-Spy] {player}: &b{command}");

    public static final Property<String> command_spy_enabled = newProperty("Command_Spy.Enabled", "{prefix}&aCommand Spy has been enabled.");

    public static final Property<String> command_spy_disabled = newProperty("Command_Spy.Disabled", "{prefix}&cCommand Spy has been disabled.");

    public static final Property<String> mute_chat_denied_message = newProperty("Mute_Chat.Denied_Message", "{prefix}&cYou are not able to talk in chat right now.");

    public static final Property<String> mute_chat_broadcast_message_enabled = newProperty("Mute_Chat.Broadcast_Messages.Enabled", "{prefix}&aChat has been Enabled by {player}.");

    public static final Property<String> mute_chat_broadcast_message_disabled = newProperty("Mute_Chat.Broadcast_Messages.Disabled", "{prefix}&cChat has been Disabled by {player}.");

    public static final Property<String> mute_chat_blocked_commands_message = newProperty("Mute_Chat.Blocked_Commands.Message", "{prefix}&cYou are not allowed to use that command while the chat is muted.");

    public static final Property<String> per_world_chat_bypass_enabled = newProperty("Per_World_Chat.Bypass_Enabled", "{prefix}&aPer-world chat bypass has been enabled.");

    public static final Property<String> per_world_chat_bypass_disabled = newProperty("Per_World_Chat.Bypass_Disabled", "{prefix}&cPer-world chat bypass has been disabled.");

    public static final Property<String> ping_players_ping = newProperty("Ping.Players_Ping", "{prefix}&7Your current ping is &c{ping} ms.");

    public static final Property<String> ping_targets_ping = newProperty("Ping.Targets_Ping", "{prefix}&7{target}'s current ping is &c{ping} ms.");

    public static final Property<String> private_message_recipient_not_found = newProperty("Private_Message.Recipient_Not_Found", "{prefix}&cYou have nobody to reply to.");

    public static final Property<String> private_message_toggled = newProperty("Private_Message.Toggled", "{prefix}&cThat player cannot receive messages right now.");

    public static final Property<String> private_message_ignored = newProperty("Private_Message.Ignored", "{prefix}&c{target} &7is currently ignoring you and cant receive any private messages.");

    public static final Property<String> private_message_self = newProperty("Private_Message.Self", "{prefix}&cYou cannot message yourself.");

    public static final Property<String> private_message_afk = newProperty("Private_Message.Afk", "{prefix}&c{target} &7is currently afk.");

    public static final Property<String> social_spy_format = newProperty("Social_Spy.Format", "&b&l(*)&bSpy &f&l[&e{player} &d-> &e{receiver}&f&l] &b{message}");

    public static final Property<String> social_spy_enabled = newProperty("Social_Spy.Enabled", "{prefix}&aSocial Spy has been enabled.");

    public static final Property<String> social_spy_disabled = newProperty("Social_Spy.Disabled", "{prefix}&cSocial Spy has been disabled.");

    public static final Property<String> staff_chat_enabled = newProperty("Staff_Chat.Enabled", "{prefix}&aStaff chat has been enabled.");

    public static final Property<String> staff_chat_disabled = newProperty("Staff_Chat.Disabled", "{prefix}&cStaff chat has been disabled.");

    public static final Property<String> toggle_chat_enabled = newProperty("Toggle_Chat.Enabled", "{prefix}&aToggle chat has been enabled, you will no longer receive chat messages.");

    public static final Property<String> toggle_chat_disabled = newProperty("Toggle_Chat.Disabled", "{prefix}&cToggle chat has been disabled, you will start receiving chat messages.");

    public static final Property<String> toggle_mentions_enabled = newProperty("Toggle_Mentions.Enabled", "{prefix}&7Toggle mentions has been &aenabled&7, you will no longer receive mention messages.");

    public static final Property<String> toggle_mentions_disabled = newProperty("Toggle_Mentions.Disabled", "{prefix}&7Toggle mentions has been &cdisabled&7, you will start receiving mention messages.");

    public static final Property<String> toggle_pm_enabled = newProperty("Toggle_PM.Enabled", "{prefix}&aTogglePM has been enabled.");

    public static final Property<String> toggle_pm_disabled = newProperty("Toggle_PM.Disabled", "{prefix}&cTogglePM has been disabled.");
}