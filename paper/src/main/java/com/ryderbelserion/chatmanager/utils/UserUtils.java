package com.ryderbelserion.chatmanager.utils;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.managers.UserManager;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.entity.Player;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class UserUtils {

    private static final ChatManager plugin = ChatManager.get();

    private static final UserManager userManager = plugin.getUserManager();

    public static PaperUser getUser(final UUID uuid) {
        final Optional<PaperUser> user = userManager.getUser(uuid);

        if (user.isEmpty()) {
            throw new NoSuchElementException("User not found");
        }

        return user.get();
    }

    public static PaperUser getUser(final Player player) {
        return getUser(player.getUniqueId());
    }
}