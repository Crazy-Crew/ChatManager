package com.ryderbelserion.chatmanager.common.registry.databases.interfaces;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.nio.file.Path;

public interface IConnector {

    void init(@NotNull final CommentedConfigurationNode config);

    void stop();

    default void reload() {

    }

    default Path getPath() {
        return null;
    }

    String getImpl();

}