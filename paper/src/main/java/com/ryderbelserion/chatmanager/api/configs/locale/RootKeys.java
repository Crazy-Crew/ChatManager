package com.ryderbelserion.chatmanager.api.configs.locale;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import org.jetbrains.annotations.NotNull;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class RootKeys implements SettingsHolder {

    protected RootKeys() {}

    @Override
    public void registerComments(@NotNull CommentsConfiguration configuration) {
        configuration.setComment("root", """
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
    }

    private static final Property<String> message_prefix = newProperty("Messages.Prefix", "&b[&6ChatManager&b] &r");

    private static final Property<String> no_permission = newProperty("Messages.No_Permission", "{prefix}&cYou don't have the permissions to do this.");

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

}