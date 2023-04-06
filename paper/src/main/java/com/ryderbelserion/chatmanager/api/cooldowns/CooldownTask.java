package com.ryderbelserion.chatmanager.api.cooldowns;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownTask {

    private final HashMap<UUID, BukkitRunnable> cooldownTasks = new HashMap<>();

    public void addUser(UUID uuid, BukkitRunnable runnable) {
        if (!containsUser(uuid)) cooldownTasks.put(uuid, runnable);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) cooldownTasks.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return getUsers().containsKey(uuid);
    }

    public Map<UUID, BukkitRunnable> getUsers() {
        return Collections.unmodifiableMap(cooldownTasks);
    }
}