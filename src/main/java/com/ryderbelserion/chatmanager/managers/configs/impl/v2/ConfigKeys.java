package com.ryderbelserion.chatmanager.managers.configs.impl.v2;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.ListProperty;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.managers.configs.beans.GenericProperty;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigKeys implements SettingsHolder {

    @Override
    public void registerComments(@NotNull CommentsConfiguration conf) {
        String[] header = {
                "Support: https://discord.gg/badbones-s-live-chat-182615261403283459",
                "Documentation: https://docs.crazycrew.us/docs/plugins/chatmanager",
                "Github: https://github.com/Crazy-Crew",
                "",
                "Issues: https://github.com/Crazy-Crew/ChatManager/issues",
                "Features: https://github.com/Crazy-Crew/ChatManager/issues",
                "",
                "List of all sounds: https://minecraft.wiki/w/Sounds.json#Java_Edition_values",
                "",
                "We no longer support &7 or &c, You can find a neat tool to easily configure MiniMessage below",
                " ⤷ https://webui.advntr.dev/"
        };

        conf.setComment("root", header);
    }

    @Comment("The prefix used in commands")
    public static final Property<String> command_prefix = newProperty("root.prefix", "<aqua>[<gold>ChatManager<aqua>] <reset>");

    @Comment("This will take into consideration, what the client's locale is set to, on join and when they change it, if this is set to true.")
    public static final Property<Boolean> per_player_locale = newProperty("root.per-player-locale", false);

    @Comment({
            "The default locale file, to display to players if the above option is set to false.",
            "",
            "A list of available localization:",
            " ⤷ en_US (English America)",
            " ⤷ de_DE (German)",
            ""
    })
    public static final Property<String> default_locale_file = newProperty("root.default-locale-file", "en_US");

    @Comment("Commands that will not be logged to the commands.log file. All commands must be lowercased.")
    public static final Property<List<String>> log_commands_whitelist = newListProperty("root.logs.commands.whitelist", List.of(
            "/register",
            "/login"
    ));

    @Comment("Should every command that is executed be logged in the commands.log file?")
    public static final Property<Boolean> log_commands = newProperty("root.logs.commands.enabled", false);

    @Comment("Should every message placed on a sign be logged in the signs.log file?")
    public static final Property<Boolean> log_signs = newProperty("root.logs.signs", false);

    @Comment("Should every message in chat be logged in the chat.log file?")
    public static final Property<Boolean> log_chat = newProperty("root.logs.chat", false);

    @Comment("Should a MOTD be sent when a player joins?")
    public static final Property<Boolean> motd_enabled = newProperty("commands.motd.enable", false);

    @Comment("How long to wait before displaying after login in seconds?")
    public static final Property<Integer> motd_delay = newProperty("commands.motd.delay", 2);

    public static final Property<List<String>> motd_message = newListProperty("commands.motd.message", List.of(
            "<gray><st>------------------------------------",
            "<green>Welcome to the server <aqua>{player}<green>!",
            "",
            "<green>If you need any help please message any online staff member.",
            "",
            "<green>You can change this message in the ChatManager - config.yml",
            "<gray><st>------------------------------------"
    ));

    @Comment("Turn on command spy when players with the correct permission join the server.")
    public static final Property<Boolean> toggle_command_spy = newProperty("features.command_spy.enable_on_join", false);

    @Comment("Commands that will not be spied on. All commands must be lowercased.")
    public static final Property<List<String>> command_spy_commands = newListProperty("features.command_spy.whitelist_commands", List.of(
            "/register",
            "/login"
    ));

    @Comment("Turn on social spy when players with the correct permission join the server.")
    public static final Property<Boolean> toggle_social_spy = newProperty("features.social_spy.enable_on_join", false);

    @Comment("Talk silently with staff without everyone else on the server seeing.")
    public static final Property<Boolean> staff_chat_toggle = newProperty("features.staff_chat.enable", false);

    @Comment("The format of /staffchat <message>")
    public static final Property<String> staff_chat_format = newProperty("features.staff_chat.format", "<yellow>[<aqua>StaffChat<yellow>] <green>{player} <gray>> <aqua>{message}");

    @Comment("A boss bar will appear when entering staff chat to show its enabled.")
    public static final Property<Boolean> staff_bossbar_toggle = newProperty("features.staff_chat.boss_bar.toggle", false);

    public static final Property<String> staff_bossbar_title = newProperty("features.staff_chat.boss_bar.title", "<yellow>Staff Chat");

    @Comment("Format for the sender's private message.")
    public static final Property<String> private_messages_sender_format = newProperty("features.private_messages.sender.format", "&c&l(!) &f&l[&e&lYou &d-> &e{receiver}&f&l] &b{message}");

    @Comment("Format for the receiver's private message.")
    public static final Property<String> private_messages_receiver_format = newProperty("features.private_messages.receiver.format", "&c&l(!) &f&l[&e{player} &d-> &e&lYou&f&l] &b{message}");

    @Comment("Allows offline players, to appear in tab completion for commands like /msg or /reply, This can be very intensive which is why it's false.")
    public static final Property<Boolean> private_message_load_offline_players = newProperty("features.private_messages.load-offline-players", false);

    @Comment("The sound that's played to the receiver.")
    public static final Property<Boolean> private_messages_sound_toggle = newProperty("features.private_messages.sound.toggle", false);

    @Comment("The sound to play to the player. This supports custom sounds!")
    public static final Property<String> private_messages_sound_value = newProperty("features.private_messages.sound.value", "entity.player.levelup");

    @Comment("The pitch of the sound")
    public static final Property<Double> private_messages_sound_pitch = newProperty("features.private_messages.sound.pitch", 1.0);

    @Comment("The volume of the sound")
    public static final Property<Double> private_messages_sound_volume = newProperty("features.private_messages.sound.volume", 1.0);

    @Comment("Disable commands when chat is muted.")
    public static final Property<Boolean> mute_chat_disable_commands = newProperty("features.chat.mute.disable_commands", false);

    @Comment("Commands that cannot be executed when chat is muted, if the option above is true.")
    public static final ListProperty<String> mute_chat_disabled_commands = newListProperty("features.chat.mute.disabled_commands", List.of(
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

    @Comment("Clear players chat when they join the server.")
    public static final Property<Boolean> clear_chat_on_join = newProperty("features.chat.clear_on_join", false);

    @Comment("How many blank lines should be broadcasted in chat?")
    public static final Property<Integer> clear_chat_lines = newProperty("features.chat.clear", 60);

    @Comment("Block certain commands in chat!")
    public static final Property<GenericProperty> blocked_commands = newBeanProperty(GenericProperty.class, "features.blocked_commands", new GenericProperty("blocked_commands"));

    @Comment("Blocks advertising in chat!")
    public static final Property<GenericProperty> block_advertising_chat = newBeanProperty(GenericProperty.class, "features.anti-advertising.chat", new GenericProperty("block_advertising_chat"));

    @Comment("Blocks advertising in signs!")
    public static final Property<GenericProperty> block_advertising_signs = newBeanProperty(GenericProperty.class, "features.anti-advertising.signs", new GenericProperty("block_advertising_signs"));

    @Comment("Blocks advertising in commands!")
    public static final Property<GenericProperty> block_advertising_commands = newBeanProperty(GenericProperty.class, "features.anti-advertising.commands", new GenericProperty("block_advertising_commands"));
}