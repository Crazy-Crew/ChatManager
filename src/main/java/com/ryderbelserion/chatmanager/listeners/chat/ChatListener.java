package com.ryderbelserion.chatmanager.listeners.chat;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.chat.ChatType;
import com.ryderbelserion.chatmanager.api.enums.chat.ToggleType;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.types.ConfigKeys;
import com.ryderbelserion.chatmanager.configs.types.SpamKeys;
import io.papermc.paper.event.player.AsyncChatEvent;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ChatListener implements Listener {

    private final ChatManager plugin = ChatManager.get();
    private final UserManager userManager = this.plugin.getUserManager();

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

        final User user = this.userManager.getUser(event.getPlayer());

        if (user == null) return;

        user.isBlockingCommands = isCommandsBlocked();
        user.isBlockingChat = isChatBlocked();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerMove(PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();

        if (from.getX() == to.getX() && from.getZ() == to.getZ()) return;

        final Player player = event.getPlayer();

        if (!isCommandsBlocked() || !isChatBlocked() || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

        final User user = this.userManager.getUser(event.getPlayer());

        if (user == null) return;

        user.isBlockingCommands = false;
        user.isBlockingChat = false;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();

        if (!isCommandsBlocked() || !isChatBlocked() || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

        final User user = this.userManager.getUser(player);

        if (user == null || !user.isBlockingCommands) return;

        event.setCancelled(true);

        Messages.anti_bot_deny_command_message.sendMessage(player);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onToggleChat(AsyncChatEvent event) {
        final Player player = event.getPlayer();

        final User user = this.userManager.getUser(player);

        if (user == null || !user.activeChatTypes.contains(ToggleType.toggle_chat.getName())) return;

        event.viewers().remove(player);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent event) {
        final Player player = event.getPlayer();

        if (!isCommandsBlocked() || !isChatBlocked() || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

        final User user = this.userManager.getUser(player);

        if (user == null || !user.isBlockingChat) return;

        event.setCancelled(true);

        Messages.anti_bot_deny_chat_message.sendMessage(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMuteChat(AsyncChatEvent event) {
        final Player player = event.getPlayer();

        final User user = this.userManager.getUser(player);

        if (player.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode()) || !user.isMuted) return;

        //todo() send message that they are muted

        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMuteCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();

        final User user = this.userManager.getUser(player);

        if (!this.config.getProperty(ConfigKeys.mute_chat_disable_commands) ||
                player.hasPermission(Permissions.BYPASS_MUTE_CHAT.getNode()) ||
                !user.isMuted) return;

        final String message = event.getMessage().toLowerCase();

        this.config.getProperty(ConfigKeys.mute_chat_disabled_commands).forEach(command -> {
            if (message.contains(command)) {
                //todo() tell them commands are blocked, because muted.

                event.setCancelled(true);
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerRadiusChat(AsyncChatEvent event) {
        final Player player = event.getPlayer();

        final User user = this.userManager.getUser(player);

        if (!this.config.getProperty(ConfigKeys.chat_radius_enable) || user.isStaffChat) return;

        final String localCharacter = this.config.getProperty(ConfigKeys.local_chat_override_symbol);
        final String globalCharacter = this.config.getProperty(ConfigKeys.global_chat_override_symbol);
        final String worldCharacter = this.config.getProperty(ConfigKeys.world_chat_override_symbol);

        final String message = event.signedMessage().message();

        if (player.hasPermission(Permissions.CHAT_RADIUS_GLOBAL_OVERRIDE.getNode())) {
            if (!globalCharacter.isEmpty()) {
                if (message.charAt(0) == globalCharacter.charAt(0)) {
                    user.chatType = ChatType.world_chat;

                    //todo() update message without breaking chat signatures

                    return;
                }
            }
        }

        if (player.hasPermission(Permissions.CHAT_RADIUS_LOCAL_OVERRIDE.getNode())) {
            if (!localCharacter.isEmpty()) {
                if (message.charAt(0) == localCharacter.charAt(0)) {
                    user.chatType = ChatType.local_chat;

                    //todo() update message without breaking chat signatures

                    return;
                }
            }
        }

        if (player.hasPermission(Permissions.CHAT_RADIUS_WORLD_OVERRIDE.getNode())) {
            if (!worldCharacter.isEmpty()) {
                if (message.charAt(0) == worldCharacter.charAt(0)) {
                    user.chatType = ChatType.world_chat;

                    //todo() update message without breaking chat signatures

                    return;
                }
            }
        }

        if (user.chatType == ChatType.local_chat) {
            event.viewers().clear();

//            for (Player receiver : this.plugin.getServer().getOnlinePlayers()) { //todo() uncomment this
//                if (Methods.inRange(uuid, receiver.getUniqueId(), radius)) {
//                    recipients.add(player);
//                    recipients.add(receiver);
//                }
//
//                if (plugin.api().getSpyChatData().containsUser(receiver.getUniqueId())) recipients.add(receiver);
//            }
        }

        if (user.chatType == ChatType.world_chat) {
            event.viewers().clear();

//            for (Player receiver : this.plugin.getServer().getOnlinePlayers()) { //todo() uncomment this
//                if (Methods.inWorld(uuid, receiver.getUniqueId())) {
//                    recipients.add(player);
//                    recipients.add(receiver);
//                }
//
//                if (this.plugin.api().getSpyChatData().containsUser(receiver.getUniqueId())) recipients.add(receiver);
//            }
        }
    }

    public final boolean isCommandsBlocked() {
        return this.config.getProperty(SpamKeys.block_commands_until_moved);
    }

    public final boolean isChatBlocked() {
        return this.config.getProperty(SpamKeys.block_chat_until_moved);
    }
}