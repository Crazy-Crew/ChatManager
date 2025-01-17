package com.ryderbelserion.chatmanager.core.managers.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.core.managers.configs.config.ConfigKeys;
import com.ryderbelserion.chatmanager.core.managers.configs.config.chat.ChatKeys;
import com.ryderbelserion.chatmanager.core.managers.configs.locale.RootKeys;
import com.ryderbelserion.core.FusionLayout;
import com.ryderbelserion.core.FusionProvider;
import java.io.File;

public class ConfigManager {

    private static final FusionLayout layout = FusionProvider.get();

    private static SettingsManager config;

    private static SettingsManager locale;

    private static SettingsManager chat;

    public static void load() {
        final File dataFolder = layout.getDataFolder();

        final YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

        config = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class)
                .create();

        locale = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "messages.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(RootKeys.class)
                .create();

        chat = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "chat.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ChatKeys.class)
                .create();
    }

    public static void reload() {
        config.reload();

        locale.reload();

        chat.reload();
    }

    public static SettingsManager getConfig() {
        return config;
    }

    public static SettingsManager getLocale() {
        return locale;
    }

    public static SettingsManager getChat() {
        return chat;
    }
}