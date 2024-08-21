package com.ryderbelserion.chatmanager.cache.objects;

import java.util.UUID;

public class User {

    public final UUID uuid;

    public User(final UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isBlockingCommands = false;

    public boolean isBlockingChat = false;

    public int chatDelay = 0;
}