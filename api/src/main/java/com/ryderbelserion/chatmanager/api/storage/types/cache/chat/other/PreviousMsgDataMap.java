package com.ryderbelserion.chatmanager.api.storage.types.cache.chat.other;

import com.ryderbelserion.chatmanager.api.storage.interfaces.DataMapController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PreviousMsgDataMap implements DataMapController {

    private final HashMap<UUID, String> map = new HashMap<>();

    @Override
    public void addUser(UUID uuid, String message) {
        if (!containsUser(uuid)) map.put(uuid, message);
    }

    @Override
    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) map.remove(uuid);
    }

    @Override
    public boolean containsUser(UUID uuid) {
        return map.containsKey(uuid);
    }

    @Override
    public String getMessage(UUID uuid) {
        return map.get(uuid);
    }

    @Override
    public Map<UUID, String> getUsers() {
        return Collections.unmodifiableMap(map);
    }
}