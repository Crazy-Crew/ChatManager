package com.ryderbelserion.chatmanager.utils.support;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.utils.ChatUtils;
import com.ryderbelserion.vital.common.api.interfaces.IPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class EssentialsSupport implements IPlugin {

    private final ChatManager plugin = ChatManager.get();

    private Essentials essentials = null;

    @Override
    public void init() {
        if (!isEnabled()) return;

        this.essentials = JavaPlugin.getPlugin(Essentials.class);

        ChatUtils.add(this);
    }

    @Override
    public void stop() {
        if (!isEnabled()) {
            ChatUtils.remove(this);

            return;
        }

        ChatUtils.remove(this);
    }

    @Override
    public final boolean isEnabled() {
        return this.plugin.getServer().getPluginManager().isPluginEnabled(getName());
    }

    @Override
    public @NotNull final String getName() {
        return "EssentialsX";
    }

    @Override
    public final boolean isVanished(@NotNull final UUID uuid) {
        final User user = this.essentials.getUser(uuid);

        if (user == null) return false;

        return user.isVanished();
    }

    @Override
    public final boolean isIgnored(@NotNull final UUID sender, @NotNull final UUID target) {
        final User user = this.essentials.getUser(sender);

        if (user == null) return false;

        return user.isIgnoredPlayer(this.essentials.getUser(target));
    }

    @Override
    public final boolean isMuted(@NotNull final UUID uuid) {
        final User user = this.essentials.getUser(uuid);

        if (user == null) return false;

        return user.isMuted();
    }
}