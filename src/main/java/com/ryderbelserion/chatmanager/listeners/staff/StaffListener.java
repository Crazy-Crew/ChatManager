package com.ryderbelserion.chatmanager.listeners.staff;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StaffListener implements Listener {

    private final ChatManager plugin = ChatManager.get();
    private final UserManager userManager = this.plugin.getUserManager();

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(ignoreCancelled = true)
    public void onStaffChat(AsyncChatEvent event) {
        final User user = this.userManager.getUser(event.getPlayer());

        if (user == null || !user.isStaffChat) return;

        event.setCancelled(true);

        MsgUtils.send(user.player, event.signedMessage().message(), true);
    }
}