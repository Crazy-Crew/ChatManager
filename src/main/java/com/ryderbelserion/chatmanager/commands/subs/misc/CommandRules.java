package com.ryderbelserion.chatmanager.commands.subs.misc;

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

public class CommandRules extends BaseCommand {

    @Command("rules")
    @Permission(value = "chatmanager.rules", def = PermissionDefault.OP, description = "Access to /chatmanager rules")
    public void rules(final CommandSender sender, @Suggestion("pages") final int page) {
        final Map<Integer, List<String>> rules = ConfigManager.getRules();

        rules.get(page).forEach(line -> MsgUtils.sendMessage(sender, line, "{max}", String.valueOf(rules.size())));
    }
}