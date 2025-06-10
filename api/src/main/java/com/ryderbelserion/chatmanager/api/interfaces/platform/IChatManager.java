package com.ryderbelserion.chatmanager.api.interfaces.platform;

import com.ryderbelserion.chatmanager.api.interfaces.registry.IMessageRegistry;
import com.ryderbelserion.chatmanager.api.interfaces.registry.IUserRegistry;
import com.ryderbelserion.fusion.core.files.FileManager;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface IChatManager {

    @NotNull FileManager getFileManager();

    @NotNull IUserRegistry getUserRegistry();

    @NotNull IMessageRegistry getMessageRegistry();

    void broadcast(@NotNull final Component component, @NotNull final String permission);

    void broadcast(@NotNull final Component component);

    void start();

    void reload();

    void stop();

}