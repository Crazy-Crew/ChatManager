package com.ryderbelserion.chatmanager.api.configs.types.locale;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import org.jetbrains.annotations.NotNull;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class RootKeys implements SettingsHolder {

    @Override
    public void registerComments(@NotNull final CommentsConfiguration configuration) {
        configuration.setComment("root", """
                The root locale section which powers the entire plugin's translation system!
                
                Colors: black, dark_blue, dark_green, dark_aqua, dark_red, dark_purple, gold, gray, dark_gray, blue, green, aqua, red, light_purple, yellow, or white
                """);

        configuration.setComment("commands",
                """
                All per command messages.
                """);

        configuration.setComment("commands.bypass",
                """
                All messages related to lobby protection bypass.
                """);

        final String[] header = {
                "All messages related to errors."
        };

        configuration.setComment("messages.errors", header);

        configuration.setComment("messages.traffic", "Messages handling join/quit messages");
    }

    @Comment("A list of available placeholders: {prefix}")
    public static final Property<String> reload_plugin = newProperty("messages.reload-plugin", "{prefix}<red>You have reloaded the plugin!");

    @Comment("A list of available placeholders: {command}")
    public static final Property<String> unknown_command = newProperty("messages.unknown-command", "{prefix}<red>{command} is not a known command.");

    @Comment("A list of available placeholders: {usage}")
    public static final Property<String> correct_usage = newProperty("messages.correct-usage", "{prefix}<red>The correct usage for this command is <yellow>{usage}");

    public static final Property<String> feature_disabled = newProperty("messages.feature-disabled", "{prefix}<red>This feature is disabled.");

    public static final Property<String> must_be_a_player = newProperty("messages.player.requirements.must-be-player", "{prefix}<red>You must be a player to use this command.");

    public static final Property<String> must_be_console_sender = newProperty("messages.player.requirements.must-be-console-sender", "{prefix}<red>You must be using console to use this command.");

    @Comment("A list of available placeholders: {player}")
    public static final Property<String> not_online = newProperty("messages.player.target-not-online", "{prefix}<red>{player} <gray>is not online.");

    public static final Property<String> same_player = newProperty("messages.player.target-same-player", "{prefix}<red>You cannot use this command on yourself.");

    public static final Property<String> no_permission = newProperty("messages.player.no-permission", "{prefix}<red>You do not have permission to use that command!");

    public static final Property<String> inventory_not_empty = newProperty("messages.player.inventory-not-empty", "{prefix}<red>Inventory is not empty, Please clear up some room.");

    @Comment("Available placeholders: {player}")
    public static final Property<String> join_message = newProperty("messages.traffic.join_message", " <dark_gray>[<green>+</green>]</dark_gray> {player}");

    @Comment("Available placeholders: {player}")
    public static final Property<String> quit_message = newProperty("messages.traffic.quit_message", " <dark_gray>[<red>-</red>]</dark_gray> {player}");

}