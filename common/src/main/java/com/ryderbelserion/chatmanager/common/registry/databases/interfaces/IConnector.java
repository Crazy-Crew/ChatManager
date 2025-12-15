package com.ryderbelserion.chatmanager.common.registry.databases.interfaces;

import com.zaxxer.hikari.HikariConfig;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.nio.file.Path;
import java.sql.Connection;

public interface IConnector {

    void init(@NotNull final CommentedConfigurationNode config);

    default void properties(@NotNull final HikariConfig hikari, @NotNull final CommentedConfigurationNode config) {
        hikari.setPoolName("chatmanager-hikari");

        hikari.setMaximumPoolSize(config.node("root", "storage", "pool-settings", "maximum-pool-size").getInt(10));
        hikari.setMinimumIdle(config.node("root", "storage", "pool-settings", "minimum-idle").getInt(10));
        hikari.setMaxLifetime(config.node("root", "storage", "pool-settings", "maximum-lifetime").getLong(1800000));

        hikari.setKeepaliveTime(config.node("root", "storage", "pool-settings", "keepalive-time").getLong(0));

        hikari.setConnectionTimeout(config.node("root", "storage", "pool-settings", "connection-timeout").getLong(5000));
    }

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

    String getImpl();

}