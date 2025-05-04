package com.ryderbelserion.chatmanager.enums.commands;

import com.ryderbelserion.chatmanager.enums.Permissions;
import org.bukkit.entity.Player;

public enum RadiusType {

    LOCAL_CHAT("Local Chat", Permissions.COMMAND_CHATRADIUS_LOCAL),
    GLOBAL_CHAT("Global Chat", Permissions.COMMAND_CHATRADIUS_GLOBAL),
    WORLD_CHAT("World Chat", Permissions.COMMAND_CHATRADIUS_WORLD);

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

    public final String getType() {
        return this.type;
    }
}