package com.ryderbelserion.chatmanager.enums;

import com.ryderbelserion.vital.paper.files.config.FileManager;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Files {

    CONFIG("config.yml"),
    MESSAGES("Messages.yml"),
    BANNED_WORDS("bannedwords.yml"),
    BANNED_COMMANDS("bannedcommands.yml"),
    AUTO_BROADCAST("AutoBroadcast.yml");

    private final String fileName;
    private final String strippedName;
    private final YamlConfiguration configuration;

    private final ChatManager plugin = ChatManager.get();

    private final FileManager fileManager = plugin.getFileManager();

    /**
     * A constructor to build a file
     *
     * @param fileName the name of the file
     */
    Files(final String fileName) {
        this.fileName = fileName;
        this.strippedName = this.fileName.replace(".yml", "");
        this.configuration = this.fileManager.getFile(this.fileName);
    }

    public final String getFileName() {
        return this.fileName;
    }

    public final String getStrippedName() {
        return this.strippedName;
    }

    public final YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public void save() {
        this.fileManager.saveFile(this.fileName);
    }
}