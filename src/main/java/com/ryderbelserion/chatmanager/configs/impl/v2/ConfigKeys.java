package com.ryderbelserion.chatmanager.configs.impl.v2;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigKeys implements SettingsHolder {

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
    public static final Property<List<String>> command_spy_commands = newListProperty("command_spy.whitelist_commands", List.of(
            "/register",
            "/login"
    ));

    @Comment("Turn on social spy when players with the correct permission join the server.")
    public static final Property<Boolean> toggle_social_spy = newProperty("features.social_spy.enable_on_join", false);
}