package com.ryderbelserion.chatmanager.utils;

import com.ryderbelserion.vital.paper.api.enums.Support;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Map;

public class MiscUtils {

    public static String populatePlaceholders(@Nullable final CommandSender sender, @NotNull String line, @NotNull final Map<String, String> placeholders) {
        if (sender != null && Support.placeholder_api.isEnabled()) {
            if (sender instanceof Player player) {
                line = PlaceholderAPI.setPlaceholders(player, line);
            }
        }

        if (!placeholders.isEmpty()) {
            for (final Map.Entry<String, String> placeholder : placeholders.entrySet()) {

                if (placeholder != null) {
                    final String key = placeholder.getKey();
                    final String value = placeholder.getValue();

                    if (key != null && value != null) {
                        line = line.replace(key, value).replace(key.toLowerCase(), value);
                    }
                }
            }
        }

        return line;
    }
}