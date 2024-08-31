package com.ryderbelserion.chatmanager.configs.impl.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.ListProperty;
import ch.jalu.configme.properties.Property;
import org.jetbrains.annotations.NotNull;
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
    }

    // Anti-Spam (Chat)
    @Comment("How many seconds does the player have to wait till they send their next message? Set to 0 to disable.")
    public static final Property<Integer> anti_spam_chat_delay = newProperty("Anti_Spam.Chat.Chat_Delay", 3);

    @Comment("Prevent players from repeating the same messages.")
    public static final Property<Boolean> anti_spam_chat_block_repetitive_messages = newProperty("Anti_Spam.Chat.Block_Repetitive_Messages", false);

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

}