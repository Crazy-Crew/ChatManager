package com.ryderbelserion.chatmanager.listeners.chat;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.CommandsConfig;
import com.ryderbelserion.chatmanager.configs.impl.types.ConfigKeys;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.HashMap;

public class FilterListener implements Listener {

    private final ChatManager plugin = ChatManager.get();

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (!this.config.getProperty(ConfigKeys.banned_commands_enable)) return;

        final Player player = event.getPlayer();
        final String message = event.getMessage();

        if (!player.hasPermission(Permissions.BYPASS_BANNED_COMMANDS.getNode())) {
            final boolean isSensitive = this.config.getProperty(ConfigKeys.banned_commands_increase_sensitivity);

            for (final String command : CommandsConfig.banned_commands) {
                if (isSensitive) {
                    if (message.equalsIgnoreCase("/" + command)) {
                        event.setCancelled(true);

                        notification(player, message.replace("/", ""));
                        execute(player);
                    }
                } else {
                    if (message.toLowerCase().contains("/" + command)) {
                        event.setCancelled(true);

                        notification(player, message.replace("/", ""));
                        execute(player);
                    }
                }
            }
        }

        if (!player.hasPermission(Permissions.BYPASS_COLON_COMMANDS.getNode())) {
            if (message.split(" ")[0].contains(":")) {
                event.setCancelled(true);

                notification(player, message.replace("/", ""));
                execute(player);
            }
        }
    }

    private void notification(final Player player, final String message) {
        if (!this.config.getProperty(ConfigKeys.banned_commands_notify_staff)) return;

        for (Player staff : this.plugin.getServer().getOnlinePlayers().stream().filter(human -> !human.hasPermission(Permissions.NOTIFY_BANNED_COMMANDS.getNode())).toList()) {
            Messages.filter_command_notify_staff.sendMessage(staff, new HashMap<>() {{
                put("{player}", player.getName());
                put("{command}", message);
            }});
        }

        Messages.filter_command_notify_staff.sendMessage(this.plugin.getServer().getConsoleSender(), new HashMap<>() {{
            put("{player}", player.getName());
            put("{command}", message);
        }});
    }

    private void execute(final Player player) {
        if (!this.config.getProperty(ConfigKeys.banned_commands_execute_command)) return;

        final String command = this.config.getProperty(ConfigKeys.banned_commands_executed_command).replace("{player}", player.getName());

        if (command.isEmpty()) return;

        final ConsoleCommandSender sender = this.plugin.getServer().getConsoleSender();

        this.plugin.getServer().dispatchCommand(sender, command);

        //todo() dispatch multiple commands
    }
}