package com.ryderbelserion.chatmanager.api.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.configurationdata.ConfigurationDataBuilder;
import com.ryderbelserion.chatmanager.api.configs.types.ConfigSettings;
import com.ryderbelserion.chatmanager.api.configs.types.FilterSettings;
import com.ryderbelserion.chatmanager.api.configs.types.LocaleSettings;
import com.ryderbelserion.chatmanager.api.configs.types.PluginSettings;
import com.ryderbelserion.chatmanager.api.configs.types.sections.AdvertSettings;
import com.ryderbelserion.chatmanager.api.configs.types.sections.SwearSettings;
import us.crazycrew.crazycore.paper.utils.FileUtils;
import java.io.File;
import java.nio.file.Path;

public class ConfigBuilder {

    private final Path path;

    public ConfigBuilder(Path path) {
        this.path = path;
    }

    private SettingsManager pluginSettings;
    private SettingsManager configSettings;
    private SettingsManager localeSettings;
    private SettingsManager wordFilterSettings;

    public void load() {
        File pluginSettings = new File(path.toFile(), "plugin-settings.yml");

        this.pluginSettings = SettingsManagerBuilder
                .withYamlFile(pluginSettings)
                .useDefaultMigrationService()
                .configurationData(PluginSettings.class)
                .create();

        File configSettings = new File(path.toFile(), "config.yml");

        this.configSettings = SettingsManagerBuilder
                .withYamlFile(configSettings)
                .useDefaultMigrationService()
                .configurationData(ConfigurationDataBuilder.createConfiguration(
                        ConfigSettings.class,
                        AdvertSettings.class,
                        SwearSettings.class
                ))
                .create();

        FileUtils.extract("/locale/", path, false);

        File localeDirectory = new File(path + "/locale/");

        File localeFile = new File(localeDirectory, getPluginSettings().getProperty(PluginSettings.LOCALE_FILE));

        this.localeSettings = SettingsManagerBuilder
                .withYamlFile(localeFile)
                .useDefaultMigrationService()
                .configurationData(LocaleSettings.class)
                .create();

        this.wordFilterSettings = SettingsManagerBuilder
                .withYamlFile(new File(path.toFile(), "word-filter.yml"))
                .useDefaultMigrationService()
                .configurationData(FilterSettings.class)
                .create();
    }

    public SettingsManager getPluginSettings() {
        return this.pluginSettings;
    }

    public SettingsManager getConfigSettings() {
        return this.configSettings;
    }

    public SettingsManager getFilterSettings() {
        return this.wordFilterSettings;
    }

    public SettingsManager getLocaleSettings() {
        return this.localeSettings;
    }

    public void setLocaleSettings(SettingsManager localeSettings) {
        this.localeSettings = localeSettings;
    }
}