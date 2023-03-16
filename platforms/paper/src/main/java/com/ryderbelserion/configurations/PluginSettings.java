package com.ryderbelserion.configurations;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class PluginSettings implements SettingsHolder {

    public PluginSettings() {}

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "Support: https://discord.gg/crazycrew",
                "Github: https://github.com/Crazy-Crew",
                "",
                "Issues: https://github.com/Crazy-Crew/ChaTManager/issues",
                "Features: https://github.com/Crazy-Crew/ChatManager/discussions"
        };

        conf.setComment("settings", header);
    }

    @Comment("Whether you want to have verbose logging enabled or not.")
    public static final Property<Boolean> VERBOSE_LOGGING = newProperty("settings.verbose-logging", true);

    @Comment("Whether or not you would like to check for plugin updates on startup.")
    public static final Property<Boolean> UPDATE_CHECKER = newProperty("settings.update-checker", true);

    @Comment("Whether or not you would like to allow us to collect statistics on how our plugin is used.")
    public static final Property<Boolean> PLUGIN_METRICS = newProperty("settings.toggle-metrics", true);

}