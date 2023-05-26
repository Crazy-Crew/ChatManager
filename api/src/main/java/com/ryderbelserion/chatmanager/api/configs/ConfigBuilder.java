package com.ryderbelserion.chatmanager.api.configs;

import ch.jalu.configme.configurationdata.ConfigurationData;
import ch.jalu.configme.configurationdata.ConfigurationDataBuilder;
import com.ryderbelserion.chatmanager.api.configs.types.ConfigSettings;
import com.ryderbelserion.chatmanager.api.configs.types.PluginSettings;
import com.ryderbelserion.chatmanager.api.configs.types.sections.AdvertisementSection;
import com.ryderbelserion.chatmanager.api.configs.types.sections.PluginSupportSection;
import com.ryderbelserion.chatmanager.api.configs.types.sections.SwearingSection;

public class ConfigBuilder {

    /**
     * Private constructor to prevent instantiation
     */
    private ConfigBuilder() {}

    /**
     * Builds the core configuration data.
     *
     * @return configuration data object containing the main crate configurations.
     */
    public static ConfigurationData buildPluginSettings() {
        return ConfigurationDataBuilder.createConfiguration(
                PluginSettings.class,
                PluginSupportSection.class
        );
    }

    public static ConfigurationData buildConfigSettings() {
        return ConfigurationDataBuilder.createConfiguration(
                ConfigSettings.class,
                AdvertisementSection.class,
                SwearingSection.class
        );
    }
}