package com.ryderbelserion.chatmanager.commands.subcommands.player;

import com.ryderbelserion.chatmanager.api.enums.ToggleOptions;
import com.ryderbelserion.chatmanager.commands.CommandManager;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import dev.triumphteam.cmd.core.annotation.Suggestion;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import java.util.UUID;

public class CommandToggle extends CommandManager {

    @SubCommand("toggle")
    @Permission(value = "chatmanager.command.toggle", def = PermissionDefault.OP)
    public void toggle(Player player, @Suggestion("toggles") ToggleOptions toggleOptions) {
        UUID uuid = player.getUniqueId();
        
        switch (toggleOptions.getName()) {
            case "chat" -> {
                if (!player.hasPermission("chatmanager.toggle.chat")) {
                    Methods.sendMessage(player, Methods.noPermission(), true);
                    return;
                }

                if (plugin.api().getToggleChatData().containsUser(uuid)) {
                    plugin.api().getToggleChatData().removeUser(uuid);
                    Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Chat.Enabled")), true);
                    return;
                }

                plugin.api().getToggleChatData().addUser(uuid);
                Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Chat.Disabled")), true);
            }

            case "mentions" -> {
                if (!player.hasPermission("chatmanager.toggle.mentions")) {
                    Methods.sendMessage(player, Methods.noPermission(), true);
                    return;
                }

                if (plugin.api().getToggleMentionsData().containsUser(uuid)) {
                    plugin.api().getToggleMentionsData().removeUser(uuid);
                    Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Mentions.Enabled")), true);
                    return;
                }

                plugin.api().getToggleMentionsData().addUser(uuid);
                Methods.sendMessage(player, placeholderManager.setPlaceholders(player, messages.getString("Toggle_Mentions.Disabled")), true);
            }
            
            case "pm" -> {
                if (!player.hasPermission("chatmanager.toggle.pm")) {
                    Methods.sendMessage(player, Methods.noPermission(), true);
                    return;
                }
                
                boolean isValid = plugin.api().getToggleMessageData().containsUser(uuid);

                if (isValid) {
                    plugin.api().getToggleMessageData().removeUser(uuid);
                    Methods.sendMessage(player, messages.getString("TogglePM.Enabled"), true);
                    return;
                }

                plugin.api().getToggleMessageData().addUser(uuid);
                Methods.sendMessage(player, messages.getString("TogglePM.Disabled"), true);
            }
        }
    }
}