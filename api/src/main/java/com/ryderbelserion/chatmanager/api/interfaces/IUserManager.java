package com.ryderbelserion.chatmanager.api.interfaces;

import com.ryderbelserion.chatmanager.api.objects.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;

public interface IUserManager<P> {

    void addUser(@NotNull final P user);

    void removeUser(@NotNull final P user);

    @Nullable User getUser(@NotNull final UUID uuid);

}