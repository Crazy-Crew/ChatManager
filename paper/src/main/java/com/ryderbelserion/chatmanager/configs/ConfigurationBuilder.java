package com.ryderbelserion.chatmanager.configs;

import ch.jalu.configme.configurationdata.ConfigurationData;
import ch.jalu.configme.configurationdata.ConfigurationDataBuilder;
import com.ryderbelserion.chatmanager.configs.sections.WordFilterSettings;

public class ConfigurationBuilder {

    /**
     * Private constructor to prevent instantiation
     */
    private ConfigurationBuilder() {}

    public static ConfigurationData buildFilterData() {
        return ConfigurationDataBuilder.createConfiguration(WordFilterSettings.class);
    }
}