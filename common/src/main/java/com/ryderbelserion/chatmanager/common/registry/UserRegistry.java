package com.ryderbelserion.chatmanager.common.registry;

import com.ryderbelserion.chatmanager.api.interfaces.registry.IUserRegistry;
import com.ryderbelserion.chatmanager.common.ChatManager;
import com.ryderbelserion.chatmanager.common.objects.User;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserRegistry implements IUserRegistry<User> {

    private final Map<UUID, User> users = new HashMap<>();

    private final ChatManager plugin;

    public UserRegistry(@NotNull final ChatManager plugin) {
        this.plugin = plugin;
    }

    public void init(@NotNull final Audience audience) {
        if (this.plugin.isConsoleSender(audience)) {
            this.users.put(ChatManager.console, new User(this.plugin, audience));
        }
    }

    @Override
    public void addUser(@NotNull final Audience audience) {
        if (this.plugin.isConsoleSender(audience)) {
            return;
        }

        final Optional<UUID> uuid = audience.get(Identity.UUID);

        uuid.ifPresent(value -> {
            final User user = new User(this.plugin, audience);

            final Optional<Locale> locale = audience.get(Identity.LOCALE);

            locale.ifPresent(user::setLocale);

            this.users.put(value, user);
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