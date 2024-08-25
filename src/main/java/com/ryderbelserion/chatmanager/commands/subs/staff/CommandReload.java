package com.ryderbelserion.chatmanager.commands.subs.staff;

import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.utils.LogUtils;
import com.ryderbelserion.chatmanager.utils.TaskUtils;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

public class CommandReload extends BaseCommand {

    @Command("reload")
    @Permission(value = "chatmanager.reload", def = PermissionDefault.OP, description = "Access to /chatmanager reload")
    public void reload(final CommandSender sender) {
        this.plugin.getFileManager().reloadFiles();

        ConfigManager.refresh();

        LogUtils.zip();
        LogUtils.create();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);

        TaskUtils.startMonitoringTask();

        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            final User user = this.userManager.getUser(player);

            //todo() remove cooldowns
            //this.plugin.api().getChatCooldowns().removeUser(player.getUniqueId());
            //this.plugin.api().getCooldownTask().removeUser(player.getUniqueId());
            //this.plugin.api().getCmdCooldowns().removeUser(player.getUniqueId());

            if (user.isStaffChat) {
                user.hideBossBar();

                if (Permissions.COMMAND_STAFF.hasPermission(player)) {
                    user.showBossBar();
                } else {
                    user.isStaffChat = !user.isStaffChat;
                }
            }
        }

        Messages.plugin_reload.sendMessage(sender);
    }
}