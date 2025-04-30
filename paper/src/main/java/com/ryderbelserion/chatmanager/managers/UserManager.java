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
        this.users.put(player.getUniqueId(), new PaperUser(player));
    }

    public void removeUser(@NotNull final Player player) {
        final PaperUser user = this.users.remove(player.getUniqueId());

        //todo() anything we need to.
    }

    public Optional<PaperUser> getUser(@NotNull final UUID uuid) {
        return Optional.ofNullable(this.users.get(uuid));
    }

    public Optional<PaperUser> getUser(@NotNull final Player player) {
        return Optional.of(this.users.get(player.getUniqueId()));
    }
}