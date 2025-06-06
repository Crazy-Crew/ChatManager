package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.fusion.core.api.interfaces.IPlugin;
import com.ryderbelserion.fusion.core.managers.PluginExtension;
import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.support.Global;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.UUID;

public class ListenerPlayerJoin extends Global implements Listener {

    private final PluginExtension extension = this.plugin.getPluginExtension();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void firstJoinMessage(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (player.hasPlayedBefore()) return;

        if (this.extension.isEnabled("GenericVanish")) {
            final IPlugin plugin = this.extension.getPlugin("GenericVanish");

            if (plugin != null && plugin.isVanished(player.getUniqueId())) {
                return;
            }
        }

        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if (config.getBoolean("Messages.First_Join.Welcome_Message.Enable", false)) {
            final String message = config.getString("Messages.First_Join.Welcome_Message.First_Join_Message", "&eWelcome to &b{server_name} %luckperms_prefix% {player}&e!");
            event.setJoinMessage(Methods.placeholders(false, player, Methods.color(message)));

            final String path = "Messages.First_Join.Welcome_Message.First_Join_Message.sound";
            final boolean isEnabled = config.contains(path + ".toggle", false) && config.getBoolean(path + ".toggle", false);

            if (isEnabled) Methods.playSound(config, path);
        }

        if (config.getBoolean("Messages.First_Join.Actionbar_Message.Enable", false)) {
            final String message = config.getString("Messages.First_Join.Actionbar_Message.First_Join_Message", "&eWelcome to &b{server_name} %luckperms_prefix% {player}&e!");

            player.sendActionBar(Methods.placeholders(false, player, Methods.color(message)));
        }

        if (config.getBoolean("Messages.First_Join.Title_Message.Enable", false)) {
            final int fadeIn = config.getInt("Messages.First_Join.Title_Message.Fade_In", 40);
            final int stay = config.getInt("Messages.First_Join.Title_Message.Stay", 20);
            final int fadeOut = config.getInt("Messages.First_Join.Title_Message.Fade_Out", 40);
            final String header = config.getString("Messages.First_Join.Title_Message.First_Join_Message.Header", "&eWelcome");
            final String footer = config.getString("Messages.First_Join.Title_Message.First_Join_Message.Footer", "&b{player} to {server_name}");

            player.sendTitle(Methods.placeholders(false, player, Methods.color(header)), Methods.placeholders(false, player, Methods.color(footer)), fadeIn, stay, fadeOut);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void joinMessage(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) return;

        if (this.extension.isEnabled("GenericVanish")) {
            final IPlugin plugin = this.extension.getPlugin("GenericVanish");

            if (plugin != null && plugin.isVanished(player.getUniqueId())) {
                return;
            }
        }

        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if ((config.getBoolean("Messages.Join_Quit_Messages.Join_Message.Enable", false)) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false))) {
            final String message = config.getString("Messages.Join_Quit_Messages.Join_Message.Message", "&b{player} &ajoined the server");
            final boolean isAsync = config.getBoolean("Messages.Async", false);

            final String path = "Messages.Join_Quit_Messages.Join_Message.sound";
            final boolean isEnabled = config.contains(path + ".toggle", false) && config.getBoolean(path + ".toggle", false);

            if (isAsync) {
                if (event.getJoinMessage() != null) {
                    event.setJoinMessage(null);

                    new FoliaScheduler(Scheduler.async_scheduler) {
                        @Override
                        public void run() {
                            server.broadcastMessage(Methods.placeholders(false, player, Methods.color(message)));
                        }
                    }.runNow();
                }
            } else {
                event.setJoinMessage(Methods.placeholders(false, player, Methods.color(message)));
            }

            if (isEnabled) Methods.playSound(config, path);
        }

        if ((config.getBoolean("Messages.Join_Quit_Messages.Actionbar_Message.Enable", false)) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false))) {
            final String message = config.getString("Messages.Join_Quit_Messages.Actionbar_Message.Message", "&eWelcome back to &b{server_name} %luckperms_prefix% {player}&e!");

            player.sendActionBar(Methods.placeholders(false, player, Methods.color(message)));
        }

        if ((config.getBoolean("Messages.Join_Quit_Messages.Title_Message.Enable", false)) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false))) {
            final int fadeIn = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_In", 40);
            final int stay = config.getInt("Messages.Join_Quit_Messages.Title_Message.Stay", 20);
            final int fadeOut = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_Out", 40);
            final String header = config.getString("Messages.Join_Quit_Messages.Title_Message.Message.Header", "&eWelcome Back");
            final String footer = config.getString("Messages.Join_Quit_Messages.Title_Message.Message.Footer", "&b{player} to {server_name}");

            player.sendTitle(Methods.placeholders(false, player, Methods.color(header)), Methods.placeholders(false, player, Methods.color(footer)), fadeIn, stay, fadeOut);
        }

        if (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false)) {
            for (final String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false)) {
                final String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission");
                final String joinMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Join_Message");
                final String actionbarMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Actionbar");
                final String titleHeader = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title.Header");
                final String titleFooter = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title.Footer");
                final int fadeIn = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_In", 40);
                final int stay = config.getInt("Messages.Join_Quit_Messages.Title_Message.Stay", 20);
                final int fadeOut = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_Out", 40);

                if (permission != null && player.hasPermission(permission)) {
                    if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Join_Message")) {
                        final boolean isAsync = config.getBoolean("Messages.Async", false);

                        if (isAsync) {
                            if (event.getJoinMessage() != null) {
                                event.setJoinMessage(null);

                                new FoliaScheduler(Scheduler.async_scheduler) {
                                    @Override
                                    public void run() {
                                        server.broadcastMessage(Methods.placeholders(false, player, Methods.color(joinMessage)));
                                    }
                                }.runNow();
                            }
                        } else {
                            event.setJoinMessage(Methods.placeholders(false, player, Methods.color(joinMessage)));
                        }
                    }

                    if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Actionbar")) {
                        try {
                            player.sendActionBar(Methods.placeholders(false, player, Methods.color(actionbarMessage)));
                        } catch (final NullPointerException ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title")) {
                        try {
                            player.sendTitle(Methods.placeholders(false, player, Methods.color(titleHeader)), Methods.placeholders(false, player, Methods.color(titleFooter)), fadeIn, stay, fadeOut);
                        } catch (final NullPointerException ex) {
                            ex.printStackTrace();
                        }
                    }

                    final String path = "Messages.Join_Quit_Messages.Group_Messages." + key + ".sound";

                    Methods.playSound(config, path);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if (this.extension.isEnabled("GenericVanish")) {
            final IPlugin plugin = this.extension.getPlugin("GenericVanish");

            if (plugin != null && plugin.isVanished(player.getUniqueId())) {
                return;
            }
        }

        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if ((config.getBoolean("Messages.Join_Quit_Messages.Quit_Message.Enable", false)) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false))) {
            final String message = config.getString("Messages.Join_Quit_Messages.Quit_Message.Message", "&b{player} &cleft the server");
            event.setQuitMessage(Methods.placeholders(false, player, Methods.color(message)));

            final String path = "Messages.Join_Quit_Messages.Quit_Message.sound";

            Methods.playSound(config, path);
        }

        if (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false)) {
            for (final String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false)) {
                final String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission");
                final String quitMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Quit_Message");

                if (permission != null && player.hasPermission(permission)) {
                    if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Quit_Message")) {
                        event.setQuitMessage(Methods.placeholders(false, player, Methods.color(quitMessage)));
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if (config.getBoolean("Clear_Chat.Clear_On_Join", false)) {
            final int lines = config.getInt("Clear_Chat.Broadcasted_Lines", 300);

            if (!player.hasPermission(Permissions.BYPASS_CLEAR_CHAT_ON_JOIN.getNode())) {
                for (int i = 0; i < lines; i++) {
                    player.sendMessage("");
                }
            }
        }

        if (config.getBoolean("Social_Spy.Enable_On_Join", false)) {
            if (player.hasPermission(Permissions.SOCIAL_SPY.getNode())) this.socialSpyData.addUser(uuid);
        }

        if (config.getBoolean("Command_Spy.Enable_On_Join", false)) {
            if (player.hasPermission(Permissions.COMMAND_SPY.getNode())) this.commandSpyData.addUser(uuid);
        }

        if (config.getBoolean("Chat_Radius.Enable", false)) {
            if (config.getString("Chat_Radius.Default_Channel", "Global").equalsIgnoreCase("Local")) this.localChatData.addUser(uuid);
            if (config.getString("Chat_Radius.Default_Channel", "Global").equalsIgnoreCase("Global")) this.globalChatData.addUser(uuid);
            if (config.getString("Chat_Radius.Default_Channel", "Global").equalsIgnoreCase("World")) this.worldChatData.addUser(uuid);
        }

        if (config.getBoolean("Chat_Radius.Enable", false)) {
            if (config.getBoolean("Chat_Radius.Enable_Spy_On_Join", false)) {
                if (player.hasPermission(Permissions.COMMAND_CHATRADIUS_SPY.getNode())) this.spyChatData.addUser(uuid);
            }
        }

        if (config.getBoolean("MOTD.Enable", false)) {
            final int delay = config.getInt("MOTD.Delay", 2);

            new FoliaScheduler(Scheduler.global_scheduler) {
                @Override
                public void run() {
                    for (final String motd : config.getStringList("MOTD.Message")) {
                        Methods.sendMessage(player, motd, false);
                    }
                }
            }.runDelayed(20L * delay);
        }
    }
}