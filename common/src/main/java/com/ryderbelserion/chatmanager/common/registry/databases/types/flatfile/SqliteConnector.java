package com.ryderbelserion.chatmanager.common.registry.databases.types.flatfile;

import com.ryderbelserion.chatmanager.common.registry.databases.constants.UserSchema;
import com.ryderbelserion.chatmanager.common.registry.databases.interfaces.IConnector;
import com.ryderbelserion.fusion.core.api.FusionProvider;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class SqliteConnector implements IConnector {

    private final FusionKyori fusion = (FusionKyori) FusionProvider.getInstance();
    private final Path path;

    private HikariDataSource source;

    public SqliteConnector(@NotNull final Path path) {
        this.path = path;
    }

    @Override
    public String getImpl() {
        return "SQLite";
    }

    @Override
    public void init(@NotNull final CommentedConfigurationNode config) {
        try {
            if (!Files.exists(this.path)) {
                Files.createFile(this.path);
            }
        } catch (final IOException exception) {
            this.fusion.log("warn", "Failed to create the file requested!", exception);
        }

        final HikariConfig hikari = new HikariConfig();

        hikari.setConnectionInitSql("PRAGMA foreign_keys = ON;");
        hikari.setConnectionTestQuery("PRAGMA journal_mode=WAL;");

        properties(hikari, config);

        hikari.setJdbcUrl(url());

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
    public String url() {
        return String.format("jdbc:sqlite:%s", getPath().toString());
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
    public Path getPath() {
        return this.path;
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