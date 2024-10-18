package com.ryderbelserion.chatmanager.api.users;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.users.objects.PaperUser;
import com.ryderbelserion.chatmanager.api.users.objects.User;
import com.ryderbelserion.chatmanager.loader.ChatManagerPaper;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class PaperUserManager extends UserManager {

    private final ChatManagerPaper plugin;

    private final Server server;

    public PaperUserManager(ChatManager chatManager) {
        this.plugin = chatManager.getPlugin();
        this.server = plugin.getServer();
    }

    @Override
    public @NotNull final Audience getConsoleSender() {
        return this.server.getConsoleSender();
    }

    @Override
    public @NotNull final User getUser(@NotNull final UUID uuid) {
        return new PaperUser(this.server.getPlayer(uuid));
    }
}