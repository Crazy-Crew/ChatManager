package com.ryderbelserion.chatmanager.common.registry.databases;

import com.ryderbelserion.chatmanager.common.registry.databases.interfaces.IConnector;
import com.ryderbelserion.chatmanager.common.registry.databases.types.cloud.PostgresConnector;
import com.ryderbelserion.chatmanager.common.registry.databases.types.flatfile.SqliteConnector;
import com.ryderbelserion.chatmanager.common.enums.Files;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.nio.file.Path;

public class DataManager {

    private IConnector connector;
    private final Path path;

    public DataManager(@NotNull final Path path) {
        this.path = path;
    }

    public final void init() {
        final CommentedConfigurationNode config = Files.config.getYamlConfig();

        final String type = config.node("root", "database", "type").getString("SQLITE");

        if (this.connector != null) {
            return;
        }

        switch (type.toLowerCase()) {
            case "sqlite" -> this.connector = new SqliteConnector(this.path.resolve("users.db")).start(config);

            case "postgresql" -> this.connector = new PostgresConnector().start(config);

            case "mariadb" -> {
                //todo() mariadb
            }
        }
    }

    public @NotNull final IConnector getConnector() {
        return this.connector;
    }
}