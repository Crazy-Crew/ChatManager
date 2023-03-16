package com.ryderbelserion;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import com.ryderbelserion.configurations.PluginSettings;
import me.h1dd3nxn1nja.chatmanager.utils.MetricsHandler;
import com.ryderbelserion.utils.UpdateChecker;
import java.io.File;

public class ApiLoader implements Universal {

    private static SettingsManager pluginConfig;

    public static void load() {
        pluginConfig = SettingsManagerBuilder
                .withYamlFile(new File(plugin.getDataFolder(), "plugin-settings.yml"))
                .configurationData(PluginSettings.class)
                .useDefaultMigrationService().create();

        if (ApiLoader.getPluginConfig().getProperty(PluginSettings.PLUGIN_METRICS)) {
            MetricsHandler metricsHandler = new MetricsHandler();

            metricsHandler.start();
        }

        if (ApiLoader.getPluginConfig().getProperty(PluginSettings.UPDATE_CHECKER)) {
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                UpdateChecker updateChecker = new UpdateChecker(52245);

                try {
                    if (!updateChecker.hasUpdate()) return;

                    String name = "ChatManager";
                    String version = updateChecker.getNewVersion();
                    String current = plugin.getDescription().getVersion();
                    String download = updateChecker.getResourcePage();

                    String updateMessage = """
                        %s has a new update available. New version: %s
                        Current Version: %s
                        Download: %s
                        """;

                    plugin.getLogger().info(String.format(updateMessage, name, version, current, download));
                } catch (Exception exception) {
                    plugin.getLogger().info(exception.getMessage());
                }
            });
        }
    }

    public static SettingsManager getPluginConfig() {
        return pluginConfig;
    }
}