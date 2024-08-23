package com.ryderbelserion.chatmanager.commands.subs.player;

import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import java.util.List;
import java.util.Map;

public class CommandHelp extends BaseCommand {

    @Command
    @Permission(value = "chatmanager.access", def = PermissionDefault.TRUE, description = "Access to /chatmanager")
    public void root(final CommandSender sender) {
        final Map<Integer, List<String>> help = ConfigManager.getHelp();

        help.get(1).forEach(line -> MsgUtils.sendMessage(sender, line, "{max}", String.valueOf(help.size())));
    }

    @Command("Help")
    @Permission(value = "chatmanager.help", def = PermissionDefault.TRUE, description = "Access to /chatmanager help")
    public void help(final CommandSender sender, @Suggestion("help_pages") final int page) {
        final Map<Integer, List<String>> help = ConfigManager.getHelp();

        help.get(page).forEach(line -> MsgUtils.sendMessage(sender, line, "{max}", String.valueOf(help.size())));
    }
}