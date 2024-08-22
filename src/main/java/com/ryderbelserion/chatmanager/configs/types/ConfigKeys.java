package com.ryderbelserion.chatmanager.configs.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigKeys implements SettingsHolder {

    @Comment("The prefix in front of the commands")
    public static final Property<String> prefix = newProperty("Root.Prefix", "<aqua>[<gold>ChatManager<aqua>] <reset>");

    @Comment("Turn on command spy when players with the correct permission join the server.")
    public static final Property<Boolean> toggle_command_spy = newProperty("Command_Spy.Enable_On_Join", false);

    @Comment("Commands that won't be shown in command spy.")
    public static final Property<List<String>> command_spy_commands = newListProperty("Command_Spy.Blacklist_Commands", List.of(
            "/login",
            "/register"
    ));

    public static final Property<Boolean> motd_toggle = newProperty("MOTD.Enable", false);

    @Comment("How long to wait before displaying after login in seconds?")
    public static final Property<Integer> motd_delay = newProperty("MOTD.Delay", 2);

    public static final Property<List<String>> motd_message = newListProperty("MOTD.Message", List.of(
            "<gray><st>------------------------------------",
            "<green>Welcome to the server <aqua>{player}<green>!",
            "",
            "<green>If you need any help please message any online staff member.",
            "",
            "<green>You can change this message in the ChatManager - Config.yml",
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