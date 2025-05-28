package com.ryderbelserion.chatmanager.paper.api;

import com.ryderbelserion.chatmanager.api.interfaces.IUserManager;
import com.ryderbelserion.chatmanager.paper.api.objects.PaperUser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaperUserManager implements IUserManager<Player> {

    private final Map<UUID, PaperUser> users = new HashMap<>();

    @Override
    public void addUser(@NotNull final Player player) {
        this.users.put(player.getUniqueId(), new PaperUser(player));
    }

    @Override
    public void removeUser(@NotNull final Player player) {
        this.users.remove(player.getUniqueId());
    }

    @Override
    public @Nullable PaperUser getUser(@NotNull final UUID uuid) {
        return this.users.get(uuid);
    }
}