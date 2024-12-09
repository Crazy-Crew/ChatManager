package com.ryderbelserion.chatmanager.api.users;

import com.ryderbelserion.chatmanager.ChatManagerPaper;
import com.ryderbelserion.chatmanager.api.users.objects.PaperUser;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaperUserManager extends UserManager {

    private final Server server;

    public PaperUserManager(ChatManagerPaper instance) {
        this.server = instance.getPlugin().getServer();
    }

    private final Map<UUID, PaperUser> users = new HashMap<>();

    @Override
    public @NotNull final Audience getConsoleSender() {
        return this.server.getConsoleSender();
    }

    @Override
    public @NotNull final PaperUser getUser(@NotNull final UUID uuid) {
        return this.users.get(uuid);
    }

    @Override
    public void addUser(@NotNull final UUID uuid) {
        final Player player = this.server.getPlayer(uuid);

        if (player == null) return;

        this.users.putIfAbsent(uuid, new PaperUser(player, uuid, player.getName()));
    }
}