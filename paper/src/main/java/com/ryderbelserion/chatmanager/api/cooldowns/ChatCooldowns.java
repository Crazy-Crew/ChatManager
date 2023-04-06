package com.ryderbelserion.chatmanager.api.cooldowns;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatCooldowns {

    private final HashMap<UUID, Integer> chatCooldowns = new HashMap<>();

    public void addUser(UUID uuid, Integer time) {
        if (!containsUser(uuid)) chatCooldowns.put(uuid, time);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) chatCooldowns.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return getUsers().containsKey(uuid);
    }

    public int getTime(UUID uuid) {
        return getUsers().get(uuid);
    }

    public Map<UUID, Integer> getUsers() {
        return Collections.unmodifiableMap(chatCooldowns);
    }
}