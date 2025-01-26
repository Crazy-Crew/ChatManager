package com.ryderbelserion.chatmanager.core.managers.configs.locale;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class RootKeys implements SettingsHolder {

    protected RootKeys() {}

    @Override
    public void registerComments(@NotNull CommentsConfiguration conf) {
        conf.setComment("features", "The locale section for existing features!");
    }

    @Comment("The message sent when /chatmanager reload is ran.")
    public static final Property<String> reload_plugin = newProperty("root.reload-plugin", "<prefix><green>Config has been reloaded.");

    @Comment("The output of the motd on join!")
    public static final Property<List<String>> motd = newListProperty("features.motd", List.of(
            "<gray>-------------------------------",
            "",
            " <green>Welcome to the server {player}!",
            "",
            "<gray>-------------------------------"
    ));
}