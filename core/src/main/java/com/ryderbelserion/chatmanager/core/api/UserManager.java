package com.ryderbelserion.chatmanager.core.api;

import com.ryderbelserion.chatmanager.core.objects.User;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.Nullable;

public interface UserManager {

    void addUser(final Audience audience);

    void removeUser(final Audience audience);

    @Nullable User getUser(final Audience audience);

}