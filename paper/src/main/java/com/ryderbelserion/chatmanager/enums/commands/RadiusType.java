package com.ryderbelserion.chatmanager.enums.commands;

import com.ryderbelserion.chatmanager.enums.Permissions;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import java.util.Optional;

public enum RadiusType {

    LOCAL_CHAT("local", Permissions.COMMAND_CHATRADIUS_LOCAL),
    GLOBAL_CHAT("global", Permissions.COMMAND_CHATRADIUS_GLOBAL),
    WORLD_CHAT("world", Permissions.COMMAND_CHATRADIUS_WORLD);

    private final Permissions node;
    private final String type;

    RadiusType(final String type, final Permissions node) {
        this.type = type;
        this.node = node;
    }

    public final boolean hasPermission(final Player player) {
        return getNode().hasPermission(player);
    }

    public final Permissions getNode() {
        return this.node;
    }

    public final String getPrettyType() {
        return StringUtils.capitalize(this.type);
    }

    public final String getType() {
        return this.type;
    }

    public static RadiusType getType(final String key) {
        RadiusType result = null;

        for (final RadiusType radius : values()) {
            if (!radius.getType().equalsIgnoreCase(key)) {
                continue;
            }

            result = radius;
        }

        return Optional.ofNullable(result).orElse(RadiusType.WORLD_CHAT);
    }
}