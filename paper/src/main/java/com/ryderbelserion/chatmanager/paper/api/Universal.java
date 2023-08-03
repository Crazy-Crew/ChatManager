package com.ryderbelserion.chatmanager.paper.api;

import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.paper.support.EssentialsSupport;
import me.h1dd3nxn1nja.chatmanager.paper.support.PluginManager;
import me.h1dd3nxn1nja.chatmanager.paper.support.misc.VaultSupport;
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