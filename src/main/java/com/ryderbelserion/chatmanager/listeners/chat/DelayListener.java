package com.ryderbelserion.chatmanager.listeners.chat;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.impl.v2.chat.SpamKeys;
import com.ryderbelserion.vital.paper.util.scheduler.FoliaRunnable;
import io.papermc.paper.event.player.AsyncChatEvent;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class DelayListener implements Listener {

    private final ChatManager plugin = ChatManager.get();
    private final UserManager userManager = this.plugin.getUserManager();

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCommandProcess(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();

        final User user = this.userManager.getUser(player);

        if (user == null || user.isStaffChat) return;

        if (this.config.getProperty(SpamKeys.block_chat_spam_repeating_commands)) {
            final String command = event.getMessage();

            if (!player.hasPermission(Permissions.BYPASS_DUPE_COMMAND.getNode())) {
                for (String value : this.config.getProperty(SpamKeys.block_chat_spam_whitelist_commands)) {
                    if (command.contains(value)) {
                        user.previousCommand = "";

                        return;
                    }
                }

                final String previousCommand = user.previousCommand;

                if (!previousCommand.isEmpty() && user.commandDelay < 1) {
                    if (command.equalsIgnoreCase(user.previousCommand)) {
                        Messages.anti_spam_command_repetitive_message.sendMessage(player);

                        event.setCancelled(true);
                    }
                }
            }

            int commandDelay = this.config.getProperty(SpamKeys.block_chat_spam_command_delay);

            if (commandDelay == -1 || user.commandDelay == 0) return;

            if (user.commandDelay >= 1) {
                int delayLeft = user.commandDelay;

                Messages.anti_spam_command_delay_message.sendMessage(player, "{Time}", String.valueOf(delayLeft));

                return;
            }

            for (String value : this.config.getProperty(SpamKeys.block_chat_spam_whitelist_commands)) {
                if (command.contains(value)) {
                    return;
                }
            }

            user.commandDelay = commandDelay;

            // start the task!
            new FoliaRunnable(this.plugin.getServer().getGlobalRegionScheduler()) {
                @Override
                public void run() {
                    int delayLeft = user.commandDelay;

                    user.commandDelay = delayLeft-1;

                    if (delayLeft == 0) {
                        cancel();
                    }
                }
            }.runAtFixedRate(this.plugin, 20, 20);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent event) {
        final Player player = event.getPlayer();

        final User user = this.userManager.getUser(player);

        if (user == null || user.isStaffChat) return;

        // we return, because the delay shouldn't be triggered.
        if (this.config.getProperty(SpamKeys.block_chat_spam_repeating_messages)) {
            if (player.hasPermission(Permissions.BYPASS_DUPE_CHAT.getNode())) return;

            final String message = event.signedMessage().message();
            final String previousMessage = user.previousMessage;

            if (!previousMessage.isEmpty() && previousMessage.equals(message)) {
                Messages.anti_spam_chat_repetitive_message.sendMessage(player);

                event.setCancelled(true);
            }

            // update message again
            user.previousMessage = message;

            return;
        }

        int chatDelay = this.config.getProperty(SpamKeys.block_chat_spam_delay);

        if (chatDelay == -1 || player.hasPermission(Permissions.BYPASS_CHAT_DELAY.getNode())) return;

        if (user.chatDelay == 0) return;

        if (user.chatDelay >= 1) {
            int delayLeft = user.chatDelay;

            Messages.anti_spam_chat_delay_message.sendMessage(player, "{Time}", String.valueOf(delayLeft));

            return;
        }

        // set the chat delay
        user.chatDelay = chatDelay;

        // start the task!
        new FoliaRunnable(this.plugin.getServer().getGlobalRegionScheduler()) {
            @Override
            public void run() {
                int delay = user.chatDelay;

                user.chatDelay = delay-1;

                if (delay == 0) {
                    cancel();
                }
            }
        }.runAtFixedRate(this.plugin, 20, 20);
    }
}