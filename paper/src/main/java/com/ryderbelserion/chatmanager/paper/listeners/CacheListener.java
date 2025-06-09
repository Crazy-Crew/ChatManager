package com.ryderbelserion.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.ChatManagerPlugin;
import com.ryderbelserion.fusion.paper.FusionPaper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CacheListener implements Listener {

    private final ChatManagerPlugin plugin = ChatManagerPlugin.get();

    private final FusionPaper fusion = this.plugin.getApi();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        /*final Player player = event.getPlayer();

        this.userManager.addUser(player);

        final PaperUser user = this.userManager.getUser(player.getUniqueId());

        event.joinMessage(this.fusion.color(player, user.locale().getProperty(RootKeys.join_message), new HashMap<>() {{
            put("{player}", player.getName());
        }}));*/

        /*if (this.config.getProperty(ConfigKeys.motd_enabled)) {
            final int delay = config.getProperty(ConfigKeys.motd_delay);

            new FoliaScheduler(this.plugin, Scheduler.global_scheduler) {
                @Override
                public void run() {
                    /*Messages.motd.sendMessage(player, new HashMap<>() {{
                        put("{player}", player.getName());
                    }});
                }
            }.runDelayed(delay);
        }*/
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        /*final Player player = event.getPlayer();

        final PaperUser user = this.userManager.getUser(player.getUniqueId());

        event.quitMessage(this.fusion.color(player, user.locale().getProperty(RootKeys.quit_message), new HashMap<>() {{
            put("{player}", player.getName());
        }}));

        this.userManager.removeUser(player);*/
    }
}