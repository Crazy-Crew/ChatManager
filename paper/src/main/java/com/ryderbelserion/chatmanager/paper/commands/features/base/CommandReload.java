package com.ryderbelserion.chatmanager.paper.commands.features.base;

import com.ryderbelserion.chatmanager.api.configs.ConfigManager;
import com.ryderbelserion.chatmanager.core.enums.Messages;
import com.ryderbelserion.chatmanager.paper.commands.features.BaseCommand;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Syntax;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

public class CommandReload extends BaseCommand {

    @Command
    @Permission(value = "chatmanager.access", def = PermissionDefault.OP)
    @Syntax(value = "/chatmanager")
    public void root(final CommandSender sender) {
        reload(sender);
    }

    @Command("reload")
    @Permission(value = "chatmanager.reload", def = PermissionDefault.OP)
    @Syntax(value = "/chatmanager reload")
    public void reload(final CommandSender sender) {
        this.fusion.reload();

        ConfigManager.reload();

        Messages.reload_plugin.sendMessage(sender);
    }
}