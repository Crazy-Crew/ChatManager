package com.ryderbelserion.chatmanager.common.registry;

import com.ryderbelserion.chatmanager.api.interfaces.registry.IUserRegistry;
import com.ryderbelserion.chatmanager.common.ChatManager;
import com.ryderbelserion.chatmanager.common.objects.User;
import com.ryderbelserion.chatmanager.common.registry.databases.DataManager;
import com.ryderbelserion.chatmanager.common.registry.databases.interfaces.IConnector;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserRegistry implements IUserRegistry<User> {

    private final Map<UUID, User> users = new HashMap<>();

    private final ChatManager plugin;
    private final DataManager dataManager;

    public UserRegistry(@NotNull final ChatManager plugin) {
        this.plugin = plugin;
        this.dataManager = this.plugin.getDataManager();
    }

    public void init(@NotNull final Audience audience) {
        if (this.plugin.isConsoleSender(audience)) {
            this.users.put(ChatManager.console, new User(audience));
        }
    }

    @Override
    public void addUser(@NotNull final Audience audience) {
        if (this.plugin.isConsoleSender(audience)) {
            return;
        }

        final Optional<UUID> uuid = audience.get(Identity.UUID);

        uuid.ifPresent(value -> {
            final IConnector connector = this.dataManager.getConnector();

            this.users.put(value, connector.getUser(audience));
        });
    }

    @Override
    public void removeUser(@NotNull final Audience audience) {
        if (this.plugin.isConsoleSender(audience)) {
            return;
        }

        final Optional<UUID> uuid = audience.get(Identity.UUID);

        uuid.ifPresent(this.users::remove);
    }

    @Override
    public final boolean hasUser(@NotNull final UUID uuid) {
        return this.users.containsKey(uuid);
    }

    @Override
    public @NotNull final User getUser(@NotNull final Audience audience) {
        if (this.plugin.isConsoleSender(audience)) {
            return this.users.get(ChatManager.console);
        }

        final Optional<UUID> optional = audience.get(Identity.UUID);

        //noinspection OptionalGetWithoutIsPresent
        return this.users.get(optional.get());
    }

    @Override
    public @NotNull final Map<UUID, User> getUsers() {
        return this.users;
    }
}