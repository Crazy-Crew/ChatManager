package com.ryderbelserion.chatmanager.cache.objects;

import java.util.UUID;

public class User {

    public final UUID uuid;

    public User(final UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isBlockingCommands = false;

    public boolean isStaffChat = false;

    // These settings, do not get stored to the data.yml, but are kept here for ease of access. transient anyway because meh
    public transient boolean isBlockingCommands = false;

    public transient boolean isBlockingChat = false;

    public transient String previousCommand = "";
    public transient int commandDelay = 0;

    public transient String previousMessage = "";
    public transient int chatDelay = 0;
}