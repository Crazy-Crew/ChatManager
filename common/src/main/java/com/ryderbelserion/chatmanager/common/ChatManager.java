package com.ryderbelserion.chatmanager.common;

import com.ryderbelserion.chatmanager.api.ChatManagerProvider;
import com.ryderbelserion.chatmanager.api.interfaces.platform.IChatManager;
import com.ryderbelserion.chatmanager.common.registry.databases.DataManager;
import com.ryderbelserion.chatmanager.common.registry.MessageRegistry;
import com.ryderbelserion.chatmanager.common.registry.UserRegistry;
import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.files.FileManager;
import com.ryderbelserion.fusion.files.enums.FileType;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.fusion.kyori.permissions.PermissionRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.UUID;

public abstract class ChatManager implements IChatManager {

    public static final UUID console = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String namespace = "chatmanager";

    protected final FileManager fileManager;
    protected final FusionKyori fusion;
    protected final Path path;

    public ChatManager(@NotNull final FusionKyori fusion, @NotNull final Path path) {
        this.fusion = fusion;
        this.fileManager = this.fusion.getFileManager();
        this.path = path;
    }

    private PermissionRegistry permissionRegistry;
    private MessageRegistry messageRegistry;
    private UserRegistry userRegistry;
    private DataManager dataManager;

    public abstract void broadcast(@NotNull final Component component, @NotNull final String permission);

    public void broadcast(@NotNull final Component component) {
        broadcast(component, "");
    }

    public abstract boolean isConsoleSender(@NotNull final Audience audience);

    public abstract void registerCommands();

    @Override
    public void start(@NotNull final Audience audience) {
        ChatManagerProvider.register(this);

        this.fileManager.addFolder(this.path.resolve("locale"), FileType.YAML)
                .addFile(this.path.resolve("config.yml"), FileType.YAML)
                .addFile(this.path.resolve("chat.yml"), FileType.YAML)
                .addFile(this.path.resolve("messages.yml"), FileType.YAML);

        this.dataManager = new DataManager(this.path);
        this.dataManager.init();

        this.messageRegistry = new MessageRegistry(this.path, this.fusion);
        this.messageRegistry.init();

        this.userRegistry = new UserRegistry(this);
        this.userRegistry.init(audience);

        this.permissionRegistry = new PermissionRegistry();
        this.permissionRegistry.start();
    }

    @Override
    public void reload() {
        this.fileManager.refresh(false).addFolder(this.path.resolve("locale"), FileType.YAML);

        this.dataManager.init();

        this.messageRegistry.init();
    }

    @Override
    public void stop() {

    }

    @Override
    public @NotNull final PermissionRegistry getPermissionRegistry() {
        return this.permissionRegistry;
    }

    @Override
    public @NotNull final MessageRegistry getMessageRegistry() {
        return this.messageRegistry;
    }

    @Override
    public @NotNull final UserRegistry getUserRegistry() {
        return this.userRegistry;
    }

    @Override
    public @NotNull final Path getPath() {
        return this.path;
    }

    public @NotNull final DataManager getDataManager() {
        return this.dataManager;
    }

    public @NotNull final FileManager getFileManager() {
        return this.fileManager;
    }

    public @NotNull FusionCore getFusion() {
        return this.fusion;
    }
}