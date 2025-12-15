package com.ryderbelserion.chatmanager.api.interfaces.platform;

import com.ryderbelserion.chatmanager.api.interfaces.registry.IMessageRegistry;
import com.ryderbelserion.chatmanager.api.interfaces.registry.IUserRegistry;
import com.ryderbelserion.fusion.core.api.interfaces.permissions.IPermissionRegistry;
import com.ryderbelserion.fusion.core.api.interfaces.permissions.enums.Mode;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public interface IChatManager {

    @NotNull <P> IPermissionRegistry<P> getPermissionRegistry();

    @NotNull <U> IUserRegistry<U> getUserRegistry();

    @NotNull IMessageRegistry getMessageRegistry();

    boolean hasPermission(@NotNull final Audience audience, @NotNull final String permission);

    void registerPermission(@NotNull final Mode mode, @NotNull final String parent, @NotNull final String description, @NotNull final Map<String, Boolean> children);

    default void registerPermission(@NotNull final Mode mode, @NotNull final String parent, @NotNull final String description) {
        registerPermission(mode, parent, description, new HashMap<>());
    }

    void unregisterPermission(@NotNull final String parent);

    boolean isPermissionRegistered(@NotNull final String parent);

    void broadcast(@NotNull final Component component, @NotNull final String permission);

    void broadcast(@NotNull final Component component);

    void start(@NotNull final Audience audience);

    void registerCommands();

    void reload();

    void stop();

    Path getPath();

}