package com.ryderbelserion.chatmanager.paper.files.enums;

import com.ryderbelserion.chatmanager.paper.files.FileManager;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;

public enum Files {

    // ENUM_NAME("fileName.yml", "fileLocation.yml"),
    // ENUM_NAME("fileName.yml", "newFileLocation.yml", "oldFileLocation.yml"),
    CONFIG("config.yml", "config.yml"),
    MESSAGES("Messages.yml", "Messages.yml"),
    BANNED_WORDS("bannedwords.yml", "bannedwords.yml"),
    BANNED_COMMANDS("bannedcommands.yml", "bannedcommands.yml"),
    AUTO_BROADCAST("AutoBroadcast.yml", "AutoBroadcast.yml");

    private final String fileName;
    private final String fileJar;
    private final String fileLocation;

    private final ChatManager plugin = ChatManager.getPlugin();

    private final FileManager fileManager = plugin.getFileManager();

    /**
     * The files that the server will try and load.
     * @param fileName The file name that will be in the plugin's folder.
     * @param fileLocation The location the file in the plugin's folder.
     */
    Files(String fileName, String fileLocation) {
        this(fileName, fileLocation, fileLocation);
    }

    /**
     * The files that the server will try and load.
     * @param fileName The file name that will be in the plugin's folder.
     * @param fileLocation The location of the file will be in the plugin's folder.
     * @param fileJar The location of the file in the jar.
     */
    Files(String fileName, String fileLocation, String fileJar) {
        this.fileName = fileName;
        this.fileLocation = fileLocation;
        this.fileJar = fileJar;
    }

    /**
     * Get the name of the file.
     * @return The name of the file.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * The location the jar it is at.
     * @return The location in the jar the file is in.
     */
    public String getFileLocation() {
        return fileLocation;
    }

    /**
     * Get the location of the file in the jar.
     * @return The location of the file in the jar.
     */
    public String getFileJar() {
        return fileJar;
    }

    /**
     * Gets the file from the system.
     * @return The file from the system.
     */
    public FileConfiguration getFile() {
        return fileManager.getFile(this);
    }

    /**
     * Saves the file from the loaded state to the file system.
     */
    public void saveFile() {
        fileManager.saveFile(this);
    }

    /**
     * Overrides the loaded state file and loads the file systems file.
     */
    public void reloadFile() {
        fileManager.reloadFile(this);
    }
}