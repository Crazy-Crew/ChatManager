package com.ryderbelserion.chatmanager.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import com.ryderbelserion.chatmanager.api.Universal;
import java.io.File;

public class SettingsHandler implements Universal {

    private SettingsManager wordFilterSettings;

    public void load() {
        wordFilterSettings = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "word-filter.yml"))
                .useDefaultMigrationService()
                .configurationData(ConfigurationBuilder.buildFilterData())
                .create();
    }

    public SettingsManager getWordFilterSettings() {
        return this.wordFilterSettings;
    }
}