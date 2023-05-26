package com.ryderbelserion.chatmanager.api.storage.interfaces;

import java.util.Map;
import java.util.UUID;

public interface DataMapController {

    void addUser(UUID uuid, String message);

    void removeUser(UUID uuid);

    String getMessage(UUID uuid);

    boolean containsUser(UUID uuid);

    Map<UUID, String> getUsers();

}