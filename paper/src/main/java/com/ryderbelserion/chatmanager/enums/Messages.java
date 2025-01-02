package com.ryderbelserion.chatmanager.enums;

import me.clip.placeholderapi.PlaceholderAPI;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Messages {
    
    PREFIX("Message.Prefix", "&b[&6ChatManager&b] &r"),
    NO_PERMISSION("Message.No_Permission", "{prefix}&cYou don't have the permissions to do this."),
    PLAYER_NOT_FOUND("Message.Player-Not-Found", "{prefix}&7The player &c{target} &7cannot be found."),
    PLUGIN_RELOAD("Message.Reload", "{prefix}&aConfig has been reloaded."),

    ANTI_ADVERTISING_CHAT_MESSAGE("Anti_Advertising.Chat.Message", "{prefix}&cAdvertising is not allowed in chat. Staff has been notified."),
    ANTI_ADVERTISING_CHAT_NOTIFY_STAFF("Anti_Advertising.Chat.Notify_Staff_Format", "&7[Anti-Advertise Chat] &f{player}: &7{message}"),

    ANTI_ADVERTISING_COMMANDS_MESSAGE("Anti_Advertising.Commands.Message", "{prefix}&cAdvertising is not allowed in commands. Staff has been notified."),
    ANTI_ADVERTISING_COMMANDS_NOTIFY_STAFF("Anti_Advertising.Commands.Notify_Staff_Format", "&7[Anti-Advertise Commands] &f{player}: &7{message}"),

    ANTI_ADVERTISING_SIGNS_MESSAGE("Anti_Advertising.Signs.Message", "{prefix}&cAdvertising is not allowed on signs. Staff has been notified."),
    ANTI_ADVERTISING_SIGNS_NOTIFY_STAFF("Anti_Advertising.Signs.Notify_Staff_Format", "&7[Anti-Advertise Signs] &f{player}: &7{message}"),

    ANTI_BOT_DENY_CHAT_MESSAGE("Anti_Bot.Deny_Chat_Message", "{prefix}&cYou must move one block before talking in chat."),
    ANTI_BOT_DENY_COMMAND_MESSAGE("Anti_Bot.Deny_Command_Message", "{prefix}&cYou must move one block before executing commands."),

    ANTI_CAPS_MESSAGE_CHAT("Anti_Caps.Message_Chat", "{prefix}&cPlease do not use caps in chat."),
    ANTI_CAPS_MESSAGE_COMMANDS("Anti_Caps.Message_Commands", "{prefix}&cPlease do not use caps in commands."),

    ANTI_SPAM_CHAT_REPETITIVE_MESSAGE("Anti_Spam.Chat.Repetitive_Message", "{prefix}&cPlease do not repeat the same message."),
    ANTI_SPAM_CHAT_DELAY_MESSAGE("Anti_Spam.Chat.Delay_Message", "{prefix}&7Please wait &c{Time} seconds &7before sending another message."),

    ANTI_SPAM_COMMAND_REPETITIVE_MESSAGE("Anti_Spam.Command.Repetitive_Message", "{prefix}&cPlease do not repeat the same command."),
    ANTI_SPAM_COMMAND_DELAY_MESSAGE("Anti_Spam.Command.Delay_Message", "{prefix}&7Please wait &c{Time} seconds &7before sending another command."),

    ANTI_SWEAR_CHAT_MESSAGE("Anti_Swear.Chat.Message", "{prefix}&cPlease do not curse in chat."),
    ANTI_SWEAR_CHAT_NOTIFY_STAFF_FORMAT("Anti_Swear.Chat.Notify_Staff_Format", "&7[Anti-Swear Chat] &f{player}: &7{message}"),

    ANTI_SWEAR_COMMAND_MESSAGE("Anti_Swear.Commands.Message", "{prefix}&cPlease do not curse in commands."),
    ANTI_SWEAR_COMMAND_NOTIFY_STAFF_FORMAT("Anti_Swear.Commands.Notify_Staff_Format", "&7[Anti-Swear Commands] &f{player}: &7{message}"),

    ANTI_SWEAR_SIGNS_MESSAGE("Anti_Swear.Signs.Message", "{prefix}&cPlease do not curse on signs."),
    ANTI_SWEAR_SIGNS_NOTIFY_STAFF_FORMAT("Anti_Swear.Signs.Notify_Staff_Format", "&7[Anti-Swear Signs] &f{player}: &7{message}"),

    ANTI_SWEAR_BLACKLISTED_WORD_ADDED("Anti_Swear.Blacklisted_Word.Added", "{prefix}&7You added the word &c{word} &7to the anti swears blacklist."),
    ANTI_SWEAR_BLACKLISTED_WORD_EXISTS("Anti_Swear.Blacklisted_Word.Exists", "{prefix}&7The word &c{word} &7is already added to the anti swears blacklist."),
    ANTI_SWEAR_BLACKLISTED_WORD_REMOVED("Anti_Swear.Blacklisted_Word.Removed", "{prefix}&7You removed the word &c{word} &7from the anti swears blacklist."),
    ANTI_SWEAR_BLACKLISTED_WORD_NOT_FOUND("Anti_Swear.Blacklisted_Word.Not_Found", "{prefix}&7The word &c{word} &7is not in the anti swears blacklist."),

    ANTI_SWEAR_WHITELISTED_WORD_ADDED("Anti_Swear.Whitelisted_Word.Added", "{prefix}&7You added the word &c{word} &7to the anti swears whitelist."),
    ANTI_SWEAR_WHITELISTED_WORD_EXISTS("Anti_Swear.Whitelisted_Word.Exists", "{prefix}&7The word &c{word} &7is already added to the anti swears whitelist."),
    ANTI_SWEAR_WHITELISTED_WORD_REMOVED("Anti_Swear.Whitelisted_Word.Removed", "{prefix}&7You removed the word &c{word} &7from the anti swears whitelist."),
    ANTI_SWEAR_WHITELISTED_WORD_NOT_FOUND("Anti_Swear.Whitelisted_Word.Not_Found", "{prefix}&7The word &c{word} &7is not in the anti swears whitelist."),

    ANTI_UNICODE_MESSAGE("Anti_Unicode.Message", "{prefix}&cPlease do not use special characters in chat."),
    ANTI_UNICODE_NOTIFY_STAFF_FORMAT("Anti_Unicode.Notify_Staff_Format", "&7[Anti-Unicode] &f{player}: &7{message}"),

    AUTO_BROADCAST_LIST("Auto_Broadcast.List", "&c{section}'s &7auto-broadcast messages:"),
    AUTO_BROADCAST_ADDED("Auto_Broadcast.Added", "{prefix}&r{message} &7has been added to the &c{section} &7messages&7!"),
    AUTO_BROADCAST_CREATED("Auto_Broadcast.Created", "{prefix}&7Created the world &c'%world%' &7with the message &r{message}&7."),

    BANNED_COMMANDS_MESSAGE("Banned_Commands.Message", "{prefix}&cYou do not have permission to use the command &7/{command}&c."),
    BANNED_COMMANDS_ADDED("Banned_Commands.Command_Added", "{prefix}&7You added the command &c{command} &7to the list of banned commands."),
    BANNED_COMMANDS_EXISTS("Banned_Commands.Command_Exists", "{prefix}&7The command &c{command} &7is already added to the list of banned commands."),
    BANNED_COMMANDS_REMOVED("Banned_Commands.Command_Removed", "{prefix}&7You removed the command &c{command} &7from the list of banned commands."),
    BANNED_COMMANDS_NOT_FOUND("Banned_Commands.Command_Not_Found", "{prefix}&7The command &c{command} &7is not in the list of banned commands."),
    BANNED_COMMANDS_NOTIFY_STAFF_FORMAT("Banned_Commands.Notify_Staff_Format", "&7[Blocked-Cmd] &f{player}: &7{command}"),

    CHAT_RADIUS_LOCAL_CHAT_ENABLED("Chat_Radius.Local_Chat.Enabled", "{prefix}&7You've entered &cLocal Chat&7! Do &c/ChatRadius global &7or &c/ChatRadius world &7to leave Local Chat!"),
    CHAT_RADIUS_LOCAL_CHAT_ALREADY_ENABLED("Chat_Radius.Local_Chat.Already_Enabled", "{prefix}&7You are already in local chat."),

    CHAT_RADIUS_GLOBAL_CHAT_ENABLED("Chat_Radius.Global_Chat.Enabled", "{prefix}&7You've entered &cGlobal Chat&7! Do &c/ChatRadius local &7or &c/ChatRadius world &7to leave global Chat!"),
    CHAT_RADIUS_GLOBAL_CHAT_ALREADY_ENABLED("Chat_Radius.Global_Chat.Already_Enabled", "{prefix}&7You are already in global chat."),

    CHAT_RADIUS_WORLD_CHAT_ENABLED("Chat_Radius.World_Chat.Enabled", "{prefix}&7You've entered &cWorld Chat&7! Do &c/ChatRadius local &7or &c/ChatRadius global &7to leave world Chat!"),
    CHAT_RADIUS_WORLD_CHAT_ALREADY_ENABLED("Chat_Radius.World_Chat.Already_Enabled", "{prefix}&7You are already in world chat."),

    CHAT_RADIUS_SPY_ENABLED("Chat_Radius.Spy.Enabled", "{prefix}&7Chat Radius spy has been &aEnabled."),
    CHAT_RADIUS_SPY_DISABLED("Chat_Radius.Spy.Disabled", "{prefix}&7Chat Radius spy has been &cDisabled."),

    CLEAR_CHAT_STAFF_MESSAGE("Clear_Chat.Staff_Message", "{prefix}&eChat has been cleared by {player}."),
    CLEAR_CHAT_BROADCAST_MESSAGE("Clear_Chat.Broadcast_Message", List.of(
            "&f*&c&m--------------------------------------------&f*",
            "&eThe chat has been cleared by {player}",
            "&f*&c&m--------------------------------------------&f*"
    )),

    COMMAND_SPY_FORMAT("Command_Spy.Format", "&7[Command-Spy] {player}: &b{command}"),
    COMMAND_SPY_ENABLED("Command_Spy.Enabled", "{prefix}&aCommand Spy has been enabled."),
    COMMAND_SPY_DISABLED("Command_Spy.Disabled", "{prefix}&cCommand Spy has been disabled."),

    MUTE_CHAT_DENIED_MESSAGE("Mute_Chat.Denied_Message", "{prefix}&cYou are not able to talk in chat right now."),
    MUTE_CHAT_BROADCAST_MESSAGES_ENABLED("Mute_Chat.Broadcast_Messages.Enabled", "{prefix}&aChat has been Enabled by {player}."),
    MUTE_CHAT_BROADCAST_MESSAGES_DISABLED("Mute_Chat.Broadcast_Messages.Disabled", "{prefix}&cChat has been Disabled by {player}."),
    MUTE_CHAT_BLOCKED_COMMANDS_MESSAGE("Mute_Chat.Blocked_Commands.Message", "{prefix}&cYou are not allowed to use that command while the chat is muted."),

    PER_WORLD_CHAT_BYPASS_ENABLED("Per_World_Chat.Bypass_Enabled", "{prefix}&aPer-world chat bypass has been enabled."),
    PER_WORLD_CHAT_BYPASS_DISABLED("Per_World_Chat.Bypass_Disabled", "{prefix}&cPer-world chat bypass has been disabled."),

    PING_PLAYERS_PING("Ping.Players_Ping", "{prefix}&7Your current ping is &c{ping} ms."),
    PING_TARGETS_PING("Ping.Targets_Ping", "{prefix}&7{target}'s current ping is &c{ping} ms."),

    PRIVATE_MESSAGE_RECIPIENT_NOT_FOUND("Private_Message.Recipient_Not_Found", "{prefix}&cYou have nobody to reply to."),
    PRIVATE_MESSAGE_TOGGLED("Private_Message.Toggled", "{prefix}&cThat player cannot receive messages right now."),
    PRIVATE_MESSAGE_IGNORED("Private_Message.Ignored", "{prefix}&c{target} &7is currently ignoring you and cant receive any private messages."),
    PRIVATE_MESSAGE_SELF("Private_Message.Self", "{prefix}&cYou cannot message yourself."),
    PRIVATE_MESSAGE_AFK("Private_Message.Afk", "{prefix}&c{target} &7is currently afk."),

    SOCIAL_SPY_FORMAT("Social_Spy.Format", "&b&l(*)&bSpy &f&l[&e{player} &d-> &e{receiver}&f&l] &b{message}"),
    SOCIAL_SPY_ENABLED("Social_Spy.Enabled", "{prefix}&aSocial Spy has been enabled."),
    SOCIAL_SPY_DISABLED("Social_Spy.Disabled", "{prefix}&cSocial Spy has been disabled."),

    STAFF_CHAT_ENABLED("Staff_Chat.Enabled", "{prefix}&aStaff Chat has been enabled."),
    STAFF_CHAT_DISABLED("Staff_Chat.Disabled", "{prefix}&cStaff Chat has been disabled."),

    TOGGLE_CHAT_ENABLED("Toggle_Chat.Enabled", "{prefix}&aToggle chat has been enabled, you will no longer receive chat messages."),
    TOGGLE_CHAT_DISABLED("Toggle_Chat.Disabled", "{prefix}&cToggle chat has been disabled, you will start receiving chat messages."),

    TOGGLE_MENTIONS_ENABLED("Toggle_Mentions.Enabled", "{prefix}&7Toggle mentions has been &aenabled&7, you will no longer receive mention messages."),
    TOGGLE_MENTIONS_DISABLED("Toggle_Mentions.Disabled", "{prefix}&7Toggle mentions has been &cdisabled&7, you will start receiving mention messages."),

    TOGGLE_PM_ENABLED("TogglePM.Enabled", "{prefix}&aTogglePM has been enabled."),
    TOGGLE_PM_DISABLED("TogglePM.Disabled", "{prefix}&cTogglePM has been disabled.");

    private final String path;
    private String defaultMessage;
    private List<String> defaultListMessage;
    
    Messages(String path, String defaultMessage) {
        this.path = path;
        this.defaultMessage = defaultMessage;
    }
    
    Messages(String path, List<String> defaultListMessage) {
        this.path = path;
        this.defaultListMessage = defaultListMessage;
    }

    public static String convertList(final List<String> list) {
        StringBuilder message = new StringBuilder();

        for (String m : list) {
            message.append(Methods.color(m)).append("\n");
        }

        return message.toString();
    }

    public static String convertList(final List<String> list, final Map<String, String> placeholders) {
        String message = convertList(list);

        for (String ph : placeholders.keySet()) {
            message = Methods.color(message.replace(ph, placeholders.get(ph))).replace(ph, placeholders.get(ph).toLowerCase());
        }

        return message;
    }

    public String getMessage(final CommandSender sender) {
        return getMessage(sender, new HashMap<>());
    }

    public String getMessage(@Nullable final CommandSender sender, @NotNull final String placeholder, @NotNull final String replacement) {
        Map<String, String> placeholders = new HashMap<>() {{
            put(placeholder, replacement);
            put("{prefix}", Methods.getPrefix());
            put("{Prefix}", Methods.getPrefix());
        }};

        return getMessage(sender, placeholders);
    }

    public String getMessage(final CommandSender sender, final Map<String, String> placeholders) {
        String message;

        if (isList()) {
            if (exists()) {
                message = Methods.color(convertList(Files.MESSAGES.getConfiguration().getStringList(this.path), placeholders));
            } else {
                message = Methods.color(convertList(getDefaultListMessage(), placeholders));
            }
        } else {
            if (exists()) {
                message = Methods.color(Files.MESSAGES.getConfiguration().getString(this.path));
            } else {
                message = Methods.color(getDefaultMessage());
            }

            message = message.replaceAll("\\{prefix}", Methods.getPrefix()).replaceAll( "\\{Prefix}", Methods.getPrefix());

            if (sender instanceof Player player) {
                if (PluginSupport.PLACEHOLDERAPI.isPluginEnabled()) {
                    message = PlaceholderAPI.setPlaceholders(player, message);
                }
            }

            for (String ph : placeholders.keySet()) {
                if (message.contains(ph)) {
                    message = message.replace(ph, placeholders.get(ph)).replace(ph, placeholders.get(ph).toLowerCase());
                }
            }
        }

        return message;
    }

    public void sendMessage(final CommandSender sender, final String placeholder, final String replacement) {
        final String message = getMessage(sender, placeholder, replacement);

        if (message.isBlank()) return;

        sender.sendMessage(message);
    }

    public void sendMessage(final CommandSender sender, final Map<String, String> placeholders) {
        final String message = getMessage(sender, placeholders);

        if (message.isBlank()) return;

        sender.sendMessage(message);
    }

    public void sendMessage(final CommandSender sender) {
        sender.sendMessage(getMessage(sender));
    }

    private boolean exists() {
        return Files.MESSAGES.getConfiguration().contains(this.path);
    }

    private boolean isList() {
        final YamlConfiguration configuration = Files.MESSAGES.getConfiguration();

        if (configuration.contains(this.path)) {
            return !configuration.getStringList(this.path).isEmpty();
        } else {
            return this.defaultMessage == null;
        }
    }

    private String getPath() {
        return this.path;
    }

    private String getDefaultMessage() {
        return this.defaultMessage;
    }

    private List<String> getDefaultListMessage() {
        return this.defaultListMessage;
    }
}