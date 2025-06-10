package com.ryderbelserion.chatmanager.common.objects;

import com.ryderbelserion.chatmanager.api.interfaces.IUser;
import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.fusion.core.files.types.YamlCustomFile;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.fusion.kyori.components.KyoriLogger;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

public class User implements IUser {

    private final FusionKyori kyori = (FusionKyori) FusionCore.Provider.get();

    private final FileManager fileManager = this.kyori.getFileManager();

    private final KyoriLogger logger = this.kyori.getLogger();

    private final Path path = this.kyori.getPath();

    private final Audience audience;

    public User(@NotNull final Audience audience) {
        this.audience = audience;
    }

    public CommentedConfigurationNode locale() {
        final YamlCustomFile customFile = this.fileManager.getYamlFile(this.path.resolve("locale").resolve(String.format("%s.yml", getLocale())));

        if (customFile == null) {
            return Objects.requireNonNull(this.fileManager.getYamlFile(this.path.resolve("messages.yml"))).getConfiguration();
        }

        return customFile.getConfiguration();
    }

    private String locale = "en-US";

    @Override
    public void setLocale(@NotNull final Locale locale) {
        final String country = locale.getCountry();
        final String language = locale.getLanguage();

        this.locale = String.format("%s-%s", language, country);

        this.logger.warn("Locale Debug: Country: {}, Language: {}", country, language);
    }

    @Override
    public @NotNull final Audience getAudience() {
        return this.audience;
    }

    @Override
    public @NotNull final String getLocale() {
        return this.locale;
    }
}