package com.ryderbelserion.chatmanager.common.registry.databases.types.flatfile;

import com.ryderbelserion.chatmanager.common.objects.User;
import com.ryderbelserion.chatmanager.common.registry.databases.constants.UserSchema;
import com.ryderbelserion.chatmanager.common.registry.databases.types.HikariConnectionFactory;
import com.ryderbelserion.fusion.core.api.FusionProvider;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.zaxxer.hikari.HikariConfig;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class SqliteConnector extends HikariConnectionFactory {

    private final FusionKyori fusion = (FusionKyori) FusionProvider.getInstance();
    private final Path path;

    public SqliteConnector(@NotNull final Path path) {
        this.path = path;
    }

    @Override
    public String getImpl() {
        return "SQLite";
    }

    @Override
    public void properties(@NotNull final HikariConfig hikari, @NotNull final CommentedConfigurationNode config) {
        super.properties(hikari, config);

        hikari.setConnectionInitSql("PRAGMA foreign_keys = ON;");
        hikari.setConnectionTestQuery("PRAGMA journal_mode=WAL;");
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

        super.init(config);

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
    public User getUser(@NotNull final Audience audience) {
        final User user = new User(audience);

        final Optional<Locale> locale = audience.get(Identity.LOCALE);

        locale.ifPresent(user::setLocale);

        return user;
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
        return "jdbc:sqlite:%s".formatted(getPath().toString());
    }

    @Override
    public String getIdentifier() {
        return "SQLite";
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
    public boolean isTableValid(@NotNull final Connection connection, @NotNull final String table) {
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