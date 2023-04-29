package com.ryderbelserion.chatmanager.api.configs.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;

public class FilterSettings implements SettingsHolder {

    public FilterSettings() {}

    @Override
    public void registerComments(CommentsConfiguration conf) {
        String[] header = {
                "Support: https://discord.gg/SekseczrWz",
                "Github: https://github.com/Crazy-Crew",
                "",
                "Issues: https://github.com/Crazy-Crew/ChatManager/issues",
                "Features: https://github.com/Crazy-Crew/ChatManager/discussions/categories/features",
                ""
        };

        conf.setComment("settings", header);
    }

    @Comment("Blacklist any words from working in chats, commands and sign checks.")
    public static final Property<List<String>> BLACKLISTED_WORDS = newListProperty("settings.words.blacklist", List.of(
            "shit",
            "fuck"
    ));

    @Comment({
            "If you have increased sensitivity enabled, This helps prevent false positives.",
            "Any words here will not be effected by the anti-swear if you have increase sensitivity enabled.",
            "Works for all chats, commands and sign checks.",
    })
    public static final Property<List<String>> WHITELISTED_WORDS = newListProperty("settings.words.whitelist", List.of(
            "an alt",
            "mike hunt"
    ));

    @Comment("If a player executes one of these commands with a swear word in it, the command will be blocked.")
    public static final Property<List<String>> BLACKLISTED_COMMANDS = newListProperty("settings.commands.blacklist", List.of(
            "plugins",
            "pl"
    ));

    @Comment("If a player executes one of these commands with a swear word in it, the command will not be blocked.")
    public static final Property<List<String>> WHITELISTED_COMMANDS = newListProperty("settings.commands.whitelist", List.of(
            "/report",
            "/login",
            "/register"
    ));
}