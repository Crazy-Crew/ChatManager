package com.ryderbelserion.chatmanager.api.cache.objects;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.enums.chat.ChatType;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.impl.v2.ConfigKeys;
import com.ryderbelserion.vital.paper.api.enums.Support;
import com.ryderbelserion.vital.paper.util.AdvUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private final SettingsManager config = ConfigManager.getConfig();

    public final CommandSender sender;
    public final Player player;

    public User(final CommandSender sender) {
        if (sender instanceof Player target) {
            this.sender = target;
            this.player = target;
        } else {
            this.sender = sender;
            this.player = null;
        }
    }

    public boolean isStaffChat = false;
    public boolean isMuted = false;

    public String replyPlayer = "";

    public final List<String> activeChatTypes = new ArrayList<>();
    public final List<String> activeSpyTypes = new ArrayList<>();

    public ChatType chatType = ChatType.global_chat;

    public String locale = "en-US";

    // These settings, do not get stored to the data.yml, but are kept here for ease of access. transient anyway because meh
    public transient boolean isBlockingCommands = false;

    public transient boolean isBlockingChat = false;

    public transient String previousCommand = "";
    public transient int commandDelay = 0;

    public transient String previousMessage = "";
    public transient int chatDelay = 0;

    public transient BossBar bossBar = null;

    public final User showBossBar() {
        if (this.player == null) return this;

        final UUID uuid = this.player.getUniqueId();

        BossBar bar = null;

        if (this.bossBar == null) {
            this.bossBar = BossBar.bossBar(AdvUtil.parse(this.config.getProperty(ConfigKeys.staff_bossbar_title), uuid),
                    0,
                    BossBar.Color.PURPLE,
                    BossBar.Overlay.NOTCHED_12);
        } else {
            bar = this.bossBar;
        }

        this.player.showBossBar(bar == null ? this.bossBar : bar);

        return this;
    }

    public final User createBossBar(final String name) {
        if (this.player == null) return this;

        this.bossBar = BossBar.bossBar(
                AdvUtil.parse(Support.placeholder_api.isEnabled() ? PlaceholderAPI.setPlaceholders(player, name) : name),
                0,
                BossBar.Color.PURPLE,
                BossBar.Overlay.NOTCHED_12
        );

        return this;
    }

    public final User hideBossBar() {
        if (this.bossBar == null || this.player == null) return null;

        this.player.hideBossBar(this.bossBar);

        this.bossBar = null;

        return this;
    }

    // other checks
    public final boolean hasPermission(final String permission) {
        if (this.player == null) return false;

        return this.player.hasPermission(permission);
    }

    public final String getName() {
        return this.player != null ? this.player.getName() : this.sender.getName();
    }
}