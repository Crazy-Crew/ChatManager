package com.ryderbelserion.chatmanager.commands.subcommands.player;

import com.ryderbelserion.chatmanager.commands.CommandManager;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

@Command("motd")
@Permission(value = "chatmanager.command.motd", def = PermissionDefault.TRUE)
public class CommandMOTD extends CommandManager {

    @Command
    public void execute(Player player) {
        boolean toggle = config.getBoolean("MOTD.Enable");

        if (!toggle) return;

        config.getStringList("MOTD.Message").forEach(line -> Methods.sendMessage(player, placeholderManager.setPlaceholders(player, line), true));
    }
}