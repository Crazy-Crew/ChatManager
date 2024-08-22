package com.ryderbelserion.chatmanager.api.cache.objects;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import com.ryderbelserion.vital.paper.util.AdvUtil;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.entity.Player;
import java.util.UUID;

public class User {

    private final SettingsManager config = ConfigManager.getConfig();

    public final Player player;

    public User(final Player player) {
        this.player = player;
    }

    public boolean isChatToggled = false;
    public boolean isCommandSpy = false;
    public boolean isStaffChat = false;
    public boolean isSocialSpy = false;

    public String locale = "en-US";

    // These settings, do not get stored to the data.yml, but are kept here for ease of access. transient anyway because meh
    public transient boolean isBlockingCommands = false;

    public transient boolean isBlockingChat = false;

    public transient String previousCommand = "";
    public transient int commandDelay = 0;

    public transient String previousMessage = "";
    public transient int chatDelay = 0;

    public transient BossBar bossBar = null;

    public void showBossBar() {
        final UUID uuid = this.player.getUniqueId();

        final BossBar bossBar = BossBar
                .bossBar(AdvUtil.parse(this.config.getProperty(ConfigKeys.staff_bossbar_title), uuid),
                        0,
                        BossBar.Color.PURPLE,
                        BossBar.Overlay.NOTCHED_12);

        this.player.showBossBar(this.bossBar = bossBar);
    }

    public void hideBossBar() {
        if (this.bossBar == null) return;

        this.player.hideBossBar(this.bossBar);

        this.bossBar = null;
    }

    // other checks
    public final boolean hasPermission(final String permission) {
        return this.player.hasPermission(permission);
    }

    public final String getName() {
        return this.player.getName();
    }
}