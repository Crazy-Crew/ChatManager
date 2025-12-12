package com.ryderbelserion.chatmanager.common.registry.databases.interfaces;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;

import java.nio.file.Path;
import java.sql.Connection;

public interface IConnector {

    IConnector start(@NotNull final CommentedConfigurationNode config);

    void stop();

    boolean isRunning();

    default String url() {
        return "";
    }

    Connection getConnection();

    default Path getPath() {
        return null;
    }

    boolean tableExists(@NotNull final Connection connection, @NotNull final String table);

}