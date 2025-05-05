package com.ryderbelserion.chatmanager.commands.types.admin;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.managers.ConfigManager;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import com.ryderbelserion.fusion.paper.files.LegacyFileManager;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

public class CommandReload extends AnnotationFeature {

    private final LegacyFileManager fileManager = this.plugin.getLegacyFileManager();

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager reload")
    @CommandDescription("Reloads the plugin!")
    @Permission(value = "chatmanager.reload", mode = Permission.Mode.ANY_OF)
    public void reload(final CommandSender sender) {
        final FileConfiguration config = Files.CONFIG.getConfiguration();

        for (final Player player : this.server.getOnlinePlayers()) {
            final BossBarUtil bossBar = new BossBarUtil();

            bossBar.removeAllBossBars(player);

            final BossBarUtil bossBarStaff = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title", "&eStaff Chat"))));

            final PaperUser user = UserUtils.getUser(player);

            user.purge();

            if (user.hasState(PlayerState.STAFF_CHAT) && Permissions.TOGGLE_STAFF_CHAT.hasPermission(player)) {
                bossBarStaff.removeStaffBossBar(player);
                bossBarStaff.setStaffBossBar(player);
            } else {
                bossBarStaff.removeStaffBossBar(player);
            }
        }

        this.fileManager.reloadFiles().init();

        Files.CONFIG.reload();

        Files.BANNED_COMMANDS.reload();
        Files.BANNED_WORDS.reload();
        Files.AUTO_BROADCAST.reload();

        ConfigManager.reload();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);

        this.plugin.check();

        Messages.PLUGIN_RELOAD.sendMessage(sender);
    }
}