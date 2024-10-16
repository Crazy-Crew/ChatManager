package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.vital.common.api.interfaces.IPlugin;
import com.ryderbelserion.vital.common.api.managers.PluginManager;
import com.ryderbelserion.vital.paper.util.scheduler.FoliaRunnable;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class ListenerPlayerJoin implements Listener {

    @NotNull
    private final ChatManager plugin = ChatManager.get();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void firstJoinMessage(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) return;

        if (PluginManager.isEnabled("GenericVanish")) {
            final IPlugin plugin = PluginManager.getPlugin("GenericVanish");

            if (plugin != null && plugin.isVanished(player.getUniqueId())) {
                return;
            }
        }

        FileConfiguration config = Files.CONFIG.getConfiguration();

        if (config.getBoolean("Messages.First_Join.Welcome_Message.Enable", false)) {
            String message = config.getString("Messages.First_Join.Welcome_Message.First_Join_Message", "&eWelcome to &b{server_name} %luckperms_prefix% {player}&e!");
            event.setJoinMessage(Methods.placeholders(false, player, Methods.color(message)));

            String path = "Messages.First_Join.Welcome_Message.First_Join_Message.sound";
            boolean isEnabled = config.contains(path + ".toggle", false) && config.getBoolean(path + ".toggle", false);

            if (isEnabled) Methods.playSound(config, path);
        }

        if (config.getBoolean("Messages.First_Join.Actionbar_Message.Enable", false)) {
            String message = config.getString("Messages.First_Join.Actionbar_Message.First_Join_Message", "&eWelcome to &b{server_name} %luckperms_prefix% {player}&e!");

            player.sendActionBar(Methods.placeholders(false, player, Methods.color(message)));
        }

        if (config.getBoolean("Messages.First_Join.Title_Message.Enable", false)) {
            int fadeIn = config.getInt("Messages.First_Join.Title_Message.Fade_In", 40);
            int stay = config.getInt("Messages.First_Join.Title_Message.Stay", 20);
            int fadeOut = config.getInt("Messages.First_Join.Title_Message.Fade_Out", 40);
            String header = config.getString("Messages.First_Join.Title_Message.First_Join_Message.Header", "&eWelcome");
            String footer = config.getString("Messages.First_Join.Title_Message.First_Join_Message.Footer", "&b{player} to {server_name}");

            player.sendTitle(Methods.placeholders(false, player, Methods.color(header)), Methods.placeholders(false, player, Methods.color(footer)), fadeIn, stay, fadeOut);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void joinMessage(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) return;

        if (PluginManager.isEnabled("GenericVanish")) {
            final IPlugin plugin = PluginManager.getPlugin("GenericVanish");

            if (plugin != null && plugin.isVanished(player.getUniqueId())) {
                return;
            }
        }

        FileConfiguration config = Files.CONFIG.getConfiguration();

        if ((config.getBoolean("Messages.Join_Quit_Messages.Join_Message.Enable", false)) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false))) {
            String message = config.getString("Messages.Join_Quit_Messages.Join_Message.Message", "&b{player} &ajoined the server");
            boolean isAsync = config.getBoolean("Messages.Async", false);

            String path = "Messages.Join_Quit_Messages.Join_Message.sound";
            boolean isEnabled = config.contains(path + ".toggle", false) && config.getBoolean(path + ".toggle", false);

            if (isAsync) {
                if (event.getJoinMessage() != null) {
                    event.setJoinMessage(null);

                    new FoliaRunnable(this.plugin.getServer().getAsyncScheduler(), null) {
                        @Override
                        public void run() {
                            plugin.getServer().broadcastMessage(Methods.placeholders(false, player, Methods.color(message)));
                        }
                    }.run(this.plugin);
                }
            } else {
                event.setJoinMessage(Methods.placeholders(false, player, Methods.color(message)));
            }

            if (isEnabled) Methods.playSound(config, path);
        }

        if ((config.getBoolean("Messages.Join_Quit_Messages.Actionbar_Message.Enable", false)) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false))) {
            String message = config.getString("Messages.Join_Quit_Messages.Actionbar_Message.Message", "&eWelcome back to &b{server_name} %luckperms_prefix% {player}&e!");

            player.sendActionBar(Methods.placeholders(false, player, Methods.color(message)));
        }

        if ((config.getBoolean("Messages.Join_Quit_Messages.Title_Message.Enable", false)) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false))) {
            int fadeIn = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_In", 40);
            int stay = config.getInt("Messages.Join_Quit_Messages.Title_Message.Stay", 20);
            int fadeOut = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_Out", 40);
            String header = config.getString("Messages.Join_Quit_Messages.Title_Message.Message.Header", "&eWelcome Back");
            String footer = config.getString("Messages.Join_Quit_Messages.Title_Message.Message.Footer", "&b{player} to {server_name}");

            player.sendTitle(Methods.placeholders(false, player, Methods.color(header)), Methods.placeholders(false, player, Methods.color(footer)), fadeIn, stay, fadeOut);
        }

        if (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false)) {
            for (String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false)) {
                String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission");
                String joinMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Join_Message");
                String actionbarMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Actionbar");
                String titleHeader = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title.Header");
                String titleFooter = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title.Footer");
                int fadeIn = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_In", 40);
                int stay = config.getInt("Messages.Join_Quit_Messages.Title_Message.Stay", 20);
                int fadeOut = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_Out", 40);

                if (permission != null && player.hasPermission(permission)) {
                    if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Join_Message")) {
                        boolean isAsync = config.getBoolean("Messages.Async", false);

                        if (isAsync) {
                            if (event.getJoinMessage() != null) {
                                event.setJoinMessage(null);

                                new FoliaRunnable(this.plugin.getServer().getAsyncScheduler(), null) {
                                    @Override
                                    public void run() {
                                        plugin.getServer().broadcastMessage(Methods.placeholders(false, player, Methods.color(joinMessage)));
                                    }
                                }.run(this.plugin);
                            }
                        } else {
                            event.setJoinMessage(Methods.placeholders(false, player, Methods.color(joinMessage)));
                        }
                    }

                    if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Actionbar")) {
                        try {
                            player.sendActionBar(Methods.placeholders(false, player, Methods.color(actionbarMessage)));
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title")) {
                        try {
                            player.sendTitle(Methods.placeholders(false, player, Methods.color(titleHeader)), Methods.placeholders(false, player, Methods.color(titleFooter)), fadeIn, stay, fadeOut);
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                    }

                    String path = "Messages.Join_Quit_Messages.Group_Messages." + key + ".sound";

                    Methods.playSound(config, path);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (PluginManager.isEnabled("GenericVanish")) {
            final IPlugin plugin = PluginManager.getPlugin("GenericVanish");

            if (plugin != null && plugin.isVanished(player.getUniqueId())) {
                return;
            }
        }

        FileConfiguration config = Files.CONFIG.getConfiguration();

        if ((config.getBoolean("Messages.Join_Quit_Messages.Quit_Message.Enable", false)) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false))) {
            String message = config.getString("Messages.Join_Quit_Messages.Quit_Message.Message", "&b{player} &cleft the server");
            event.setQuitMessage(Methods.placeholders(false, player, Methods.color(message)));

            String path = "Messages.Join_Quit_Messages.Quit_Message.sound";

            Methods.playSound(config, path);
        }

        if (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false)) {
            for (String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false)) {
                String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission");
                String quitMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Quit_Message");

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
        Player player = event.getPlayer();

        FileConfiguration config = Files.CONFIG.getConfiguration();

        int lines = config.getInt("Clear_Chat.Broadcasted_Lines", 300);
        int delay = config.getInt("MOTD.Delay", 2);

        if (config.getBoolean("Clear_Chat.Clear_On_Join", false)) {
            if (player.hasPermission(Permissions.BYPASS_CLEAR_CHAT_ON_JOIN.getNode())) return;

            for (int i = 0; i < lines; i++) {
                player.sendMessage("");
            }
        }

        if (config.getBoolean("Social_Spy.Enable_On_Join", false)) {
            if (player.hasPermission(Permissions.SOCIAL_SPY.getNode())) this.plugin.api().getSocialSpyData().addUser(player.getUniqueId());
        }

        if (config.getBoolean("Command_Spy.Enable_On_Join", false)) {
            if (player.hasPermission(Permissions.COMMAND_SPY.getNode())) this.plugin.api().getCommandSpyData().addUser(player.getUniqueId());
        }

        if (config.getBoolean("Chat_Radius.Enable", false)) {
            if (config.getString("Chat_Radius.Default_Channel", "Global").equalsIgnoreCase("Local")) this.plugin.api().getLocalChatData().addUser(player.getUniqueId());
            if (config.getString("Chat_Radius.Default_Channel", "Global").equalsIgnoreCase("Global")) this.plugin.api().getGlobalChatData().addUser(player.getUniqueId());
            if (config.getString("Chat_Radius.Default_Channel", "Global").equalsIgnoreCase("World")) this.plugin.api().getWorldChatData().addUser(player.getUniqueId());
        }

        if (config.getBoolean("Chat_Radius.Enable", false)) {
            if (!config.getBoolean("Chat_Radius.Enable_Spy_On_Join", false)) return;

            if (player.hasPermission(Permissions.COMMAND_CHATRADIUS_SPY.getNode())) plugin.api().getSpyChatData().addUser(player.getUniqueId());
        }

        if (config.getBoolean("MOTD.Enable", false)) {
            new FoliaRunnable(this.plugin.getServer().getGlobalRegionScheduler()) {
                @Override
                public void run() {
                    for (String motd : config.getStringList("MOTD.Message")) {
                        Methods.sendMessage(player, motd, false);
                    }
                }
            }.runDelayed(this.plugin, 20L * delay);
        }
    }
}