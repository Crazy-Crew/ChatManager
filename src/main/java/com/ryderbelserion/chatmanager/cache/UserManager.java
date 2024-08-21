package com.ryderbelserion.chatmanager.cache;

import com.ryderbelserion.chatmanager.cache.objects.User;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserManager {

    private final Set<User> users = new HashSet<>();

    public void addUser(final Player player) {
        this.users.add(new User(player.getUniqueId()));
    }

    public void removeUser(final Player player) {
        final User user = getUser(player);

        if (user == null) return;

        this.users.remove(user);
    }

    public final User getUser(final Player player) {
        User user = null;

        final UUID uuid = player.getUniqueId();

        for (User key : this.users) {
            if (uuid.equals(key.uuid)) {
                user = key;

                break;
            }
        }

        return user;
    }

    public void purge() {
        this.users.clear();
    }
}