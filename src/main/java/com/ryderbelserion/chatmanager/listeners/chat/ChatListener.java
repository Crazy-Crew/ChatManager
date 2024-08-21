package com.ryderbelserion.chatmanager.listeners.chat;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.cache.UserManager;
import com.ryderbelserion.chatmanager.cache.objects.User;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.types.SpamKeys;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.h1dd3nxn1nja.chatmanager.enums.Permissions;
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

    private final SettingsManager config = ConfigManager.getSpam();

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

        // send message
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent event) {
        final Player player = event.getPlayer();

        if (!isCommandsBlocked() || !isChatBlocked() || player.hasPermission(Permissions.BYPASS_ANTI_BOT.getNode())) return;

        final User user = this.userManager.getUser(player);

        if (user == null || !user.isBlockingChat) return;

        event.setCancelled(true);

        // send message
    }

    public final boolean isCommandsBlocked() {
        return this.config.getProperty(SpamKeys.block_commands_until_moved);
    }

    public final boolean isChatBlocked() {
        return this.config.getProperty(SpamKeys.block_chat_until_moved);
    }
}