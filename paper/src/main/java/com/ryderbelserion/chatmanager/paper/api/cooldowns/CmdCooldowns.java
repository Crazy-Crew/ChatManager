package com.ryderbelserion.chatmanager.paper.api.cooldowns;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CmdCooldowns {

    private final HashMap<UUID, Integer> map = new HashMap<>();

    public void addUser(UUID uuid, Integer time) {
        if (!containsUser(uuid)) map.put(uuid, time);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) map.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return map.containsKey(uuid);
    }

    public int getTime(UUID uuid) {
        return map.get(uuid);
    }

    public void subtract(UUID uuid) {
        map.computeIfPresent(uuid, ((id, integer) -> integer-1));
    }

    public Map<UUID, Integer> getUsers() {
        return Collections.unmodifiableMap(map);
    }
}