package com.ryderbelserion.chatmanager.commands.subs.staff.chat;

import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Optional;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;

public class CommandClearChat extends BaseCommand {

    @Command("clearchat")
    @Permission(value = "chatmanager.staff.clear", def = PermissionDefault.OP, description = "Clears the chat with an optional arg")
    public void clear(final CommandSender sender, @Suggestion ("numbers") @Optional Integer lines) {
        final int amount = lines != null && lines > 0 ? lines : this.config.getProperty(ConfigKeys.clear_chat_broadcasted_lines);

        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            if (player.hasPermission(Permissions.BYPASS_CLEAR_CHAT.getNode())) return;

            for (int count = 0; count < amount; count++) {
                player.sendMessage("");
            }
        });

        Messages.clear_chat_broadcast_message.broadcast(new HashMap<>() {{
            put("{player}", sender.getName());
        }});

        if (sender instanceof ConsoleCommandSender) {
            Messages.clear_chat_staff_message.sendMessage(sender, "{player}", sender.getName());
        }
    }
}