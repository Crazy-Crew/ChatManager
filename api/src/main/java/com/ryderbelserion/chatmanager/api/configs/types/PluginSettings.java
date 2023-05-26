package com.ryderbelserion.chatmanager.api.configs.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class PluginSettings implements SettingsHolder {

    // Empty constructor required by SettingsHolder
    public PluginSettings() {}

    private static final String settings = "settings.";

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "Github: https://github.com/Crazy-Crew",
                "",
                "Issues/Features: https://github.com/Crazy-Crew/ChatManager/issues",
                "Wiki: https://github.com/Crazy-Crew/ChatManager/wiki",
                "Discord Support: https://discord.gg/mh7Ydaf"
        };

        conf.setComment("settings", header);
    }

    @Comment("How many commands should be displayed per page in /chatmanager help?")
    public static final Property<Integer> MAX_HELP_PAGE_ENTRIES = newProperty(settings + "max-help-page-entries", 10);

    @Comment("Whether the command prefix should be enabled.")
    public static final Property<Boolean> COMMAND_PREFIX_TOGGLE = newProperty(settings + "settings.prefix-toggle", true);

    @Comment("The command prefix that is shown at the beginning of every message.")
    public static final Property<String> COMMAND_PREFIX = newProperty(settings + "command-prefix", "<red>[</red><green>ChatManager</green><red>]</red> ");

    @Comment("The prefix that is shown for messages sent in console such as logging messages.")
    public static final Property<String> CONSOLE_PREFIX = newProperty(settings + "console-prefix", "<gradient:#fe5f55:#6b55b5>[ChatManager]</gradient> ");

    @Comment({
            "Choose the language you prefer to use on your server!",
            "",
            "Currently Available:",
            " > lang-en.yml ( English )",
            "",
            "If you do not see your language above, You can contribute by modifying the current lang-en.yml",
            "https://github.com/Crazy-Crew/ChatManager/tree/main/src/main/resources/locale/locale-en.yml",
            ""
    })
    public static final Property<String> LOCALE_FILE = newProperty(settings + "locale-file", "lang-en.yml");

    @Comment("Whether you want to have verbose logging enabled or not.")
    public static final Property<Boolean> VERBOSE_LOGGING = newProperty(settings + "verbose-logging", true);

    @Comment("Whether you want statistics sent to https://bstats.org or not.")
    public static final Property<Boolean> TOGGLE_METRICS = newProperty(settings + "toggle-metrics", true);

    @Comment({
            "What command aliases do you want to use?",
            "You can use as many as you would like, Separate each command using : and do not use any spaces!",
            "",
            "Warning: any changes requires a restart!"
    })
    public static final Property<String> PLUGIN_ALIASES = newProperty(settings + "plugin-aliases", "chatmanager");
}