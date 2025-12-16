package com.ryderbelserion.chatmanager.common.registry.databases.types.flatfile;

import com.ryderbelserion.chatmanager.common.registry.databases.interfaces.IConnector;
import com.ryderbelserion.fusion.core.api.FusionProvider;
import com.ryderbelserion.fusion.files.FileManager;
import com.ryderbelserion.fusion.files.enums.FileType;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.nio.file.Path;

public class JsonConnector implements IConnector {

    private final FusionKyori fusion = (FusionKyori) FusionProvider.getInstance();
    private final FileManager fileManager = this.fusion.getFileManager();
    private final Path path;

    public JsonConnector(@NotNull final Path path) {
        this.path = path;
    }

    @Override
    public void init(@NotNull final CommentedConfigurationNode config) {
        this.fileManager.addFile(this.path, FileType.JSON);
    }

    @Override
    public void reload() {
        this.fileManager.reloadFile(this.path);
    }

    @Override
    public void stop() {
        this.fileManager.saveFile(this.path);
        this.fileManager.removeFile(this.path);
    }

    @Override
    public String getImpl() {
        return "JSON";
    }
}