package com.ryderbelserion.chatmanager.commands.subs.staff;

import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.utils.LogUtils;
import com.ryderbelserion.chatmanager.utils.TaskUtils;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

public class CommandReload extends BaseCommand {

    @Command
    @Permission(value = "chatmanager.access", def = PermissionDefault.OP, description = "Access to /chatmanager help")
    public void help(final CommandSender sender) {

    }

    @Command("reload")
    @Permission(value = "chatmanager.reload", def = PermissionDefault.OP, description = "Access to /chatmanager reload")
    public void reload(final CommandSender sender) {
        this.plugin.getFileManager().reloadFiles();

        ConfigManager.refresh();

        LogUtils.create();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);

        TaskUtils.startMonitoringTask();

        Messages.plugin_reload.sendMessage(sender);
    }
}