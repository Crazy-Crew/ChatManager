package com.ryderbelserion.chatmanager.common.registry.databases.types;

import com.ryderbelserion.chatmanager.common.registry.databases.interfaces.IConnector;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;

public abstract class HikariConnectionFactory implements IConnector {

    protected HikariDataSource source;

    protected abstract String getDriver();

    protected abstract String getIdentifier();

    public void init(@NotNull final CommentedConfigurationNode config) {
        final HikariConfig hikari = new HikariConfig();

        properties(hikari, config);

        hikari.setDriverClassName(getDriver());

        hikari.setJdbcUrl("jdbc:%s://%s:%s/%s".formatted(
                getIdentifier(),
                config.node("root", "storage", "connection", "url").getString(""),
                config.node("root", "storage", "connection", "port").getInt(5432),
                config.node("root", "storage", "database").getString("")
        ));

        hikari.setUsername(config.node("root", "storage", "username").getString(""));
        hikari.setPassword(config.node("root", "storage", "password").getString(""));

        this.source = new HikariDataSource(hikari);
    }
}