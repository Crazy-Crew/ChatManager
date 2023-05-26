package com.ryderbelserion.chatmanager.api.storage.types.cache.commands.toggles;

import com.ryderbelserion.chatmanager.api.storage.interfaces.DataSetController;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ToggleMentionsData implements DataSetController {

    private final HashSet<UUID> users = new HashSet<>();

    @Override
    public void addUser(UUID uuid) {
        if (!containsUser(uuid)) users.add(uuid);
    }

    @Override
    public void removeUser(UUID uuid) {
        if (containsUser(uuid)) users.remove(uuid);
    }

    @Override
    public boolean containsUser(UUID uuid) {
        return users.contains(uuid);
    }

    @Override
    public Set<UUID> getUsers() {
        return Collections.unmodifiableSet(users);
    }
}