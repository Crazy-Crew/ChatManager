package com.ryderbelserion.chatmanager.managers.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.chatmanager.managers.configs.impl.messages.commands.ChatKeys;
import com.ryderbelserion.chatmanager.managers.configs.impl.messages.ErrorKeys;
import com.ryderbelserion.chatmanager.managers.configs.impl.messages.MiscKeys;
import com.ryderbelserion.chatmanager.managers.configs.impl.messages.PlayerKeys;
import com.ryderbelserion.chatmanager.managers.configs.impl.messages.commands.SpyKeys;
import com.ryderbelserion.chatmanager.managers.configs.impl.messages.commands.ToggleKeys;
import com.ryderbelserion.chatmanager.managers.configs.impl.types.RuleKeys;
import com.ryderbelserion.chatmanager.managers.configs.impl.types.ConfigKeys;
import com.ryderbelserion.chatmanager.managers.configs.impl.types.chat.SpamKeys;
import com.ryderbelserion.chatmanager.managers.configs.impl.types.chat.ChatRadiusKeys;
import com.ryderbelserion.chatmanager.managers.configs.persist.blacklist.CommandsConfig;
import com.ryderbelserion.chatmanager.managers.configs.persist.blacklist.WordsConfig;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class ConfigManager {

    private static final YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

    private static final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    private static final File folder = new File(plugin.getDataFolder(), "blacklist");

    private static SettingsManager config;
    private static SettingsManager messages;

    private static SettingsManager rules;

    private static CommandsConfig commandsConfig;
    private static WordsConfig wordsConfig;

    public static void load() {
        config = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class, ChatRadiusKeys.class, SpamKeys.class)
                .create();

        messages = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "messages.yml"), builder)
                .useDefaultMigrationService()
                // the order these are added in, is how they are applied to messages.yml
                // SpyKeys, i.e. goes under commands like commands.spy, so similar sections should go together.
                .configurationData(MiscKeys.class, PlayerKeys.class, ErrorKeys.class, ToggleKeys.class, SpyKeys.class, ChatKeys.class)
                .create();

        rules = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "rules.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(RuleKeys.class)
                .create();

        folder.mkdirs();

        commandsConfig = new CommandsConfig();
        commandsConfig.load();

        wordsConfig = new WordsConfig();
        wordsConfig.load();
    }

    /**
     * Refreshes configuration files.
     */
    public static void refresh() {
        config.reload();

        messages.reload();

        Files.commands_file.create();
        Files.words_file.create();

        commandsConfig.load();
        wordsConfig.load();
    }

    public static SettingsManager getConfig() {
        return config;
    }

    public static SettingsManager getMessages() {
        return messages;
    }

    public static SettingsManager getRules() {
        return rules;
    }

    public static CommandsConfig getCommandsConfig() {
        return commandsConfig;
    }

    public static WordsConfig getWordsConfig() {
        return wordsConfig;
    }
}