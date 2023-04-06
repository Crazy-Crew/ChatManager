package com.ryderbelserion.chatmanager.api.chat;

import java.util.*;

public class UserRepliedData {

    private final HashMap<UUID, UUID> userReplied = new HashMap<>();

    public void addUser(UUID uuid, UUID other) {
        if (!containsUser(uuid)) userReplied.put(uuid, other);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) userReplied.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return getUsers().containsKey(uuid);
    }

    public UUID getUser(UUID uuid) {
        return getUsers().get(uuid);
    }

    public Map<UUID, UUID> getUsers() {
        return Collections.unmodifiableMap(userReplied);
    }
}