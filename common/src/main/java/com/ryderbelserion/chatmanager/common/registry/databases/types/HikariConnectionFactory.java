package com.ryderbelserion.chatmanager.common.registry.databases.types;

import com.ryderbelserion.chatmanager.common.registry.databases.interfaces.IConnector;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.sql.Connection;

public abstract class HikariConnectionFactory implements IConnector {

    protected HikariDataSource source;

    protected String getDriver() {
        return "";
    }

    protected abstract String getIdentifier();

    protected abstract Connection getConnection();

    protected abstract boolean isRunning();

    protected abstract boolean isTableValid(@NotNull final Connection connection, @NotNull final String table);

    protected abstract String url();

    public void properties(@NotNull final HikariConfig hikari, @NotNull final CommentedConfigurationNode config) {
        hikari.setPoolName("chatmanager-hikari");

        hikari.setMaximumPoolSize(config.node("root", "storage", "pool-settings", "maximum-pool-size").getInt(10));
        hikari.setMinimumIdle(config.node("root", "storage", "pool-settings", "minimum-idle").getInt(10));
        hikari.setMaxLifetime(config.node("root", "storage", "pool-settings", "maximum-lifetime").getLong(1800000));

        hikari.setKeepaliveTime(config.node("root", "storage", "pool-settings", "keepalive-time").getLong(0));

        hikari.setConnectionTimeout(config.node("root", "storage", "pool-settings", "connection-timeout").getLong(5000));
    }

    public void init(@NotNull final CommentedConfigurationNode config) {
        final HikariConfig hikari = new HikariConfig();

        properties(hikari, config);

        final String driver = getDriver();

        if (!driver.isBlank()) {
            hikari.setDriverClassName(driver);
        }

        hikari.setJdbcUrl(url());

        hikari.setUsername(config.node("root", "storage", "username").getString(""));
        hikari.setPassword(config.node("root", "storage", "password").getString(""));

        this.source = new HikariDataSource(hikari);
    }
}