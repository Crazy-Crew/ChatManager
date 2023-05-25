package com.ryderbelserion.chatmanager.api.configs.migrate;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class FilterSettingsMigration {

    public static void copyFilterSettings(boolean deleteFile, Path path) {
        File config = new File(path + "/config.yml");

        if (config.exists()) {
            File bannedCommands = new File(path + "/bannedcommands.yml");

            File bannedWords = new File(path + "/bannedwords.yml");

            File newFile = new File(path + "/word-filter.yml");

            YamlConfiguration fileOne = YamlConfiguration.loadConfiguration(bannedCommands);

            YamlConfiguration fileTwo = YamlConfiguration.loadConfiguration(bannedWords);

            YamlConfiguration fileThree = YamlConfiguration.loadConfiguration(config);

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(newFile);

            if (bannedCommands.exists() && fileOne.getString("Banned-Commands") != null) {
                List<String> commands = fileOne.getStringList("Banned-Commands");

                configuration.set("settings.commands.blacklist", commands);

                if (deleteFile) bannedCommands.delete();
            }

            if (bannedWords.exists() && fileTwo.getString("Banned-Words") != null || fileTwo.getString("Whitelisted_Words") != null) {
                List<String> words = fileTwo.getStringList("Banned-Words");
                List<String> safe = fileTwo.getStringList("Whitelisted_Words");

                configuration.set("settings.words.blacklist", words);
                configuration.set("settings.words.whitelist", safe);

                if (deleteFile) bannedWords.delete();
            }

            if (fileThree.getString("Anti_Swear.Commands.Whitelisted_Commands") != null) {
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
}