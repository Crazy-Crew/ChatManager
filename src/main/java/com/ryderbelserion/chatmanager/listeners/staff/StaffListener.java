package com.ryderbelserion.chatmanager.listeners.staff;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.simpleyaml.configuration.file.FileConfiguration;

public class StaffListener implements Listener {

    private final ChatManager plugin = ChatManager.get();
    private final UserManager userManager = this.plugin.getUserManager();

    @EventHandler(ignoreCancelled = true)
    public void onStaffChat(AsyncChatEvent event) {
        final User user = this.userManager.getUser(event.getPlayer());

        if (user == null || !user.isStaffChat) return;

        event.setCancelled(true);

        FileConfiguration config = Files.CONFIG.getConfiguration();

        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            if (Permissions.TOGGLE_STAFF_CHAT.hasPermission(player)) {
                //Methods.sendMessage(player, config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", player.getName()).replace("{message}", event.signedMessage().message()));
            }
        });

        //Methods.tellConsole(config.getString("Staff_Chat.Format", "&e[&bStaffChat&e] &a{player} &7> &b{message}").replace("{player}", user.getName()).replace("{message}", event.signedMessage().message()), false);
    }
}