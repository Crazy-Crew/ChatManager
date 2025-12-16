package com.ryderbelserion.chatmanager.common.registry.databases;

import com.ryderbelserion.chatmanager.common.registry.databases.interfaces.IConnector;
import com.ryderbelserion.chatmanager.common.registry.databases.types.flatfile.JsonConnector;
import com.ryderbelserion.chatmanager.common.registry.databases.types.flatfile.SqliteConnector;
import com.ryderbelserion.chatmanager.common.enums.Files;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.nio.file.Path;

public class DataManager {

    private IConnector connector;
    private String currentType;
    private final Path path;

    public DataManager(@NotNull final Path path) {
        this.path = path;
    }

    public final void init() {
        final CommentedConfigurationNode config = Files.config.getYamlConfig();

        final String type = config.node("root", "storage", "type").getString("SQLITE").toLowerCase();

        if (this.connector != null && !this.currentType.equalsIgnoreCase(type)) { // if current storage type is not the type in the config. stop the connection
            this.connector.stop();
        }

        switch (type) {
            case "sqlite" -> {
                this.connector = new SqliteConnector(this.path.resolve("users.db"));
                this.connector.init(config);
            }

            case "postgresql" -> {
                //this.connector = new PostgresConnector();
                //this.connector.init(config);

                //todo() postgres
            }

            case "mariadb" -> {
                //todo() mariadb
            }

            case "json" -> {
                this.connector = new JsonConnector(this.path.resolve("users.json"));
                this.connector.init(config);
            }
        }

        this.currentType = type;
    }

    public @NotNull final IConnector getConnector() {
        return this.connector;
    }
}