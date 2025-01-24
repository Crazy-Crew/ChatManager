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
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final FusionLayout layout = FusionProvider.get();

    private static final Map<String, SettingsManager> locales = new HashMap<>();

    private static SettingsManager config;

    private static SettingsManager chat;

    public static void load() {
        final File dataFolder = layout.getDataFolder();

        final YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

        config = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class)
                .create();

        final File localeFolder = new File(dataFolder, "locale");

        locales.put("en-US.yml", SettingsManagerBuilder
                .withYamlFile(new File(localeFolder, "en-US.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(RootKeys.class)
                .create());

        final File[] contents = localeFolder.listFiles();

        if (contents != null) {
            for (final File file : contents) {
                final String name = file.getName();

                if (file.isDirectory() || !name.endsWith(".yml") || locales.containsKey(name)) continue;

                locales.put(name, SettingsManagerBuilder
                        .withYamlFile(file, builder)
                        .useDefaultMigrationService()
                        .configurationData(RootKeys.class)
                        .create());
            }
        }

        chat = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "chat.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ChatKeys.class)
                .create();
    }

    public static void reload() {
        config.reload();

        locales.forEach((name, settingsManager) -> settingsManager.reload());

        chat.reload();
    }

    public static SettingsManager getConfig() {
        return config;
    }

    public static SettingsManager getLocale(final String locale) {
        return locales.getOrDefault(locale, locales.get("en-US.yml"));
    }

    public static SettingsManager getLocale() {
        return getLocale("en-US.yml");
    }

    public static SettingsManager getChat() {
        return chat;
    }
}