package com.ryderbelserion.chatmanager.common.config;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.common.config.impl.ConfigKeys;
import java.io.File;

public class ConfigManager {

    private SettingsManager config;

    public void load(final File dataFolder) {
        YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

        this.config = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "config.yml"), builder)
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