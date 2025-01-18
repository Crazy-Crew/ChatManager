package com.ryderbelserion.chatmanager.core.enums.keys;

import com.ryderbelserion.core.FusionLayout;
import com.ryderbelserion.core.FusionProvider;
import com.ryderbelserion.core.api.enums.FileType;
import com.ryderbelserion.core.files.FileManager;
import com.ryderbelserion.core.files.types.YamlCustomFile;

public enum Files {

    config("config.yml"),
    chat("chat.yml");

    private final String fileName;
    private final String folder;

    Files(final String fileName, final String folder) {
        this.fileName = fileName;
        this.folder = folder;
    }

    Files(final String fileName) {
        this(fileName, "");
    }

    private final FusionLayout fusion = FusionProvider.get();

    private final FileManager fileManager = this.fusion.getFileManager();

    public final YamlCustomFile getCustomFile() {
        return (YamlCustomFile) this.fileManager.getFile(this.fileName, FileType.YAML);
    }

    public final String getFileName() {
        return this.fileName;
    }

    public final String getFolder() {
        return this.folder;
    }
}