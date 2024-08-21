package com.ryderbelserion.chatmanager.utils;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.vital.paper.util.scheduler.FoliaRunnable;
import org.bukkit.Server;

public class TaskUtils {

    private static final ChatManager plugin = ChatManager.get();
    private static final Server server = plugin.getServer();

    public static void startMonitoringTask() {
        // run task every minute, simply to check if a player can use staff chat.
        new FoliaRunnable(server.getGlobalRegionScheduler()) {
            @Override
            public void run() {
                server.getOnlinePlayers().forEach(player -> {
                    final User user = plugin.getUserManager().getUser(player);

                    if (user == null) return;

                    if (!Permissions.TOGGLE_STAFF_CHAT.hasPermission(player)) {
                        user.isStaffChat = false;
                    }

                    if (!Permissions.SOCIAL_SPY.hasPermission(player)) {
                        user.isSocialSpy = false;
                    }

                    if (!Permissions.COMMAND_SPY.hasPermission(player)) {
                        user.isCommandSpy = false;
                    }
                });
            }
        }.runAtFixedRate(plugin, 0, 1200); // 60 seconds
    }
}