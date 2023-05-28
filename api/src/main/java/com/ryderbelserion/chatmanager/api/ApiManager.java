package com.ryderbelserion.chatmanager.api;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import com.ryderbelserion.chatmanager.api.configs.ConfigBuilder;
import com.ryderbelserion.chatmanager.api.configs.types.FilterSettings;
import com.ryderbelserion.chatmanager.api.configs.types.LocaleSettings;
import com.ryderbelserion.chatmanager.api.configs.types.PluginSettings;
import com.ryderbelserion.chatmanager.api.configs.types.sections.PluginSupportSection;
import com.ryderbelserion.chatmanager.api.storage.types.cache.CacheManager;
import com.ryderbelserion.chatmanager.api.support.types.other.VaultSupport;
import com.ryderbelserion.chatmanager.api.support.types.placeholders.PlaceholderAPISupport;
import com.ryderbelserion.chatmanager.api.support.types.vanish.EssentialsVanishSupport;
import com.ryderbelserion.chatmanager.api.support.types.vanish.GenericVanishSupport;
import com.ryderbelserion.stick.paper.Stick;
import com.ryderbelserion.stick.paper.utils.FileUtils;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.bukkit.Bukkit;
import java.io.File;

public class ApiManager {

    private static ApiManager apiManager;

    private final PluginProviderContext context;

    public ApiManager(PluginProviderContext context) {
        this.context = context;
    }

    private Stick stick;

    private SettingsManager pluginSettings;
    private SettingsManager configSettings;
    private SettingsManager localeSettings;
    private SettingsManager wordFilterSettings;

    private CacheManager cacheManager;

    public void load() {
        // This must go first.
        apiManager = this;

        stick = new Stick(this.context.getDataDirectory(), this.context.getConfiguration().getName());

        File pluginSettings = new File(this.context.getDataDirectory().toFile(), "plugin-settings.yml");

        this.pluginSettings = SettingsManagerBuilder
                .withYamlFile(pluginSettings)
                .useDefaultMigrationService()
                .configurationData(ConfigBuilder.buildPluginSettings())
                .create();

        File configSettings = new File(this.context.getDataDirectory().toFile(), "config.yml");

        this.configSettings = SettingsManagerBuilder
                .withYamlFile(configSettings)
                .useDefaultMigrationService()
                .configurationData(ConfigBuilder.buildConfigSettings())
                .create();

        this.wordFilterSettings = SettingsManagerBuilder
                .withYamlFile(new File(this.context.getDataDirectory().toFile(), "word-filter.yml"))
                .useDefaultMigrationService()
                .configurationData(FilterSettings.class)
                .create();

        this.cacheManager = new CacheManager();

        this.cacheManager.load();

        //this.userManager = new JsonManager(this.path);

        //this.userManager.load();

        reload(false);
    }

    private EssentialsVanishSupport essentialsVanishSupport;

    private GenericVanishSupport genericVanishSupport;

    private EssentialsSupport essentialsSupport;

    private VaultSupport vaultSupport;

    public void reload(boolean reloadCommand) {
        FileUtils.extract("/locale/", this.context.getDataDirectory(), false);

        File localeDirectory = new File(this.context.getDataDirectory() + "/locale/");

        File localeFile = new File(localeDirectory, getPluginSettings().getProperty(PluginSettings.LOCALE_FILE));

        this.localeSettings = SettingsManagerBuilder
                .withYamlFile(localeFile)
                .useDefaultMigrationService()
                .configurationData(LocaleSettings.class)
                .create();

        if (this.pluginSettings.getProperty(PluginSupportSection.ESSENTIALS_SUPPORT)) {
            this.essentialsSupport = new EssentialsSupport();
            this.essentialsVanishSupport = new EssentialsVanishSupport();
        }

        if (this.pluginSettings.getProperty(PluginSupportSection.PREMIUM_VANISH_SUPPORT) || this.pluginSettings.getProperty(PluginSupportSection.SUPER_VANISH_SUPPORT)) this.genericVanishSupport = new GenericVanishSupport();

        if (this.pluginSettings.getProperty(PluginSupportSection.PLACEHOLDER_API_SUPPORT)) new PlaceholderAPISupport().register();

        if (this.pluginSettings.getProperty(PluginSupportSection.VAULT_SUPPORT)) {
            this.vaultSupport = new VaultSupport();
            this.vaultSupport.start(Bukkit.getServer());
        }

        if (reloadCommand) {
            this.pluginSettings.reload();

            this.configSettings.reload();
        }
    }

    public static ApiManager getApiManager() {
        return apiManager;
    }

    public Stick getStick() {
        return this.stick;
    }

    public CacheManager getCacheManager() {
        return this.cacheManager;
    }

    // Plugin Support
    public EssentialsSupport getEssentialsSupport() {
        return this.essentialsSupport;
    }

    public EssentialsVanishSupport getEssentialsVanishSupport() {
        return this.essentialsVanishSupport;
    }

    public GenericVanishSupport getGenericVanishSupport() {
        return this.genericVanishSupport;
    }

    public VaultSupport getVaultSupport() {
        return this.vaultSupport;
    }

    // Config Settings
    public SettingsManager getPluginSettings() {
        return this.pluginSettings;
    }

    public SettingsManager getConfigSettings() {
        return this.configSettings;
    }

    public SettingsManager getWordFilterSettings() {
        return this.wordFilterSettings;
    }

    public SettingsManager getLocaleSettings() {
        return this.localeSettings;
    }

    public void setLocaleSettings(SettingsManager localeSettings) {
        this.localeSettings = localeSettings;
    }
}