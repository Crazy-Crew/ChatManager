package com.ryderbelserion.chatmanager.cache.objects;

import org.bukkit.entity.Player;

public class User {

    public final Player player;

    public User(final Player player) {
        this.player = player;
    }

    public String locale = "en-US";

    public boolean isStaffChat = false;

    // These settings, do not get stored to the data.yml, but are kept here for ease of access. transient anyway because meh
    public transient boolean isBlockingCommands = false;

    public transient boolean isBlockingChat = false;

    public transient String previousCommand = "";
    public transient int commandDelay = 0;

    public transient String previousMessage = "";
    public transient int chatDelay = 0;


    public final boolean hasPermission(final String permission) {
        return this.player.hasPermission(permission);
    }
}