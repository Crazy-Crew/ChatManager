package com.ryderbelserion.chatmanager.v1.api.cooldowns;

import org.bukkit.scheduler.BukkitRunnable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownTask {

    private final HashMap<UUID, BukkitRunnable> map = new HashMap<>();

    public void addUser(UUID uuid, BukkitRunnable runnable) {
        if (!containsUser(uuid)) map.put(uuid, runnable);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) map.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return map.containsKey(uuid);
    }

    public Map<UUID, BukkitRunnable> getUsers() {
        return Collections.unmodifiableMap(map);
    }
}