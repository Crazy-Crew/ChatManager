package com.ryderbelserion.chatmanager.v1.api;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginManager;
import me.h1dd3nxn1nja.chatmanager.support.misc.VaultSupport;
import org.bukkit.configuration.file.FileConfiguration;

public interface Universal {

    ChatManager plugin = ChatManager.getPlugin();

    PluginManager pluginManager = plugin.getPluginManager();
    CrazyManager crazyManager = plugin.getCrazyManager();

    PlaceholderManager placeholderManager = crazyManager.getPlaceholderManager();

    VaultSupport vaultSupport = pluginManager.getVaultSupport();

    EssentialsSupport essentialsSupport = pluginManager.getEssentialsSupport();


    SettingsManager settingsManager = plugin.getSettingsManager();

    FileConfiguration config = settingsManager.getConfig();

    FileConfiguration messages = settingsManager.getMessages();

}