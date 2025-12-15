package com.ryderbelserion.chatmanager.common.registry.databases.types.cloud;

import com.ryderbelserion.chatmanager.common.registry.databases.constants.UserSchema;
import com.ryderbelserion.chatmanager.common.registry.databases.types.HikariConnectionFactory;
import com.ryderbelserion.fusion.core.api.FusionProvider;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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