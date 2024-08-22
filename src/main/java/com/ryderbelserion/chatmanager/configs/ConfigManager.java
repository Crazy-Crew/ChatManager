package com.ryderbelserion.chatmanager.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.configs.impl.messages.messages.ErrorKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.messages.MiscKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.messages.PlayerKeys;
import com.ryderbelserion.chatmanager.configs.types.MessageKeys;
import com.ryderbelserion.chatmanager.configs.types.SpamKeys;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import com.ryderbelserion.vital.common.managers.files.CustomFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.ConfigurationSection;
import org.simpleyaml.configuration.file.YamlFile;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private static final YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

    private static final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    private static final Map<Integer, List<String>> rules = new HashMap<>();

    private static SettingsManager config;
    private static SettingsManager messages;

    public static void load() {
        config = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class, SpamKeys.class)
                .create();

        messages = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "messages.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(PlayerKeys.class, ErrorKeys.class, MiscKeys.class, MessageKeys.class)
                .create();

        populateRules();
    }

    /**
     * Refreshes configuration files.
     */
    public static void refresh() {
        config.reload();

        populateRules();
    }

    public static SettingsManager getConfig() {
        return config;
    }

    public static SettingsManager getMessages() {
        return messages;
    }

    public static void populateRules() {
        rules.clear();

        final CustomFile customFile = plugin.getFileManager().getFile("rules.yml");

        if (customFile != null) {
            YamlFile file = customFile.getConfiguration();

            if (file != null) {
                ConfigurationSection section = file.getConfigurationSection("rules");

                if (section != null) {
                    section.getKeys(false).forEach(key -> {
                        final List<String> rule = section.getStringList(key);

                        rules.put(Integer.parseInt(key), rule);
                    });
                }
            }
        }
    }

    public static Map<Integer, List<String>> getRules() {
        return rules;
    }
}