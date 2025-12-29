package com.ryderbelserion.chatmanager.common.registry.databases.interfaces;

import com.ryderbelserion.chatmanager.common.objects.User;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.nio.file.Path;

public interface IConnector {

    void init(@NotNull final CommentedConfigurationNode config);

    User getUser(@NotNull final Audience audience);

    void stop();

    default void reload() {

    }

    default Path getPath() {
        return null;
    }

    String getImpl();

}