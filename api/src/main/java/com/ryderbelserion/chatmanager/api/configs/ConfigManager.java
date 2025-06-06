package com.ryderbelserion.chatmanager.api.configs;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.chatmanager.api.configs.types.ConfigKeys;
import com.ryderbelserion.chatmanager.api.configs.types.locale.ErrorKeys;
import com.ryderbelserion.chatmanager.api.configs.types.locale.RootKeys;
import com.ryderbelserion.chatmanager.api.configs.types.chat.ChatKeys;
import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.api.exceptions.FusionException;
import com.ryderbelserion.fusion.core.files.FileAction;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.fusion.core.files.types.JaluCustomFile;
import com.ryderbelserion.fusion.core.utils.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private static final YamlFileResourceOptions options = YamlFileResourceOptions.builder().indentationSize(2).build();

    private static final FusionCore layout = FusionCore.Provider.get();

    private static final FileManager fileManager = layout.getFileManager();

    private static final Path path = layout.getPath();

    public static void load() {
        // extract first, because I really dislike how ConfigMe formats it.
        FileUtils.extract("locale", path, new ArrayList<>() {{
            add(FileAction.FOLDER);
        }});

        List.of(
                "config.yml",
                "messages.yml",
                "chat.yml"
        ).forEach(file -> FileUtils.extract(file, path, new ArrayList<>()));

        fileManager.addFolder(path.resolve("locale"), builder -> builder.configurationData(RootKeys.class, ErrorKeys.class), new ArrayList<>(), options)
                .addFile(path.resolve("config.yml"), builder -> builder.configurationData(ConfigKeys.class), new ArrayList<>(), options)
                .addFile(path.resolve("messages.yml"), builder -> builder.configurationData(RootKeys.class, ErrorKeys.class), new ArrayList<>(), options)
                .addFile(path.resolve("chat.yml"), builder -> builder.configurationData(ChatKeys.class), new ArrayList<>(), options);
    }

    public static void reload() {
        fileManager.refresh(false); // reloads all files!

        // we must run this because of object mapping when accounting for new files.
        fileManager.addFolder(path.resolve("locale"), builder -> builder.configurationData(RootKeys.class, ErrorKeys.class), new ArrayList<>() {{
            add(FileAction.RELOAD);
        }}, options);
    }

    public static SettingsManager getLocale(@NotNull final String locale) {
        @Nullable final JaluCustomFile customFile = fileManager.getJaluFile(path.resolve("locale").resolve(locale));

        if (customFile == null) {
            return getLocale();
        }

        return customFile.getConfiguration();
    }

    public static SettingsManager getConfig() {
        @Nullable final JaluCustomFile customFile = fileManager.getJaluFile(path.resolve("config.yml"));

        if (customFile == null) {
            throw new FusionException("The config.yml file cannot be found.");
        }

        return customFile.getConfiguration();
    }

    public static SettingsManager getChat() {
        @Nullable final JaluCustomFile customFile = fileManager.getJaluFile(path.resolve("chat.yml"));

        if (customFile == null) {
            throw new FusionException("The chat.yml file cannot be found.");
        }

        return customFile.getConfiguration();
    }

    public static SettingsManager getLocale() {
        @Nullable final JaluCustomFile customFile = fileManager.getJaluFile(path.resolve("messages.yml"));

        if (customFile == null) {
            throw new FusionException("The messages.yml file cannot be found.");
        }

        return customFile.getConfiguration();
    }
}