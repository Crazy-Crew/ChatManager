package com.ryderbelserion.chatmanager.configs.impl.messages;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.configs.beans.EntryProperty;

import java.util.HashMap;
import java.util.List;

import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class MiscKeys implements SettingsHolder {

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "Support: https://discord.gg/SekseczrWz",
                "Github: https://github.com/Crazy-Crew",
                "",
                "Issues: https://github.com/Crazy-Crew/ChatManager/issues",
                "Features: https://github.com/Crazy-Crew/ChatManager/issues",
                "",
                "All messages allow the use of {prefix} unless stated otherwise.",
                ""
        };

        conf.setComment("misc", header);
    }

    @Comment("A list of available placeholders: {command}")
    public static final Property<String> unknown_command = newProperty("misc.unknown-command", "{prefix}<red>{command} is not a known command.");

    @Comment("A list of available placeholders: {usage}")
    public static final Property<String> correct_usage = newProperty("misc.correct-usage", "{prefix}<red>The correct usage for this command is <yellow>{usage}");

    public static final Property<String> feature_disabled = newProperty("misc.feature-disabled", "{prefix}<red>This feature is disabled.");

    @Comment("This is the message, for the /lobbyplus help command!")
    public static final Property<EntryProperty> help = newBeanProperty(EntryProperty.class, "misc.command-help", new EntryProperty(new HashMap<>() {{
        put(1, List.of(
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ <green>Help Page 1/{max} ━━━━━━━━━━━━━━━━━━━</gold></bold>",
                "",
                " <gold>[] <white>= Required Arguments",
                " <dark_green>[] <white>= Optional Arguments",
                "",
                " ⤷ <white>/chatmanager help <gold>[page] <yellow>- Shows the help menu for the plugin ChatManager!",
                " ⤷ <white>/chatmanager filter add <gold>[type] [word] <yellow>- Adds a word/command to the filter.",
                " ⤷ <white>/chatmanager filter remove <gold>[type] [word] <yellow>- Removes a word from the filter.",
                " ⤷ <white>/chatmanager filter list <gold>[type] <yellow>- Shows a list of words from this filter.",
                " ⤷ <white>/chatmanager rules <gold>[page] <yellow>- Shows the rules configured for your server!",
                " ⤷ <white>/chatmanager filter help <yellow>- Shows the help menu for /chatmanager filter.",
                " ⤷ <white>/chatmanager chatradius <gold>[chat type] <yellow>- Changes your server radius!",
                " ⤷ <white>/chatmanager toggle <gold>[toggle type] <yellow>- Manage multiple chat types!",
                " ⤷ <white>/chatmanager clearchat <gold>[amount] <yellow>- Clears the chat.",
                " ⤷ <white>/chatmanager motd - <yellow>Shows the message of the day!",
                "",
                "<gray>Page 1/{max}. Type /chatmanager help 2 to go to the next page.",
                "",
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ <green>Help Page 1/{max} ━━━━━━━━━━━━━━━━━━━</gold></bold>"
        ));

        put(2, List.of(
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ <green>Help Page 2/{max} ━━━━━━━━━━━━━━━━━━━</gold></bold>",
                "",
                " <gold>[] <white>= Required Arguments",
                " <dark_green>[] <white>= Optional Arguments",
                "",
                " ⤷ <white>/chatmanager staffchat <dark_green>[message] <yellow>- Sends a message to other online staff!",
                " ⤷ <white>/chatmanager spy <gold>[spy type] <yellow>- Allows you to spy on commands/messages.",
                " ⤷ <white>/chatmanager warning <gold>[message] <yellow>- Reloads the plugin.",
                " ⤷ <white>/chatmanager reload <yellow>- Reloads the plugin.",
                "",
                "<gray>Page 1/{max}. Type /chatmanager help 2 to go to the next page.",
                "",
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ <green>Help Page 2/{max} ━━━━━━━━━━━━━━━━━━━</gold></bold>"
        ));
    }}));
}