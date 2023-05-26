package com.ryderbelserion.chatmanager.api.storage.interfaces;

import java.util.Set;
import java.util.UUID;

public interface DataSetController {

    void addUser(UUID uuid);

    void removeUser(UUID uuid);

    boolean containsUser(UUID uuid);

    Set<UUID> getUsers();

}