package com.ryderbelserion.chatmanager.utils;

import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import java.util.List;

public class DispatchUtils {

    private final static ChatManager plugin = ChatManager.get();

    private final static Server server = plugin.getServer();

    private final static ConsoleCommandSender sender = server.getConsoleSender();

    public static void dispatchCommand(final Player player, final List<String> commands) {
        if (commands.isEmpty()) return; // do not run

        final String name = player.getName();

        new FoliaScheduler(Scheduler.global_scheduler) {
            @Override
            public void run() {
                for (final String command : commands) {
                    if (command.isEmpty()) {
                        continue;
                    }

                    server.dispatchCommand(sender, command.replace("{player}", name));
                }
            }
        }.runNow();
    }
}