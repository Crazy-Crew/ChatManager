package com.ryderbelserion.chatmanager.api.chat.logging;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PreviousCmdData {

    private final HashMap<UUID, String> previousMessages = new HashMap<>();

    public void addUser(UUID uuid, String message) {
        if (!containsUser(uuid)) previousMessages.put(uuid, message);
    }

    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) previousMessages.remove(uuid);
    }

    public boolean containsUser(UUID uuid) {
        return getUsers().containsKey(uuid);
    }

    public String getMessage(UUID uuid) {
        return getUsers().get(uuid);
    }

    public Map<UUID, String> getUsers() {
        return Collections.unmodifiableMap(previousMessages);
    }
}