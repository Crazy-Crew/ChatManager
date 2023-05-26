package com.ryderbelserion.chatmanager.api.configs.builder;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class CustomFile {

    // Does not extend .yml
    private final String strippedName;
    // Extends .yml
    private final String fileName;

    private final Path folder;
    private final String subFolder;

    private FileConfiguration fileConfiguration;

    public CustomFile(String fileName, Path folder, String subFolder) {
        this.strippedName = fileName.replace(".yml", "");
        this.fileName = fileName;
        this.folder = folder;

        this.subFolder = subFolder;
    }

    public void build() {
        File folder = new File(getFolder(), "/" + subFolder);

        if (folder.exists()) {
            File newFile = new File(folder + "/" + this.fileName);

            this.fileConfiguration = newFile.exists() ? YamlConfiguration.loadConfiguration(newFile) : null;

            return;
        }

        folder.mkdir();

        this.fileConfiguration = null;
    }

    public String getStrippedName() {
        return this.strippedName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public File getFolder() {
        return this.folder.toFile();
    }

    public String getSubFolder() {
        return this.subFolder;
    }

    public FileConfiguration getFileConfiguration() {
        return this.fileConfiguration;
    }

    public boolean exists() {
        return this.fileConfiguration != null;
    }

    public boolean save() {
        File newFile = new File(getFolder(), getSubFolder() + "/" + getFileName());

        if (exists()) {
            try {
                getFileConfiguration().save(newFile);

                return true;
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            }
        }

        return false;
    }

    public boolean reload() {
        File newFile = new File(getFolder(), getSubFolder() + "/" + getFileName());

        try {
            this.fileConfiguration = exists() ? YamlConfiguration.loadConfiguration(newFile) : null;

            return true;
        } catch (Exception exception) {
            exception.printStackTrace();

            return false;
        }
    }
}