package com.ryderbelserion.chatmanager.api.chat.logging;

import java.util.*;

public class PreviousMsgData {

    private final HashMap<UUID, String> map = new HashMap<>();

    public void addUser(UUID uuid, String message) {
        if (!containsUser(uuid)) map.put(uuid, message);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) map.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return map.containsKey(uuid);
    }

    public String getMessage(UUID uuid) {
        return map.get(uuid);
    }

    public Map<UUID, String> getUsers() {
        return Collections.unmodifiableMap(map);
    }
}