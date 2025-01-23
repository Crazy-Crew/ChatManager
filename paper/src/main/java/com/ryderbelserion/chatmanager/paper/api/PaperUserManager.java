package com.ryderbelserion.chatmanager.paper.api;

import com.ryderbelserion.chatmanager.core.api.IUserManager;
import com.ryderbelserion.chatmanager.core.objects.User;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaperUserManager implements IUserManager {

    private final Map<UUID, User> users = new HashMap<>();

    @Override
    public void addUser(final Audience audience) {
        this.users.putIfAbsent(audience.getOrDefault(Identity.UUID, null), new User(audience));
    }

    @Override
    public void removeUser(final Audience audience) {
        this.users.remove(audience.getOrDefault(Identity.UUID, null));
    }

    @Override
    public @Nullable User getUser(final Audience audience) {
        return this.users.getOrDefault(audience.getOrDefault(Identity.UUID, null), null);
    }
}