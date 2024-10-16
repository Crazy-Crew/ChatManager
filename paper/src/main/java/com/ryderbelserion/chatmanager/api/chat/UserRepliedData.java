package com.ryderbelserion.chatmanager.api.chat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepliedData {

    private final Map<UUID, UUID> replies = new HashMap<>();

    public void addUser(UUID uuid, UUID other) {
        replies.put(uuid, other);
    }

    public void removeUser(UUID uuid) {
        replies.remove(uuid);
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