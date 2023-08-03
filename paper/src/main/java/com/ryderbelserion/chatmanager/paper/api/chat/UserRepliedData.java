package com.ryderbelserion.chatmanager.paper.api.chat;

import java.util.*;

public class UserRepliedData {

    private final HashMap<UUID, UUID> replies = new HashMap<>();

    public void addUser(UUID uuid, UUID other) {
        if (!containsUser(uuid)) replies.put(uuid, other);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) replies.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return replies.containsKey(uuid);
    }

    public UUID getUser(UUID uuid) {
        return replies.get(uuid);
    }

    public Map<UUID, UUID> getUsers() {
        return Collections.unmodifiableMap(replies);
    }
}