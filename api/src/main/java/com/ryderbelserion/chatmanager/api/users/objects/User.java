package com.ryderbelserion.chatmanager.api.users.objects;

import com.ryderbelserion.chatmanager.ChatManagerProvider;
import com.ryderbelserion.chatmanager.api.ChatManager;
import com.ryderbelserion.util.Methods;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public abstract class User {

    private final ChatManager api = ChatManagerProvider.get();

    public abstract Component getDisplayName();

    public abstract Audience getAudience();

    public abstract Locale getLocale();

    public abstract String getName();

    public abstract UUID getUUID();

    public BossBar bossBar = null;

    /**
     * Show the boss bar for {@link User}.
     *
     * @param title the title of the bossbar
     * @param placeholders the placeholders to replace in the title
     * @return {@link User}
     */
    public final User showBossBar(final String title, final Map<String, String> placeholders) {
        if (title.isEmpty()) return this;

        final Audience audience = getAudience();

        if (audience == null) return this;

        BossBar bar = null;

        if (this.bossBar == null) {
            final Component display = Methods.parse(this.api.parse(audience, title, placeholders));

            this.bossBar = BossBar.bossBar(display, 0, BossBar.Color.PURPLE, BossBar.Overlay.NOTCHED_12);
        } else {
            bar = this.bossBar;
        }

        audience.showBossBar(bar == null ? this.bossBar : bar);

        return this;
    }

    /**
     * Show the boss bar for {@link User}.
     *
     * @param title the title of the bossbar
     * @return {@link User}
     */
    public final User showBossBar(final String title) {
        return showBossBar(title, new HashMap<>());
    }

    /**
     * Hide the boss bar for {@link User}.
     *
     * @return {@link User}
     */
    public final User hideBossBar() {
        final Audience audience = getAudience();

        if (audience == null || this.bossBar == null) return this;

        audience.hideBossBar(this.bossBar);

        this.bossBar = null;

        return this;
    }
}