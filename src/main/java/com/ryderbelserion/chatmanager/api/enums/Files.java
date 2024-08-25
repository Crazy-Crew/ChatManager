package com.ryderbelserion.chatmanager.api.enums;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.vital.paper.api.files.FileManager;
import org.jetbrains.annotations.NotNull;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public enum Files {

    advertisement_log_file("advertisements.log", "logs", true),
    command_log_file("commands.log", "logs", true),
    swear_log_file("swears.log", "logs", true),
    sign_log_file("signs.log", "logs", true),
    chat_log_file("chat.log", "logs", true),

    commands_file("commands.json", "blacklist", true),
    words_file("words.json", "blacklist", true),

    auto_broadcast_file("broadcast.yml");

    private @NotNull final ChatManager plugin = ChatManager.get();

    private @NotNull final FileManager fileManager = this.plugin.getFileManager();

    private final String fileName;
    private final boolean isPlain;

    private final File file;

    /**
     * A constructor to build a file
     *
     * @param fileName the name of the file
     * @param filePath the path of the file
     * @param isPlain true or false
     */
    Files(final String fileName, final String filePath, final boolean isPlain) {
        this.fileName = fileName;
        this.isPlain = isPlain;

        this.file = this.isPlain ? new File(new File(this.plugin.getDataFolder(), filePath), this.fileName) : new File(this.plugin.getDataFolder(), this.fileName);
    }

    /**
     * A constructor to build a file
     *
     * @param fileName the name of the file
     */
    Files(final String fileName) {
        this(fileName, "", false);
    }

    public final YamlConfiguration getConfiguration() {
        if (this.isPlain) return null;

        return this.fileManager.getFile(this.fileName).getConfiguration();
    }

    public void reload() {
        if (this.isPlain) return;

        this.fileManager.addFile(this.file);
    }

    public void save() {
        if (this.isPlain) return;

        this.fileManager.saveFile(this.fileName);
    }

    public final Files create() {
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();

            try {
                this.file.createNewFile();
            } catch (IOException exception) {
                this.plugin.getComponentLogger().warn("Failed to create file: {}", this.file.getName());
            }
        }

        return this;
    }

    public final File getFile() {
        return this.file;
    }
}