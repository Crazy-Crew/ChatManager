package com.ryderbelserion.chatmanager.api.users;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.users.objects.PaperUser;
import com.ryderbelserion.chatmanager.api.users.objects.User;
import com.ryderbelserion.chatmanager.loader.ChatManagerPaper;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaperUserManager extends UserManager {

    private final ChatManagerPaper plugin;

    private final Server server;

    public PaperUserManager(ChatManager chatManager) {
        this.plugin = chatManager.getPlugin();
        this.server = this.plugin.getServer();
    }

    private final Map<UUID, PaperUser> users = new HashMap<>();

    @Override
    public @NotNull final Audience getConsoleSender() {
        return this.server.getConsoleSender();
    }

    @Override
    public @NotNull final User getUser(@NotNull final UUID uuid) {
        return this.users.get(uuid);
    }

    @Override
    public void addUser(@NotNull final UUID uuid) {
        this.users.putIfAbsent(uuid, new PaperUser(this.server.getPlayer(uuid)));
    }
}