package com.ryderbelserion.chatmanager.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.configs.types.SpamKeys;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class ConfigManager {

    private static final YamlFileResourceOptions builder = YamlFileResourceOptions.builder().indentationSize(2).build();

    private static final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    private static SettingsManager spam;

    public static void load() {
        spam = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "spam.yml"), builder)
                .useDefaultMigrationService()
                .configurationData(SpamKeys.class)
                .create();
    }

    public static SettingsManager getSpam() {
        return spam;
    }
}