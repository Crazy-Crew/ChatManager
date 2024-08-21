package com.ryderbelserion.chatmanager.api.enums;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.vital.common.managers.files.FileManager;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.file.YamlConfiguration;
import java.io.File;

public enum Files {

    CONFIG("config.yml"),
    MESSAGES("Messages.yml"),
    BANNED_WORDS("bannedwords.yml"),
    BANNED_COMMANDS("bannedcommands.yml"),
    AUTO_BROADCAST("AutoBroadcast.yml");

    private @NotNull final ChatManager plugin = ChatManager.get();

    private @NotNull final FileManager fileManager = this.plugin.getFileManager();

    private final String fileName;

    /**
     * A constructor to build a file
     *
     * @param fileName the name of the file
     */
    Files(final String fileName) {
        this.fileName = fileName;
    }

    public final YamlConfiguration getConfiguration() {
        return this.fileManager.getFile(this.fileName).getConfiguration();
    }

    public void reload() {
        this.fileManager.addFile(new File(this.plugin.getDataFolder(), this.fileName));
    }

    public void save() {
        this.fileManager.saveFile(this.fileName);
    }
}