package com.ryderbelserion.chatmanager.common;

import com.ryderbelserion.chatmanager.api.ChatManagerProvider;
import com.ryderbelserion.chatmanager.api.interfaces.platform.IChatManager;
import com.ryderbelserion.chatmanager.common.registry.MessageRegistry;
import com.ryderbelserion.chatmanager.common.registry.UserRegistry;
import com.ryderbelserion.fusion.core.files.FileAction;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.fusion.core.files.FileType;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.ArrayList;

public abstract class ChatManager implements IChatManager {

    private final FileManager fileManager;
    private final Path path;

    public ChatManager(@NotNull final Path path, @NotNull final FileManager fileManager) {
        this.fileManager = fileManager;
        this.path = path;
    }

    private MessageRegistry messageRegistry;
    private UserRegistry userRegistry;

    @Override
    public void start() {
        ChatManagerProvider.register(this);

        this.fileManager.addFolder(this.path.resolve("locale"), FileType.YAML, new ArrayList<>(), null)
                .addFile(this.path.resolve("config.yml"), new ArrayList<>(), null)
                .addFile(this.path.resolve("chat.yml"), new ArrayList<>(), null)
                .addFile(this.path.resolve("messages.yml"), new ArrayList<>(), null);

        this.messageRegistry = new MessageRegistry(this.userRegistry = new UserRegistry());
        this.messageRegistry.init();
    }

    @Override
    public void reload() {
        this.fileManager.refresh(false);

        this.fileManager.addFolder(this.path.resolve("locale"), FileType.YAML, new ArrayList<>() {{
            add(FileAction.RELOAD);
        }}, null); // adds new files only

        this.messageRegistry.init();
    }

    @Override
    public void stop() {

    }

    @Override
    public @NotNull final FileManager getFileManager() {
        return this.fileManager;
    }

    @Override
    public @NotNull final UserRegistry getUserRegistry() {
        return this.userRegistry;
    }

    @Override
    public @NotNull final MessageRegistry getMessageRegistry() {
        return this.messageRegistry;
    }
}