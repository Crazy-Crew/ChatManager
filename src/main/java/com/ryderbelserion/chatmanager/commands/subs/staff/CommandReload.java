package com.ryderbelserion.chatmanager.commands.subs.staff;

import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.utils.TaskUtils;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

public class CommandReload extends BaseCommand {

    @Command("reload")
    @Permission(value = "chatmanager.reload", def = PermissionDefault.OP)
    public void execute(final CommandSender sender) {
        this.plugin.getFileManager().reloadFiles();

        ConfigManager.refresh();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);

        TaskUtils.startMonitoringTask();

        Messages.plugin_reload.sendMessage(sender);
    }
}