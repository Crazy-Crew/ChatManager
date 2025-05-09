package com.ryderbelserion.chatmanager.managers;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserManager {

    private final Map<UUID, PaperUser> users = new HashMap<>();

    public void addUser(@NotNull final Player player) {
        final PaperUser user = new PaperUser(player);

        user.setLocale(player.locale());

        this.users.put(player.getUniqueId(), user);
    }

    public void removeUser(@NotNull final Player player) {
        final PaperUser user = this.users.remove(player.getUniqueId());

        //todo() anything we need to.
    }

    public @NotNull Optional<PaperUser> getUser(@NotNull final UUID uuid) {
        return this.users.get(uuid) == null ? Optional.empty() : Optional.of(this.users.get(uuid));
    }

    public @NotNull Optional<PaperUser> getUser(@NotNull final Player player) {
        return getUser(player.getUniqueId());
    }
}