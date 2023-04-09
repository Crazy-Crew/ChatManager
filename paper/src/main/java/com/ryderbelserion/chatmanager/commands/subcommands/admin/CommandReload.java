package com.ryderbelserion.chatmanager.commands.subcommands.admin;

import com.ryderbelserion.chatmanager.commands.CommandManager;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import java.util.UUID;

public class CommandReload extends CommandManager {

    @SubCommand("reload")
    @Permission(value = "chatmanager.command.reload", def = PermissionDefault.OP)
    public void reload(CommandSender sender) {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            UUID uuid = player.getUniqueId();

            plugin.api().getChatCooldowns().removeUser(uuid);
            plugin.api().getCooldownTask().removeUser(uuid);
            plugin.api().getCmdCooldowns().removeUser(uuid);

            BossBarUtil bossBar = new BossBarUtil();
            bossBar.removeAllBossBars(player);
        });

        settingsManager.reloadConfig();
        settingsManager.reloadMessages();
        settingsManager.reloadAutoBroadcast();
        settingsManager.reloadBannedCommands();
        settingsManager.reloadBannedWords();
        settingsManager.setup();

        plugin.getServer().getScheduler().cancelTasks(plugin);

        try {
            if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Actionbar_Messages.Enable")) AutoBroadcastManager.actionbarMessages();
            if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Global_Messages.Enable")) AutoBroadcastManager.globalMessages();
            if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Per_World_Messages.Enable")) AutoBroadcastManager.perWorldMessages();
            if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Title_Messages.Enable")) AutoBroadcastManager.titleMessages();
            if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Bossbar_Messages.Enable")) AutoBroadcastManager.bossBarMessages();
        } catch (Exception e) {
            Methods.tellConsole("There was an error setting up auto broadcast. Stack-trace:", true);
            e.printStackTrace();
        }

        Methods.sendMessage(sender, messages.getString("Message.Reload"), true);
    }
}