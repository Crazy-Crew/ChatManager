package com.ryderbelserion.chatmanager.common.registry.databases.types.cloud;

import com.ryderbelserion.chatmanager.common.registry.databases.constants.UserSchema;
import com.ryderbelserion.chatmanager.common.registry.databases.interfaces.IConnector;
import com.ryderbelserion.fusion.core.api.FusionProvider;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class PostgresConnector implements IConnector {

    private final FusionKyori fusion = (FusionKyori) FusionProvider.getInstance();

    private HikariDataSource source;

    @Override
    public IConnector start(@NotNull final CommentedConfigurationNode config) {
        final HikariConfig hikari = new HikariConfig();

        hikari.setConnectionInitSql("PRAGMA foreign_keys = ON;");
        hikari.setConnectionTestQuery("PRAGMA journal_mode=WAL;");

        hikari.setMaximumPoolSize(config.node("root", "database", "pool-settings", "maximum-pool-size").getInt(10));
        hikari.setMinimumIdle(config.node("root", "database", "pool-settings", "minimum-idle").getInt(10));
        hikari.setMaxLifetime(config.node("root", "database", "pool-settings", "maximum-lifetime").getLong(1800000));

        hikari.setKeepaliveTime(config.node("root", "database", "pool-settings", "keepalive-time").getLong(0));

        hikari.setConnectionTimeout(config.node("root", "database", "pool-settings", "connection-timeout").getLong(5000));

        hikari.setJdbcUrl(config.node("root", "database", "authentication", "").getString(""));

        this.source = new HikariDataSource(hikari);

        CompletableFuture.runAsync(() -> {
           try (final Connection connection = getConnection()) {
               if (connection == null) {
                   this.fusion.log("warn", "The connection is null for SQLITE type!");

                   return;
               }

               try (final PreparedStatement statement = connection.prepareStatement(UserSchema.create_users_table)) {
                   statement.executeUpdate();
               } catch (final SQLException exception) {
                   this.fusion.log("warn", "Failed to create users table!", exception);
               }
            } catch (final SQLException exception) {
               this.fusion.log("warn", "Failed to execute prepared statement on initialization", exception);
           }
        });

        return this;
    }

    @Override
    public void stop() {
        if (this.source != null) {
            this.source.close();
        }
    }

    @Override
    public boolean isRunning() {
        try {
            final Connection connection = getConnection();

            return connection != null && !connection.isClosed();
        } catch (final SQLException exception) {
            this.fusion.log("warn", "Connection is currently not running!", exception);

            return false;
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return this.source.getConnection();
        } catch (final SQLException exception) {
            this.fusion.log("warn", "Connection is null!", exception);

            return null;
        }
    }

    @Override
    public boolean tableExists(@NotNull final Connection connection, @NotNull final String table) {
        try (ResultSet resultSet = connection.getMetaData().getTables(
                null,
                null,
                table,
                null
        )) {
            return resultSet.next();
        } catch (final SQLException exception) {
            this.fusion.log("Warn", "Table does not exist!", exception);

            return false;
        }
    }
}