package com.ryderbelserion.chatmanager.core.managers.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.core.managers.configs.config.ConfigKeys;
import com.ryderbelserion.chatmanager.core.managers.configs.config.chat.ChatKeys;
import com.ryderbelserion.chatmanager.core.managers.configs.locale.RootKeys;
import com.ryderbelserion.core.FusionLayout;
import com.ryderbelserion.core.FusionProvider;
import com.ryderbelserion.core.api.enums.FileType;
import com.ryderbelserion.core.util.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private static final FusionLayout layout = FusionProvider.get();

    private static final Map<String, SettingsManager> locales = new HashMap<>();

    private static final YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

    private static SettingsManager config;

    private static SettingsManager locale;

    private static SettingsManager chat;

    public static void load() {
        final File dataFolder = layout.getDataFolder();

        FileUtils.extract("config.yml", false);
        FileUtils.extract("messages.yml", false);
        FileUtils.extract("chat.yml", false);

        config = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "config.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ConfigKeys.class)
                .create();

        locale = SettingsManagerBuilder.withYamlFile(new File(dataFolder, "messages.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(RootKeys.class)
                .create();

        chat = SettingsManagerBuilder
                .withYamlFile(new File(dataFolder, "chat.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(ChatKeys.class)
                .create();

        final File localeFolder = new File(dataFolder, "locale");

        if (localeFolder.mkdirs()) {
            layout.getFileManager().addFolder("locale", FileType.NONE);
        }

        loadLocale(localeFolder);
    }

    public static void reload() {
        config.reload();

        locale.reload();

        chat.reload();

        final File localeFolder = new File(layout.getDataFolder(), "locale");

        if (localeFolder.mkdirs()) {
            layout.getFileManager().addFolder("locale", FileType.NONE);

            loadLocale(localeFolder);
        } else {
            final List<String> brokenLocales = new ArrayList<>();

            locales.forEach((name, settingsManager) -> {
                final File localeFile = new File(localeFolder, name);

                if (localeFile.exists()) {
                    settingsManager.reload();
                } else {
                    brokenLocales.add(name);
                }
            });

            brokenLocales.forEach(locales::remove);
        }
    }

    private static void loadLocale(File localeFolder) {
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
    }

    public static SettingsManager getLocale(final String locale) {
        return locales.getOrDefault(locale, getLocale());
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