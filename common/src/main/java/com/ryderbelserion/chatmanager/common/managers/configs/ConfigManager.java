package com.ryderbelserion.chatmanager.common.managers.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.common.managers.configs.config.ConfigKeys;
import com.ryderbelserion.chatmanager.common.managers.configs.config.chat.ChatKeys;
import com.ryderbelserion.chatmanager.common.managers.configs.locale.RootKeys;
import java.io.File;

public class ConfigManager {

    private SettingsManager config;

    private SettingsManager locale;

    private SettingsManager chat;

    public void load(final File dataFolder) {
        YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

        this.config = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class)
                .create();

        this.locale = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "messages.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(RootKeys.class)
                .create();

        this.chat = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "chat.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ChatKeys.class)
                .create();
    }

    public void reload() {
        this.config.reload();

        this.locale.reload();

        this.chat.reload();
    }

    public final SettingsManager getConfig() {
        return this.config;
    }

    public final SettingsManager getLocale() {
        return this.locale;
    }

    public final SettingsManager getChat() {
        return this.chat;
    }
}