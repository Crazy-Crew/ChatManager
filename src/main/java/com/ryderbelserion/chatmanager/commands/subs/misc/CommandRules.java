package com.ryderbelserion.chatmanager.commands.subs.misc;

import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

public class CommandRules extends BaseCommand {

    @Command("rules")
    @Permission(value = "chatmanager.rules", def = PermissionDefault.OP)
    public void execute(final CommandSender sender, @Suggestion("pages") final int page) {
        ConfigManager.getRules().get(page).forEach(line -> MsgUtils.sendMessage(sender, line));
    }
}