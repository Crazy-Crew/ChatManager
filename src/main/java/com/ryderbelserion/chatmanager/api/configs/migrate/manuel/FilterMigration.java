package com.ryderbelserion.chatmanager.api.configs.migrate.manuel;

import com.ryderbelserion.chatmanager.v1.api.Universal;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FilterMigration implements Universal {

    public static void copyFilterSettings(boolean deleteFile) {
        File config = new File(plugin.getDataFolder() + "/config.yml");

        File bannedCommands = new File(plugin.getDataFolder() + "/bannedcommands.yml");

        File bannedWords = new File(plugin.getDataFolder() + "/bannedwords.yml");

        File newFile = new File(plugin.getDataFolder() + "/word-filter.yml");

        YamlConfiguration fileOne = YamlConfiguration.loadConfiguration(bannedCommands);

        YamlConfiguration fileTwo = YamlConfiguration.loadConfiguration(bannedWords);

        YamlConfiguration fileThree = YamlConfiguration.loadConfiguration(config);

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(newFile);

        if (bannedCommands.exists()) {
            List<String> commands = fileOne.getStringList("Banned-Commands");

            configuration.set("settings.commands.blacklist", commands);

            if (deleteFile) bannedCommands.delete();
        }

        if (bannedWords.exists()) {
            List<String> words = fileTwo.getStringList("Banned-Words");
            List<String> safe = fileTwo.getStringList("Whitelisted_Words");

            configuration.set("settings.words.blacklist", words);
            configuration.set("settings.words.whitelist", safe);

            if (deleteFile) bannedWords.delete();
        }

        if (config.exists()) {
            List<String> whitelist = fileThree.getStringList("Anti_Swear.Commands.Whitelisted_Commands");

            configuration.set("settings.commands.whitelist", whitelist);

            fileThree.set("Anti_Swear.Commands.Whitelisted_Commands", null);

            try {
                fileThree.save(config);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            configuration.save(newFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}