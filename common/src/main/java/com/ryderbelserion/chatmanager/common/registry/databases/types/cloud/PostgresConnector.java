package com.ryderbelserion.chatmanager.common.registry.databases.types.cloud;

import com.ryderbelserion.chatmanager.common.enums.Files;
import com.ryderbelserion.chatmanager.common.objects.User;
import com.ryderbelserion.chatmanager.common.registry.databases.constants.UserSchema;
import com.ryderbelserion.chatmanager.common.registry.databases.types.HikariConnectionFactory;
import com.ryderbelserion.fusion.core.api.FusionProvider;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class PostgresConnector extends HikariConnectionFactory {

    private final FusionKyori fusion = (FusionKyori) FusionProvider.getInstance();

    @Override
    protected String getDriver() {
        return "org.postgresql.Driver";
    }

    @Override
    protected String getIdentifier() {
        return "postgresql";
    }

    @Override
    public String getImpl() {
        return "PostgreSQL";
    }

    @Override
    public void init(@NotNull CommentedConfigurationNode config) {
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
    protected boolean isTableValid(@NotNull final Connection connection, @NotNull final String table) {
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

    @Override
    protected String url() {
        final CommentedConfigurationNode config = Files.config.getYamlConfig();

        return "jdbc:%s://%s:%s/%s".formatted(
                getIdentifier(),
                config.node("root", "storage", "connection", "url").getString(""),
                config.node("root", "storage", "connection", "port").getInt(5432),
                config.node("root", "storage", "database").getString("")
        );
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
}