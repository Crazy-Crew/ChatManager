package com.ryderbelserion.chatmanager.paper.api.objects;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.objects.User;
import com.ryderbelserion.chatmanager.api.configs.ConfigManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PaperUser extends User {

    public PaperUser(@NotNull final Player player) {
        super(player);
    }

    @Override
    public SettingsManager locale() {
        return ConfigManager.getLocale(getLocale());
    }
}