package com.ryderbelserion.chatmanager.listeners.staff;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.Messages;
import com.ryderbelserion.chatmanager.api.enums.Permissions;
import com.ryderbelserion.chatmanager.api.events.MessageSendEvent;
import com.ryderbelserion.chatmanager.cache.UserManager;
import com.ryderbelserion.chatmanager.cache.objects.User;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.types.spy.ModKeys;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;

public class SpyListener implements Listener {

    private final ChatManager plugin = ChatManager.get();
    private final UserManager userManager = this.plugin.getUserManager();

    private final SettingsManager config = ConfigManager.getSpam();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommandSpy(PlayerCommandPreprocessEvent event) {
        if (!this.config.getProperty(ModKeys.toggle_command_spy)) return;

        final Player player = event.getPlayer();

        if (player.hasPermission(Permissions.BYPASS_COMMAND_SPY.getNode())) return;

        final String message = event.getMessage();

        for (final String command : this.config.getProperty(ModKeys.command_spy_commands)) {
            if (message.toLowerCase().startsWith(command)) return;
        }

        for (final Player staff : this.plugin.getServer().getOnlinePlayers()) {
            if (!staff.hasPermission(Permissions.COMMAND_SPY.getNode())) return;

            final User user = this.userManager.getUser(player);

            if (user == null || !user.isCommandSpy) return;

            Messages.COMMAND_SPY_FORMAT.sendMessage(staff, new HashMap<>() {{
                put("{player}", player.getName());
                put("{command}", message);
            }});
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSocialSpy(MessageSendEvent event) {
        if (!this.config.getProperty(ModKeys.toggle_social_spy)) return;

        final Player player = event.getPlayer();

        if (player.hasPermission(Permissions.BYPASS_COMMAND_SPY.getNode())) return;

        final String message = event.getMessage();

        for (final String command : this.config.getProperty(ModKeys.command_spy_commands)) {
            if (message.toLowerCase().startsWith(command)) return;
        }

        final Player target = event.getTarget();

        for (final Player staff : this.plugin.getServer().getOnlinePlayers()) {
            if (!staff.hasPermission(Permissions.COMMAND_SPY.getNode())) return;

            final User user = this.userManager.getUser(staff);

            if (user == null || !user.isCommandSpy) return;

            Messages.SOCIAL_SPY_FORMAT.sendMessage(staff, new HashMap<>() {{
                put("{receiver}", target.getName());
                put("{player}", player.getName());
                put("{message}", message);
            }});
        }
    }
}