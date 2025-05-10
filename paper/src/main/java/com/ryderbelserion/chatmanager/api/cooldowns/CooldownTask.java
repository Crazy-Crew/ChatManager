package com.ryderbelserion.chatmanager.api.cooldowns;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownTask {

    private final Map<UUID, ScheduledTask> map = new HashMap<>();

    public void addUser(UUID uuid, ScheduledTask runnable) {
        if (!containsUser(uuid)) map.put(uuid, runnable);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) map.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return map.containsKey(uuid);
    }

    public Map<UUID, ScheduledTask> getUsers() {
        return Collections.unmodifiableMap(map);
    }
}