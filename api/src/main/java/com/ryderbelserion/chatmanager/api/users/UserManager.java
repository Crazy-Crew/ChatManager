package com.ryderbelserion.chatmanager.api.users;

import com.ryderbelserion.chatmanager.api.users.objects.User;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public abstract class UserManager {

    public abstract Audience getConsoleSender();

    public abstract User getUser(@NotNull final UUID uuid);

    public abstract void addUser(@NotNull final UUID uuid);

}