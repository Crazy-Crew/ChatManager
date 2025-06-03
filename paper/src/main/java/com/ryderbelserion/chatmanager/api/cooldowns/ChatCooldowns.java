package com.ryderbelserion.chatmanager.api.cooldowns;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatCooldowns {

    private final Map<UUID, Integer> map = new HashMap<>();

    public void addUser(UUID uuid, int time) {
        if (!containsUser(uuid)) map.put(uuid, time);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) map.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return map.containsKey(uuid);
    }

    public int getTime(UUID uuid) {
        return containsUser(uuid) ? map.get(uuid) : 0;
    }

    public void subtract(UUID uuid) {
        map.computeIfPresent(uuid, ((id, integer) -> integer-1));
    }

    public Map<UUID, Integer> getUsers() {
        return Collections.unmodifiableMap(map);
    }
}