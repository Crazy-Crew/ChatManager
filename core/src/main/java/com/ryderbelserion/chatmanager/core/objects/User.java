package com.ryderbelserion.chatmanager.core.objects;

import com.ryderbelserion.core.FusionLayout;
import com.ryderbelserion.core.FusionProvider;
import com.ryderbelserion.core.api.enums.FileType;
import com.ryderbelserion.core.files.types.YamlCustomFile;
import net.kyori.adventure.audience.Audience;

public class User {

    private final FusionLayout layout = FusionProvider.get();

    private final Audience audience;

    private String locale = "en-US";

    public User(final Audience audience) {
        this.audience = audience;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public final Audience getAudience() {
        return this.audience;
    }

    public YamlCustomFile getLocale() {
        return (YamlCustomFile) this.layout.getFileManager().getFile(this.locale + ".yml", FileType.YAML);
    }
}