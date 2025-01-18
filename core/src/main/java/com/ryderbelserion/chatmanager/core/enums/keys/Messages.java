package com.ryderbelserion.chatmanager.core.enums.keys;

import com.ryderbelserion.chatmanager.core.ChatProvider;
import com.ryderbelserion.chatmanager.core.api.IChatManager;
import com.ryderbelserion.chatmanager.core.api.UserManager;
import com.ryderbelserion.chatmanager.core.objects.User;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public enum Messages {

    reload_plugin(false, "root", "reload-plugin");

    private final boolean isList;
    private final Object[] path;

    Messages(final boolean isList, final Object... path) {
        this.isList = isList;
        this.path = path;
    }

    private IChatManager provider = ChatProvider.getChatManager();

    private UserManager userManager = this.provider.getUserManager();

    public String getString(final Audience audience) {
        final @Nullable User user = this.userManager.getUser(audience);

        if (user == null) {
            throw new NullPointerException("User was not found in the cache when trying to send a message!");
        }

        return user.getLocale().getConfigurationNode().node(this.path).getString();
    }

    public List<String> getList(final Audience audience) {
        final @Nullable User user = this.userManager.getUser(audience);

        if (user == null) {
            throw new NullPointerException("User was not found in the cache when trying to send a message!");
        }

        return user.getLocale().getConfigurationNode().node(this.path).getList(String.class);
    }

    public final Object[] getPath() {
        return this.path;
    }
}