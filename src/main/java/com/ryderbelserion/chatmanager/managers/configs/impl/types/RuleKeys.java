package com.ryderbelserion.chatmanager.managers.configs.impl.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.chatmanager.managers.configs.beans.EntryProperty;
import java.util.HashMap;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;

public class RuleKeys implements SettingsHolder {

    @Comment("This is the message, for the /chatmanager rules command!")
    public static final Property<EntryProperty> rules = newBeanProperty(EntryProperty.class, "rules", new EntryProperty().populate(new HashMap<>() {{
        put("1", List.of(
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ <green>Rules Page 1/{max} ━━━━━━━━━━━━━━━━━━━</gold></bold>",
                "",
                " <red>⤷ <green>No Hacking",
                " <red>⤷ <green>No DDOS Threats",
                " <red>⤷ <green>Be Kind to all players and staff",
                "",
                "<gray>Page 1/{max}. Type /chatmanager rules 2 to go to the next page.",
                "",
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ <green>Rules Page 1/{max} ━━━━━━━━━━━━━━━━━━━</gold></bold>"
        ));

        put("2", List.of(
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ <green>Rules Page 2/{max} ━━━━━━━━━━━━━━━━━━━</gold></bold>",
                "",
                " <red>⤷ <green>No swearing anywhere at anytime!",
                " <red>⤷ <green>Do NOT abuse caps!",
                " <red>⤷ <green>No Advertising any other minecraft servers!",
                "",
                "<gray>Page 2/{max}. Type /chatmanager rules 3 to go to the next page.",
                "",
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ <green>Rules Page 2/{max} ━━━━━━━━━━━━━━━━━━━</gold></bold>"
        ));

        put("3", List.of(
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ <green>Rules Page 3/{max} ━━━━━━━━━━━━━━━━━━━</gold></bold>",
                "",
                " <red>⤷ <green>Do not use any special characters in chat.",
                " <red>⤷ <green>Do not ask for staff.",
                " <red>⤷ <green>Do not spam in chat.",
                "",
                "<gray>Page 3/{max}. Type /chatmanager rules 2 to go to the previous page.",
                "",
                "<bold><gold>━━━━━━━━━━━━━━━━━━━ <green>Rules Page 3/{max} ━━━━━━━━━━━━━━━━━━━</gold></bold>"
        ));
    }}));
}