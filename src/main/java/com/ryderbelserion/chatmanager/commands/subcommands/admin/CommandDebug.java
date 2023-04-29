package com.ryderbelserion.chatmanager.commands.subcommands.admin;

import com.ryderbelserion.chatmanager.api.enums.DebugOptions;
import com.ryderbelserion.chatmanager.commands.CommandManager;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.utils.Debug;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

@Command("debug")
@Permission(value = "chatmanager.command.debug", def = PermissionDefault.OP)
public class CommandDebug extends CommandManager {

    @Command
    public void reload(CommandSender sender, @Suggestion("debugs") DebugOptions debug) {
        switch (debug.getName()) {
            case "broadcast" -> {
                Methods.sendMessage(sender, "&eDebugging autobroadcast, Please go to your console to see the debug log.", true);
                Debug.debugAutoBroadcast();
            }

            case "config" -> {
                Methods.sendMessage(sender, "&eDebugging config, Please go to your console to see the debug log.", true);
                Debug.debugConfig();
            }

            case "messages" -> {
                Methods.sendMessage(sender, "&eDebugging config, Please go to your console to see the debug log.", true);
                Debug.debugMessages();
            }

            case "all" -> {
                Methods.sendMessage(sender, "&eDebugging all configuration files, Please go to your console to see the debug low.", true);
                Debug.debugAutoBroadcast();
                Debug.debugConfig();
                Debug.debugMessages();
            }
        }
    }
}