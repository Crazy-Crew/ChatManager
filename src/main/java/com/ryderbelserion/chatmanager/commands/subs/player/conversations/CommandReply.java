package com.ryderbelserion.chatmanager.commands.subs.player.conversations;

import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

public class CommandReply extends BaseCommand {

    @Command("reply")
    @Permission(value = "chatmanager.reply", def = PermissionDefault.TRUE, description = "Reply to another player")
    public void reply(final CommandSender sender) {

    }
}