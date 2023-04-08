package com.ryderbelserion.chatmanager.api;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.support.PluginManager;
import me.h1dd3nxn1nja.chatmanager.support.misc.VaultSupport;
import org.bukkit.configuration.file.FileConfiguration;

public interface Universal {

    ChatManager plugin = ChatManager.getPlugin();

    PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();
    VaultSupport vaultSupport = plugin.getPluginManager().getVaultSupport();

    PluginManager pluginManager = plugin.getPluginManager();

    EssentialsSupport essentialsSupport = pluginManager.getEssentialsSupport();

    SettingsManager settingsManager = plugin.getSettingsManager();

    FileConfiguration config = settingsManager.getConfig();

    FileConfiguration messages = settingsManager.getMessages();
    FileConfiguration bannedCommands = settingsManager.getBannedCommands();
    FileConfiguration bannedWords = settingsManager.getBannedWords();

}