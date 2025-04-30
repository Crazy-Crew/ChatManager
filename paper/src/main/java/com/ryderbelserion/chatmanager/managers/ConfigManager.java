package com.ryderbelserion.chatmanager.managers;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.configs.locale.RootKeys;
import com.ryderbelserion.fusion.core.api.exceptions.FusionException;
import com.ryderbelserion.fusion.core.managers.files.FileManager;
import com.ryderbelserion.fusion.core.managers.files.types.JaluCustomFile;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.nio.file.Path;

public class ConfigManager {

    private static final ChatManager plugin = ChatManager.get();

    private static final Path path = plugin.getDataPath();

    private static final FileManager fileManager = plugin.getFileManager();

    public static void load() {
        fileManager.addFolder(path.resolve("locale"), consumer -> consumer.configurationData(RootKeys.class), null, false, false)
                .addFile(path.resolve("messages.yml"), consumer -> consumer.configurationData(RootKeys.class), null, false, true);
    }

    public static void reload() {
        fileManager.reload().addFolder(path.resolve("locale"), consumer -> consumer.configurationData(RootKeys.class), null, false, true);
    }

    public static SettingsManager getLocale(@NotNull final String locale) {
        @Nullable final JaluCustomFile customFile = fileManager.getJaluFile(path.resolve(locale));

        if (customFile == null) {
            return getLocale();
        }

        return customFile.getConfig();
    }

    public static SettingsManager getLocale() {
        @Nullable final JaluCustomFile customFile = fileManager.getJaluFile(path.resolve("messages.yml"));

        if (customFile == null) {
            throw new FusionException("The messages.yml file cannot be found.");
        }

        return customFile.getConfig();
    }
}