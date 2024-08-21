package com.ryderbelserion.chatmanager.cache.objects;

import java.util.UUID;

public class User {

    public final UUID uuid;

    public User(final UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isMentionsEnabled = false;
    public boolean isSocialSpy = false;

    public boolean blockCommands = false;
    public boolean blockChat = false;

    public boolean isBot = false;

    public int chatDelay = 0;
}