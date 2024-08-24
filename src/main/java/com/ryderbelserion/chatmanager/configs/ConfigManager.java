package com.ryderbelserion.chatmanager.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.chatmanager.configs.impl.messages.commands.ChatKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.ErrorKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.MiscKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.PlayerKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.commands.SpyKeys;
import com.ryderbelserion.chatmanager.configs.impl.messages.commands.ToggleKeys;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.CommandsConfig;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.WordsConfig;
import com.ryderbelserion.chatmanager.configs.types.MessageKeys;
import com.ryderbelserion.chatmanager.configs.types.SpamKeys;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import com.ryderbelserion.vital.paper.api.files.CustomFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private static final YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

    private static final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    private static final File folder = new File(plugin.getDataFolder(), "blacklist");

    private static final Map<Integer, List<String>> rules = new HashMap<>();

    private static final Map<Integer, List<String>> help = new HashMap<>();

    private static SettingsManager config;
    private static SettingsManager messages;

    private static CommandsConfig commandsConfig;
    private static WordsConfig wordsConfig;

    public static void load() {
        config = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class, SpamKeys.class)
                .create();

        messages = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "messages.yml"), builder)
                .useDefaultMigrationService()
                // the order these are added in, is how they are applied to messages.yml
                // SpyKeys, i.e. goes under commands like commands.spy, so similar sections should go together.
                .configurationData(MiscKeys.class, PlayerKeys.class, ErrorKeys.class, ToggleKeys.class, SpyKeys.class, ChatKeys.class, MessageKeys.class)
                .create();

        folder.mkdirs();

        commandsConfig = new CommandsConfig();
        commandsConfig.load();

        wordsConfig = new WordsConfig();
        wordsConfig.load();
        populateRules();
        populateHelp();
    }

    /**
     * Refreshes configuration files.
     */
    public static void refresh() {
        config.reload();

        messages.reload();

        Files.commands_file.create();
        Files.words_file.create();

        commandsConfig.save();

        wordsConfig.save();

        populateRules();
        populateHelp();
    }

    public static SettingsManager getConfig() {
        return config;
    }

    public static SettingsManager getMessages() {
        return messages;
    }

    public static CommandsConfig getCommandsConfig() {
        return commandsConfig;
    }

    public static WordsConfig getWordsConfig() {
        return wordsConfig;
    }

    public static void populateRules() {
        rules.clear();

        final CustomFile customFile = plugin.getFileManager().getFile("rules.yml");

        if (customFile != null) {
            final YamlConfiguration file = customFile.getConfiguration();

            if (file != null) {
                final ConfigurationSection section = file.getConfigurationSection("rules");

                if (section != null) {
                    section.getKeys(false).forEach(key -> {
                        final List<String> rule = section.getStringList(key + ".lines");

                        rules.put(Integer.parseInt(key), rule);
                    });
                }
            }
        }
    }

    public static void populateHelp() {
        help.clear();

        final CustomFile customFile = plugin.getFileManager().getFile("help.yml");

        if (customFile != null) {
            final YamlConfiguration file = customFile.getConfiguration();

            if (file != null) {
                final ConfigurationSection section = file.getConfigurationSection("help");

                if (section != null) {
                    section.getKeys(false).forEach(key -> {
                        final List<String> rule = section.getStringList(key + ".lines");

                        help.put(Integer.parseInt(key), rule);
                    });
                }
            }
        }
    }

    public static Map<Integer, List<String>> getRules() {
        return Collections.unmodifiableMap(rules);
    }

    public static Map<Integer, List<String>> getHelp() {
        return Collections.unmodifiableMap(help);
    }
}