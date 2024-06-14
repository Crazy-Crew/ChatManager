package com.ryderbelserion.chatmanager.api.chat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SpyChatData {

    private final HashSet<UUID> users = new HashSet<>();

    public void addUser(UUID uuid) {
        if (!containsUser(uuid)) users.add(uuid);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) users.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return users.contains(uuid);
    }

    public Set<UUID> getUsers() {
        return Collections.unmodifiableSet(users);
    }
}