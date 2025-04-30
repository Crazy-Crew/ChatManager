package com.ryderbelserion.chatmanager.commands.types.admin;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.StaffChatData;
import com.ryderbelserion.chatmanager.api.cooldowns.ChatCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CmdCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CooldownTask;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.managers.ConfigManager;
import com.ryderbelserion.fusion.paper.files.LegacyFileManager;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

public class CommandReload extends AnnotationFeature {

    private final Server server = this.plugin.getServer();

    private final ApiLoader api = this.plugin.api();

    private final CooldownTask task = this.api.getCooldownTask();

    private final ChatCooldowns chat = this.api.getChatCooldowns();

    private final CmdCooldowns cmd = this.api.getCmdCooldowns();

    private final StaffChatData data = this.api.getStaffChatData();

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
            this.chat.removeUser(player.getUniqueId());
            this.task.removeUser(player.getUniqueId());
            this.cmd.removeUser(player.getUniqueId());

            final BossBarUtil bossBar = new BossBarUtil();

            bossBar.removeAllBossBars(player);

            final BossBarUtil bossBarStaff = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title", "&eStaff Chat"))));

            if (this.data.containsUser(player.getUniqueId()) && player.hasPermission("chatmanager.staffchat")) {
                bossBarStaff.removeStaffBossBar(player);
                bossBarStaff.setStaffBossBar(player);
            }
        }

        this.fileManager.reloadFiles().init();

        Files.CONFIG.reload();

        Messages.addMissingMessages();

        Files.MESSAGES.reload();
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