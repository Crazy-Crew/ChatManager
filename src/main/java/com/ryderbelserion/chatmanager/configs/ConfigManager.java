package com.ryderbelserion.chatmanager.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.configs.types.SpamKeys;
import com.ryderbelserion.chatmanager.configs.types.rules.RuleKeys;
import com.ryderbelserion.chatmanager.configs.types.spy.ModKeys;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class ConfigManager {

    private static final YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

    private static final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    private static SettingsManager config;
    private static SettingsManager rules;
    private static SettingsManager spam;

    public static void load() {
        config = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ModKeys.class)
                .create();

        rules = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "rules.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(RuleKeys.class)
                .create();

        spam = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "spam.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(SpamKeys.class)
                .create();
    }

    /**
     * Refreshes configuration files.
     */
    public static void refresh() {
        config.reload();

        rules.reload();

        spam.reload();
    }

    public static SettingsManager getConfig() {
        return config;
    }

    public static SettingsManager getRules() {
        return rules;
    }

    public static SettingsManager getSpam() {
        return spam;
    }
}