package com.ryderbelserion.chatmanager.listeners.chat;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.impl.v2.ConfigKeys;
import com.ryderbelserion.chatmanager.configs.beans.CommandProperty;
import com.ryderbelserion.chatmanager.configs.beans.GenericProperty;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.CommandsConfig;
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
        final GenericProperty property = this.config.getProperty(ConfigKeys.blocked_commands);

        if (!property.isEnabled()) return;

        final Player player = event.getPlayer();
        final String message = event.getMessage();

        if (!player.hasPermission(Permissions.BYPASS_BANNED_COMMANDS.getNode())) {
            final boolean isSensitive = property.getSensitivity().equalsIgnoreCase("high");

            for (final String command : CommandsConfig.banned_commands) {
                if (isSensitive) {
                    if (message.equalsIgnoreCase("/" + command)) {
                        event.setCancelled(true);

                        notification(property, player, message.replace("/", ""));
                        execute(property, player);
                    }
                } else {
                    if (message.toLowerCase().contains("/" + command)) {
                        event.setCancelled(true);

                        notification(property, player, message.replace("/", ""));
                        execute(property, player);
                    }
                }
            }
        }

        if (!player.hasPermission(Permissions.BYPASS_COLON_COMMANDS.getNode())) {
            if (message.split(" ")[0].contains(":")) {
                event.setCancelled(true);

                notification(property, player, message.replace("/", ""));
                execute(property, player);
            }
        }
    }

    private void notification(final GenericProperty property, final Player player, final String message) {
        if (!property.isNotify()) return;

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

    private void execute(final GenericProperty property, final Player player) {
        final CommandProperty commandProperty = property.getCommandProperty();

        if (!commandProperty.isExecute()) return;

        final String command = commandProperty.getValue().replace("{player}", player.getName());

        if (command.isEmpty()) return;

        final ConsoleCommandSender sender = this.plugin.getServer().getConsoleSender();

        this.plugin.getServer().dispatchCommand(sender, command);

        //todo() dispatch multiple commands
    }
}