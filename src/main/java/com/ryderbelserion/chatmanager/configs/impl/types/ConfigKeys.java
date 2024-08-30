package com.ryderbelserion.chatmanager.configs.impl.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.ListProperty;
import ch.jalu.configme.properties.Property;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigKeys implements SettingsHolder {

    @Override
    public void registerComments(@NotNull CommentsConfiguration conf) {
        conf.setComment("Root", """
                =================================================================================================#
                                                 Main Configuration of Chat Manager
                =================================================================================================#
                
                    If you need any plugin support, feel free to join our discord server
                
                    Discord Link: https://discord.gg/mh7Ydaf
                
                =================================================================================================#
                
                 Information:
                   1. Migrate existing configurations to https://toolbox.helpch.at/
                
                   2. https://webui.advntr.dev/
                
                   3. Sound List
                      - 1.21.1 and newer: https://minecraft.wiki/w/Sounds.json#Java_Edition_values
                
                   4. All messages can be changed in the messages.yml file.
                
                   5. Wiki: https://docs.crazycrew.us/docs/plugins/chatmanager
                
                =================================================================================================#
                """);

        conf.setComment("Anti_Advertising", """
                =================================================================================================#
                 Block players from advertising other servers.
                =================================================================================================#
                """);

        conf.setComment("Anti_Bot", """
                =================================================================================================#
                 Prevent players from executing commands, or typing in chat until they move when they join your server.
                =================================================================================================#
                """);

        conf.setComment("Anti_Caps", """
                =================================================================================================#
                 Prevent messages written in CAPS.
                =================================================================================================#
                """);

        conf.setComment("Anti_Spam", """
                =================================================================================================#
                 Prevent the chat from being flooded with repetitive messages.
                =================================================================================================#
                """);

        conf.setComment("Anti_Swear", """
                =================================================================================================#
                 Prevent players from executing commands, or typing in chat until they move when they join your server.
                =================================================================================================#
                """);

        conf.setComment("Anti_Unicode", """
                =================================================================================================#
                 Prevent players from swearing in chat, comamnds, and signs.
                =================================================================================================#
                """);

        conf.setComment("Banned_Commands", """
                =================================================================================================#
                 Prevent players from swearing in chat, comamnds, and signs.
                =================================================================================================#
                """);

        conf.setComment("Broadcast_Commands", """
                =================================================================================================#
                 Prevent players from swearing in chat, comamnds, and signs.
                =================================================================================================#
                """);

        // per group chat formats

        conf.setComment("Chat_Radius", """
                =================================================================================================#
                 Players can only send messages to other players that are in range.
                 How to use chat radius: https://docs.crazycrew.us/docs/plugins/chatmanager/guides/chat/radius
                =================================================================================================#
                """);

        conf.setComment("Clear_Chat", """
                =================================================================================================#
                 Clear Chat
                =================================================================================================#
                """);

        conf.setComment("Command_Spy", """
                =================================================================================================#
                 Command Spy
                =================================================================================================#
                """);

        conf.setComment("Anti_Unicode", """
                #=================================================================================================#
                # Formatted Messages
                # This section allows you to enable or disable the use of color codes and format codes in chat.
                # Each color code and format is permission based.
                # To give players permission to use color codes give them\s
                # chatmanager.color.all or chatmanager.color.{code} without the &. Example: chatmanager.color.a
                # To give players permission to use format codes give them\s
                # chatmanager.format.all or chatmanager.format.{code} without the &. Example: chatmanager.color.f
                #=================================================================================================#
                """);

        conf.setComment("Lists", """
                #=================================================================================================#
                # Staff and Player List:
                #=================================================================================================#
                """);

        conf.setComment("Logs", """
                #=================================================================================================#
                # Logs
                #=================================================================================================#
                """);

        // per group formats

        conf.setComment("Mentions", """
                #=================================================================================================#
                # When a player mentions another players name.
                #=================================================================================================#
                """);

        conf.setComment("MOTD", """
                #=================================================================================================#
                # When players join your server, welcome them with a Message of the Day.
                #=================================================================================================#
                """);

        conf.setComment("Mute_Chat", """
                #=================================================================================================#
                # Mute Chat.
                #=================================================================================================#
                """);

        // per world chat

        conf.setComment("Private_Messages", """
                #=================================================================================================#
                # Private messages allow you to secretly message another player without everyone else seeing.
                #=================================================================================================#
                """);

        conf.setComment("Server_Name", """
                #=================================================================================================#
                # The server name used in {server_name} variable.
                #=================================================================================================#
                """);

        conf.setComment("Social_Spy", """
                #=================================================================================================#
                # Staff can see what players type in certain commands.
                #=================================================================================================#
                """);

        conf.setComment("Staff_Chat", """
                #=================================================================================================#
                # Talk silently with staff without everyone else on the server seeing.
                #=================================================================================================#
                """);
    }

    @Comment("The prefix in front of the commands")
    public static final Property<String> prefix = newProperty("root.prefix", "<aqua>[<gold>ChatManager<aqua>] <reset>");

    @Comment("Block advertising in chat.")
    public static final Property<Boolean> anti_advertising_chat = newProperty("Anti_Advertising.Chat.Enable", false);

    @Comment("Should the anti advertising checker be more sensitive? This may cause false positives.")
    public static final Property<Boolean> anti_advertising_chat_sensitivity = newProperty("Anti_Advertising.Chat.Increase_Sensitivity", false);

    @Comment("Should staff get notified when a player advertises in chat?")
    public static final Property<Boolean> anti_advertising_chat_notify_staff = newProperty("Anti_Advertising.Chat.Notify_Staff", false);

    @Comment("Should a command be executed to the player that advertises?")
    public static final Property<Boolean> anti_advertising_chat_execute_command = newProperty("Anti_Advertising.Chat.Execute_Command", false);

    @Comment({
            "The command that is executed when a player advertises.",
            "Set this section to Executed_Command: '' to disable"
    })
    public static final Property<String> anti_advertising_chat_executed_command = newProperty("Anti_Advertising.Chat.Executed_Command", "kick {player} Please do not advertise in chat");

    @Comment("Every time a player advertises in chat, the message will be logged in the advertisements.log file.")
    public static final Property<Boolean> anti_advertising_chat_log_to_file = newProperty("Anti_Advertising.Chat.Log_Advertisers", false);

    @Comment("Block advertising in commands.")
    public static final Property<Boolean> anti_advertising_commands = newProperty("Anti_Advertising.Commands.Enable", false);

    @Comment("Should the anti advertising checker be more sensitive? This may cause false positives.")
    public static final Property<Boolean> anti_advertising_commands_sensitivity = newProperty("Anti_Advertising.Commands.Increase_Sensitivity", false);

    @Comment("Should staff get notified when a player advertises in commands?")
    public static final Property<Boolean> anti_advertising_commands_notify_staff = newProperty("Anti_Advertising.Commands.Notify_Staff", true);

    @Comment("Should a command be executed to the player that advertises?")
    public static final Property<Boolean> anti_advertising_commands_execute_command = newProperty("Anti_Advertising.Commands.Execute_Command", false);

    @Comment({
            "The command that is executed when a player advertises.",
            "Set this section to Executed_Command: '' to disable"
    })
    public static final Property<String> anti_advertising_commands_executed_command = newProperty("Anti_Advertising.Commands.Executed_Command", "kick {player} Please do not advertise in chat");

    @Comment("Every time a player advertises in commands, their message will be logged in the advertisements.log file.")
    public static final Property<Boolean> anti_advertising_commands_log_to_file = newProperty("Anti_Advertising.Commands.Log_Advertisers", true);

    @Comment("If a player executes one of these commands with an advertisement in it, the command will not be blocked.")
    public static final ListProperty<String> anti_advertising_commands_whitelist = newListProperty("Anti_Advertising.Commands.Whitelist", List.of("/report"));

    @Comment("Block advertising on signs.")
    public static final Property<Boolean> anti_advertising_signs = newProperty("Anti_Advertising.Signs.Enable", false);

    @Comment("Should the anti advertising checker be more sensitive? This may cause false positives.")
    public static final Property<Boolean> anti_advertising_signs_sensitivity = newProperty("Anti_Advertising.Signs.Increase_Sensitivity", false);

    @Comment("Should staff get notified when a player advertises on a sign?")
    public static final Property<Boolean> anti_advertising_signs_notify_staff = newProperty("Anti_Advertising.Signs.Notify_Staff", true);

    @Comment("Should a command be executed to the player that advertises?")
    public static final Property<Boolean> anti_advertising_signs_execute_command = newProperty("Anti_Advertising.Signs.Execute_Command", false);

    @Comment({
            "The command that is executed when a player advertises.",
            "Set this section to Executed_Command: '' to disable"
    })
    public static final Property<String> anti_advertising_signs_executed_command = newProperty("Anti_Advertising.Signs.Executed_Command", "kick {player} Please do not advertise in chat");

    @Comment("Every time a player advertises on signs, their message will be logged in the advertisements.log file.")
    public static final Property<Boolean> anti_advertising_signs_log_to_file = newProperty("Anti_Advertising.Signs.Log_Advertisers", true);

    @Comment("Websites that won't be blocked if said in chat, commands, or signs.")
    public static final ListProperty<String> anti_advertising_whitelist = newListProperty("Anti_Advertising.Whitelist", List.of("google.com"));

    // Anti-Bot
    @Comment("Block players from typing in chat until they move.")
    public static final Property<Boolean> anti_bot_block_chat_until_moved = newProperty("Anti_Bot.Block_Chat_Until_Moved", false);

    @Comment("Block players from executing commands until they move.")
    public static final Property<Boolean> anti_bot_block_commands_until_moved = newProperty("Anti_Bot.Block_Commands_Until_Moved", false);

    // Anti-Caps
    @Comment("Enable anti-caps.")
    public static final Property<Boolean> anti_caps_enable = newProperty("Anti_Caps.Enable", false);

    @Comment("Should players capitalize words in commands?")
    public static final Property<Boolean> anti_caps_enable_in_commands = newProperty("Anti_Caps.Enable_In_Commands", true);

    @Comment("The minimum size the message has to be to get blocked.")
    public static final Property<Integer> anti_caps_min_message_length = newProperty("Anti_Caps.Min_Message_Length", 5);

    @Comment("The percentage the message has to be capitalized to get blocked.")
    public static final Property<Integer> anti_caps_required_percentage = newProperty("Anti_Caps.Required_Percentage", 70);

    // Anti-Spam (Chat)
    @Comment("How many seconds does the player have to wait till they send their next message? Set to 0 to disable.")
    public static final Property<Integer> anti_spam_chat_delay = newProperty("Anti_Spam.Chat.Chat_Delay", 3);

    @Comment("Prevent players from repeating the same messages.")
    public static final Property<Boolean> anti_spam_chat_block_repetitive_messages = newProperty("Anti_Spam.Chat.Block_Repetitive_Messages", false);

    // Anti-Spam (Command)
    @Comment("How many seconds does the player have to wait till they send their next command? Set to 0 to disable.")
    public static final Property<Integer> anti_spam_command_delay = newProperty("Anti_Spam.Command.Command_Delay", 3);

    @Comment("Prevent players from repeating the same commands.")
    public static final Property<Boolean> anti_spam_command_block_repetitive_commands = newProperty("Anti_Spam.Command.Block_Repetitive_Commands", false);

    @Comment("Whitelisted commands that won't be affected by the anti-spam.")
    public static final ListProperty<String> anti_spam_command_whitelist = newListProperty("Anti_Spam.Command.Whitelist", List.of("/spawn"));

    // Anti-Swear (Chat)
    @Comment("Block swearing in chat.")
    public static final Property<Boolean> anti_swear_chat_enable = newProperty("Anti_Swear.Chat.Enable", false);

    @Comment("Should the anti-swear checker be more sensitive? This may cause false positives.")
    public static final Property<Boolean> anti_swear_chat_increase_sensitivity = newProperty("Anti_Swear.Chat.Increase_Sensitivity", false);

    @Comment("Should chat manager block the message from being sent in chat?")
    public static final Property<Boolean> anti_swear_chat_block_message = newProperty("Anti_Swear.Chat.Block_Message", true);

    @Comment("Should staff get notified when a player swears in chat?")
    public static final Property<Boolean> anti_swear_chat_notify_staff = newProperty("Anti_Swear.Chat.Notify_Staff", true);

    @Comment("Should a command be executed when a player swears in chat?")
    public static final Property<Boolean> anti_swear_chat_execute_command = newProperty("Anti_Swear.Chat.Execute_Command", true);

    @Comment({
            "The command that is executed when a player swears in chat.",
            "Set this section to Executed_Command: '' to disable"
    })
    public static final ListProperty<String> anti_swear_chat_executed_command = newListProperty("Anti_Swear.Chat.Executed_Command", List.of("kick {player} Do not swear in chat", "warn {player} Do not swear in chat"));

    @Comment("Every time a player swears in chat their message will be logged in the swears.log file.")
    public static final Property<Boolean> anti_swear_chat_log_swearing = newProperty("Anti_Swear.Chat.Log_Swearing", false);

    // Anti-Swear (Commands)
    @Comment("Block swearing in commands.")
    public static final Property<Boolean> anti_swear_commands_enable = newProperty("Anti_Swear.Commands.Enable", false);

    @Comment("Should the anti-swear checker be more sensitive? This may cause false positives.")
    public static final Property<Boolean> anti_swear_commands_increase_sensitivity = newProperty("Anti_Swear.Commands.Increase_Sensitivity", false);

    @Comment("Should chat manager block the command from being sent?")
    public static final Property<Boolean> anti_swear_commands_block_command = newProperty("Anti_Swear.Commands.Block_Command", true);

    @Comment("If a player executes one of these commands with a swear word in it, the command will not be blocked.")
    public static final ListProperty<String> anti_swear_commands_whitelisted_commands = newListProperty("Anti_Swear.Commands.Whitelisted_Commands", List.of("/report", "/login", "/register"));

    @Comment("Should staff get notified when a player swears in commands?")
    public static final Property<Boolean> anti_swear_commands_notify_staff = newProperty("Anti_Swear.Commands.Notify_Staff", true);

    @Comment("Should a command be executed when a player swears in commands?")
    public static final Property<Boolean> anti_swear_commands_execute_command = newProperty("Anti_Swear.Commands.Execute_Command", true);

    @Comment({
            "The command that is executed when a player swears in commands.",
            "Set this section to Executed_Command: '' to disable"
    })
    public static final ListProperty<String> anti_swear_commands_executed_command = newListProperty("Anti_Swear.Commands.Executed_Command", List.of("kick {player} Do not swear in chat", "warn {player} Do not swear in chat"));

    @Comment("Every time a player swears in commands their message will be logged in the swears.log file.")
    public static final Property<Boolean> anti_swear_commands_log_swearing = newProperty("Anti_Swear.Commands.Log_Swearing", false);

    // Anti-Swear (Signs)
    @Comment("Block swearing on signs.")
    public static final Property<Boolean> anti_swear_signs_enable = newProperty("Anti_Swear.Signs.Enable", false);

    @Comment("Should the anti-swear checker be more sensitive? This may cause false positives.")
    public static final Property<Boolean> anti_swear_signs_increase_sensitivity = newProperty("Anti_Swear.Signs.Increase_Sensitivity", false);

    @Comment("Should chat manager block the message from being added to the sign?")
    public static final Property<Boolean> anti_swear_signs_block_sign = newProperty("Anti_Swear.Signs.Block_Sign", true);

    @Comment("Should staff get notified when a player swears on signs?")
    public static final Property<Boolean> anti_swear_signs_notify_staff = newProperty("Anti_Swear.Signs.Notify_Staff", true);

    @Comment("Should a command be executed when a player swears on signs?")
    public static final Property<Boolean> anti_swear_signs_execute_command = newProperty("Anti_Swear.Signs.Execute_Command", true);

    @Comment({
            "The command that is executed when a player swears on signs.",
            "Set this section to Executed_Command: '' to disable"
    })
    public static final ListProperty<String> anti_swear_signs_executed_command = newListProperty("Anti_Swear.Signs.Executed_Command", List.of("kick {player} Do not swear in chat", "warn {player} Do not swear in chat"));

    @Comment("Every time a player swears on signs their message will be logged in the swears.log file.")
    public static final Property<Boolean> anti_swear_signs_log_swearing = newProperty("Anti_Swear.Signs.Log_Swearing", false);

    @Comment("Block special characters in chat.")
    public static final Property<Boolean> anti_unicode_enable = newProperty("Anti_Unicode.Enable", false);

    @Comment("Should staff get notified when a player uses special characters?")
    public static final Property<Boolean> anti_unicode_notify_staff = newProperty("Anti_Unicode.Notify_Staff", true);

    @Comment("Should a command be executed when a player uses special characters?")
    public static final Property<Boolean> anti_unicode_execute_command = newProperty("Anti_Unicode.Execute_Command", false);

    @Comment({
            "The command that is executed when a player uses special characters in chat.",
            "Set this section to Executed_Command: '' to disable"
    })
    public static final Property<String> anti_unicode_executed_command = newProperty("Anti_Unicode.Executed_Command", "kick {player} Please do not use special characters in chat.");

    @Comment("Anything that's in this list won’t be blocked by the anti unicode checker.")
    public static final ListProperty<String> anti_unicode_whitelist = newListProperty("Anti_Unicode.Whitelist", List.of("«", "»"));

    @Comment("Enable banned commands.")
    public static final Property<Boolean> banned_commands_enable = newProperty("Banned_Commands.Enable", false);

    @Comment("Increase banned commands sensitivity so if you add the command /gamemode, players won't be able to do /gamemode creative, /gamemode survival, etc.")
    public static final Property<Boolean> banned_commands_increase_sensitivity = newProperty("Banned_Commands.Increase_Sensitivity", false);

    @Comment("Should staff get notified when a player uses a banned command?")
    public static final Property<Boolean> banned_commands_notify_staff = newProperty("Banned_Commands.Notify_Staff", true);

    @Comment("Should a command be executed to the player that uses a banned command?")
    public static final Property<Boolean> banned_commands_execute_command = newProperty("Banned_Commands.Execute_Command", true);

    @Comment({
            "The command that is executed when a player executes a banned command.",
            "Set this section to Executed_Command: '' to disable"
    })
    public static final Property<String> banned_commands_executed_command = newProperty("Banned_Commands.Executed_Command", "kick {player} You are not allowed to use that command!");

    // Broadcast Command
    @Comment("The default prefix that goes in front of the broadcasted message.")
    public static final Property<String> broadcast_command_prefix = newProperty("Broadcast_Commands.Command.Broadcast.Prefix", "&c[&4Broadcast&c]&r ");

    @Comment("The default color the broadcasted message will be.")
    public static final Property<String> broadcast_command_default_color = newProperty("Broadcast_Commands.Command.Broadcast.Default_Color", "&b");

    @Comment("The sound that will be sent to everyone on the server when a broadcasted message is sent. Set toggle to false")
    public static final Property<Boolean> broadcast_command_sound_toggle = newProperty("Broadcast_Commands.Command.Broadcast.sound.toggle", false);

    public static final Property<String> broadcast_command_sound_value = newProperty("Broadcast_Commands.Command.Broadcast.sound.value", "entity.player.levelup");

    public static final Property<Double> broadcast_command_sound_pitch = newProperty("Broadcast_Commands.Command.Broadcast.sound.pitch", 1.0);

    public static final Property<Double> broadcast_command_sound_volume = newProperty("Broadcast_Commands.Command.Broadcast.sound.volume", 1.0);

    // Announcement Command
    @Comment("The sound that will be sent to everyone on the server when an announcement is sent. Set toggle to false")
    public static final Property<Boolean> announcement_command_sound_toggle = newProperty("Broadcast_Commands.Command.Announcement.sound.toggle", false);

    public static final Property<String> announcement_command_sound_value = newProperty("Broadcast_Commands.Command.Announcement.sound.value", "entity.player.levelup");

    public static final Property<Double> announcement_command_sound_pitch = newProperty("Broadcast_Commands.Command.Announcement.sound.pitch", 1.0);

    public static final Property<Double> announcement_command_sound_volume = newProperty("Broadcast_Commands.Command.Announcement.sound.volume", 1.0);

    @Comment("The message that will be sent to everyone on the server.")
    public static final ListProperty<String> announcement_command_message = newListProperty("Broadcast_Commands.Command.Announcement.Message", List.of(
            "&8&m--------------------------",
            "&e&lANNOUNCEMENT",
            "{message}",
            "&8&m--------------------------"
    ));

    // Warning Command
    @Comment("The sound that will be sent to everyone on the server when a broadcasted message is sent. Set toggle to false")
    public static final Property<Boolean> warning_command_sound_toggle = newProperty("Broadcast_Commands.Command.Warning.sound.toggle", false);

    public static final Property<String> warning_command_sound_value = newProperty("Broadcast_Commands.Command.Warning.sound.value", "entity.player.levelup");

    public static final Property<Double> warning_command_sound_pitch = newProperty("Broadcast_Commands.Command.Warning.sound.pitch", 1.0);

    public static final Property<Double> warning_command_sound_volume = newProperty("Broadcast_Commands.Command.Warning.sound.volume", 1.0);

    @Comment("The message that will be sent to everyone on the server.")
    public static final ListProperty<String> warning_command_message = newListProperty("Broadcast_Commands.Command.Warning.Message", List.of(
            "&8&m--------------------------",
            "&4&lWARNING",
            "{message}",
            "&8&m--------------------------"
    ));

    @Comment("Enable chat radius.")
    public static final Property<Boolean> chat_radius_enable = newProperty("Chat_Radius.Enable", true);

    @Comment({
            "When players join the server, or if the plugin is hard reloaded (with plugman) players will be",
            "automatically put into this chat channel.",
            "Chat Channels: Local, Global, World."
    })
    public static final Property<String> chat_radius_default_channel = newProperty("Chat_Radius.Default_Channel", "Global");

    // Local Chat
    @Comment("The prefix that's sent in local chat.")
    public static final Property<String> local_chat_prefix = newProperty("Chat_Radius.Local_Chat.Prefix", "&7[&cLocal&7]");

    @Comment({
            "If you put this symbol in front of your message, it will override any channel which you are in,",
            "sending the message to all players in the server, then keeps you in local chat.",
            "Do Override_Symbol: '' to disable."
    })
    public static final Property<String> local_chat_override_symbol = newProperty("Chat_Radius.Local_Chat.Override_Symbol", "#");

    // Global Chat
    @Comment("The prefix that's sent in global chat.")
    public static final Property<String> global_chat_prefix = newProperty("Chat_Radius.Global_Chat.Prefix", "&7[&bGlobal&7]");

    @Comment({
            "If you put this symbol in front of your message, it will override any channel which you are in,",
            "sending the message to all players in the server, then keeps you in global chat.",
            "Do Override_Symbol: '' to disable."
    })
    public static final Property<String> global_chat_override_symbol = newProperty("Chat_Radius.Global_Chat.Override_Symbol", "!");

    // World Chat
    @Comment("The prefix that's sent in world chat.")
    public static final Property<String> world_chat_prefix = newProperty("Chat_Radius.World_Chat.Prefix", "&7[&dWorld&7]");

    @Comment({
            "If you put this symbol in front of your message, it will override any channel which you are in,",
            "sending the message to all players in the server, then keeps you in world chat.",
            "Do Override_Symbol: '' to disable."
    })
    public static final Property<String> world_chat_override_symbol = newProperty("Chat_Radius.World_Chat.Override_Symbol", "$");

    @Comment("The maximum distance players will receive the sender's messages.")
    public static final Property<Integer> chat_radius_block_distance = newProperty("Chat_Radius.Block_Distance", 250);

    @Comment("Enable chat radius spy on join.")
    public static final Property<Boolean> chat_radius_enable_spy_on_join = newProperty("Chat_Radius.Enable_Spy_On_Join", false);

    @Comment("Clear players chat when they join the server.")
    public static final Property<Boolean> clear_chat_on_join = newProperty("Clear_Chat.Clear_On_Join", false);

    @Comment("How many blank lines should be broadcasted in chat?")
    public static final Property<Integer> clear_chat_broadcasted_lines = newProperty("Clear_Chat.Broadcasted_Lines", 60);

    @Comment("Turn on command spy when players with the correct permission join the server.")
    public static final Property<Boolean> toggle_command_spy = newProperty("Command_Spy.Enable_On_Join", false);

    @Comment("Commands that won't be shown in command spy.")
    public static final Property<List<String>> command_spy_commands = newListProperty("Command_Spy.Blacklist_Commands", List.of(
            "/login",
            "/register"
    ));

    @Comment({
            "This section allows you to enable or disable the use of color codes and format codes in chat.",
            "Each color code and format is permission based.",
            "To give players permission to use color codes give them",
            "chatmanager.color.all or chatmanager.color.{code} without the &. Example: chatmanager.color.a",
            "To give players permission to use format codes give them",
            "chatmanager.format.all or chatmanager.format.{code} without the &. Example: chatmanager.color.f"
    })
    public static final Property<Boolean> formatted_messages_enable = newProperty("Formatted_Messages.Enable", false);

    // Staff and Player List
    @Comment("Staff List")
    public static final ListProperty<String> staff_list = newListProperty("Lists.Staff_List", List.of(
            "&7&m---------------&8[ &aStaff List &8]&7&m---------------",
            "&7Online Staff Members&7: {staff}"
    ));

    @Comment("Player List")
    public static final ListProperty<String> player_list = newListProperty("Lists.Player_List", List.of(
            "&7&m---------------&8[ &aPlayer List &8]&7&m---------------",
            "&7Online Players &a{server_online}&8/&a{server_max_players}&7: {players}"
    ));

    // Logs
    @Comment("Should every message in chat be logged in the chat.log file?")
    public static final Property<Boolean> log_chat = newProperty("Logs.Log_Chat", false);

    @Comment("Should every command that is executed be logged in the commands.log file?")
    public static final Property<Boolean> log_commands = newProperty("Logs.Log_Commands", false);

    @Comment("Should every message placed on a sign be logged in the signs.log file?")
    public static final Property<Boolean> log_signs = newProperty("Logs.Log_Signs", false);

    @Comment("Commands that won't be logged in the commands.log file. Commands in the Blacklist_Commands list must be lowercase.")
    public static final ListProperty<String> log_blacklist_commands = newListProperty("Logs.Blacklist_Commands", List.of("/login", "/register"));

    @Comment("Enable mentions.")
    public static final Property<Boolean> mentions_enable = newProperty("Mentions.Enable", false);

    @Comment("The sound that's played to the receiver")
    public static final Property<Boolean> mentions_sound_toggle = newProperty("Mentions.sound.toggle", false);

    public static final Property<String> mentions_sound_value = newProperty("Mentions.sound.value", "entity.player.levelup");

    public static final Property<Double> mentions_sound_pitch = newProperty("Mentions.sound.pitch", 1.0);

    public static final Property<Double> mentions_sound_volume = newProperty("Mentions.sound.volume", 1.0);

    @Comment("The symbol players have to use to tag other players. Leave it blank for nothing.")
    public static final Property<String> mentions_tag_symbol = newProperty("Mentions.Tag_Symbol", "@");

    @Comment("The color the players name will be highlighted as when mentioned in chat. To disable make it Mention_Color: \"\"")
    public static final Property<String> mentions_mention_color = newProperty("Mentions.Mention_Color", "");

    @Comment("The title message that's sent to the player mentioned.")
    public static final Property<Boolean> mentions_title_enable = newProperty("Mentions.Title.Enable", true);

    public static final Property<String> mentions_title_header = newProperty("Mentions.Title.Header", "<red>Mentioned");

    public static final Property<String> mentions_title_footer = newProperty("Mentions.Title.Footer", "&7You have been mentioned by {player}");

    // Mute Chat
    @Comment("Disable commands when chat is muted.")
    public static final Property<Boolean> mute_chat_disable_commands = newProperty("Mute_Chat.Disable_Commands", false);

    @Comment("Commands that cannot be executed when chat is muted if the boolean 'Disable_Commands' is set to true.")
    public static final ListProperty<String> mute_chat_disabled_commands = newListProperty("Mute_Chat.Disabled_Commands", List.of(
            "/message",
            "/msg",
            "/m",
            "/tell",
            "/t",
            "/whisper",
            "/w",
            "/me",
            "/reply",
            "/r"
    ));

    // Private Messages
    @Comment("Format for the sender's private message.")
    public static final Property<String> private_messages_sender_format = newProperty("Private_Messages.Sender.Format", "&c&l(!) &f&l[&e&lYou &d-> &e{receiver}&f&l] &b{message}");

    @Comment("Format for the receiver's private message.")
    public static final Property<String> private_messages_receiver_format = newProperty("Private_Messages.Receiver.Format", "&c&l(!) &f&l[&e{player} &d-> &e&lYou&f&l] &b{message}");

    @Comment("Allows offline players, to appear in tab completion for commands like /msg or /reply, This can be very intensive which is why it's false.")
    public static final Property<Boolean> private_message_load_offline_players = newProperty("", false);

    @Comment("The sound that's played to the receiver.")
    public static final Property<Boolean> private_messages_sound_toggle = newProperty("Private_Messages.sound.toggle", false);

    public static final Property<String> private_messages_sound_value = newProperty("Private_Messages.sound.value", "entity.player.levelup");

    public static final Property<Double> private_messages_sound_pitch = newProperty("Private_Messages.sound.pitch", 1.0);

    public static final Property<Double> private_messages_sound_volume = newProperty("Private_Messages.sound.volume", 1.0);

    public static final Property<String> server_name = newProperty("Server_Name", "Server Name");

    public static final Property<Boolean> motd_toggle = newProperty("MOTD.Enable", false);

    @Comment("How long to wait before displaying after login in seconds?")
    public static final Property<Integer> motd_delay = newProperty("MOTD.Delay", 2);

    public static final Property<List<String>> motd_message = newListProperty("MOTD.Message", List.of(
            "<gray><st>------------------------------------",
            "<green>Welcome to the server <aqua>{player}<green>!",
            "",
            "<green>If you need any help please message any online staff member.",
            "",
            "<green>You can change this message in the ChatManager - config.yml",
            "<gray><st>------------------------------------"
    ));

    @Comment("Turn on social spy when players with the correct permission join the server.")
    public static final Property<Boolean> toggle_social_spy = newProperty("Social_Spy.Enable_On_Join", false);

    @Comment({
            "=================================================================================================",
            "Talk silently with staff without everyone else on the server seeing.",
            "================================================================================================="
    })
    public static final Property<Boolean> staff_chat_toggle = newProperty("Staff_Chat.Enable", false);

    public static final Property<String> staff_chat_format = newProperty("Staff_Chat.Format", "<yellow>[<aqua>StaffChat<yellow>] <green>{player} <gray>> <aqua>{message}");

    @Comment("A boss bar will appear when entering staff chat to show its enabled.")
    public static final Property<Boolean> staff_bossbar_toggle = newProperty("Staff_Chat.Boss_Bar.Toggle", false);

    public static final Property<String> staff_bossbar_title = newProperty("Staff_Chat.Boss_Bar.Title", "<yellow>Staff Chat");

}