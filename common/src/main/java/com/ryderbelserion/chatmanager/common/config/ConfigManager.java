package com.ryderbelserion.chatmanager.common.config;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.ChatManagerProvider;
import com.ryderbelserion.chatmanager.api.ChatManager;
import com.ryderbelserion.chatmanager.common.config.impl.ConfigKeys;
import java.io.File;

public class ConfigManager {

    private final ChatManager chatManager = ChatManagerProvider.get();

    private SettingsManager config;

    public void load() {
        YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

        this.config = SettingsManagerBuilder
                .withYamlFile(new File(this.chatManager.getDataFolder(), "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class)
                .create();
    }

    public void reload() {
        this.config.reload();
    }

    public final SettingsManager getConfig() {
        return this.config;
    }
}