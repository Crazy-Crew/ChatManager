package com.ryderbelserion.chatmanager.enums;

import com.ryderbelserion.fusion.files.enums.FileAction;
import com.ryderbelserion.fusion.paper.api.files.PaperFileManager;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.configuration.file.YamlConfiguration;
import java.nio.file.Path;

public enum Files {

    CONFIG("config.yml"),
    MESSAGES("Messages.yml"),
    BANNED_WORDS("bannedwords.yml"),
    BANNED_COMMANDS("bannedcommands.yml"),
    AUTO_BROADCAST("AutoBroadcast.yml");

    private final String fileName;
    private final String strippedName;

    private final ChatManager plugin = ChatManager.get();

    private final Path path = this.plugin.getDataPath();

    private final PaperFileManager fileManager = plugin.getFileManager();

    /**
     * A constructor to build a file
     *
     * @param fileName the name of the file
     */
    Files(final String fileName) {
        this.fileName = fileName;
        this.strippedName = this.fileName.replace(".yml", "");
    }

    public final String getFileName() {
        return this.fileName;
    }

    public final String getStrippedName() {
        return this.strippedName;
    }

    public final YamlConfiguration getConfiguration() {
        return this.fileManager.getPaperFile(this.path.resolve(this.fileName)).get().getConfiguration();
    }

    public void save() {
        this.fileManager.saveFile(this.path.resolve(this.fileName));
    }

    public void reload() {
        this.fileManager.addPaperFile(this.path.resolve(this.fileName), consumer -> consumer.addAction(FileAction.RELOAD_FILE));
    }
}