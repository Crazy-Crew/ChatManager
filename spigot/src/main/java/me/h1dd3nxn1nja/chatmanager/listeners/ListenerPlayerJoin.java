package me.h1dd3nxn1nja.chatmanager.listeners;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import me.h1dd3nxn1nja.chatmanager.managers.PlaceholderManager;
import me.h1dd3nxn1nja.chatmanager.utils.JSONMessage;
import me.h1dd3nxn1nja.chatmanager.utils.ServerProtocol;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerPlayerJoin implements Listener {

    private final ChatManager plugin = ChatManager.getPlugin();

    private final SettingsManager settingsManager = plugin.getSettingsManager();

    private final PlaceholderManager placeholderManager = plugin.getCrazyManager().getPlaceholderManager();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void firstJoinMessage(PlayerJoinEvent event) {
        FileConfiguration config = settingsManager.getConfig();

        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            if (config.getBoolean("Messages.First_Join.Welcome_Message.Enable")) {
                String message = config.getString("Messages.First_Join.Welcome_Message.First_Join_Message");
                event.setJoinMessage(placeholderManager.setPlaceholders(player, message));

                for (Player online : plugin.getServer().getOnlinePlayers()) {
                    try {
                        online.playSound(online.getLocation(), Sound.valueOf(config.getString("Messages.First_Join.Welcome_Message.Sound")), 10, 1);
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }

            if (config.getBoolean("Messages.First_Join.Actionbar_Message.Enable")) {
                String message = config.getString("Messages.First_Join.Actionbar_Message.First_Join_Message");

                if ((ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1))) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(placeholderManager.setPlaceholders(player, message)));
                } else {
                    JSONMessage.create().actionbar(placeholderManager.setPlaceholders(player, message), player);
                }
            }

            if ((ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1))) {
                if (config.getBoolean("Messages.First_Join.Title_Message.Enable")) {
                    int fadeIn = config.getInt("Messages.First_Join.Title_Message.Fade_In");
                    int stay = config.getInt("Messages.First_Join.Title_Message.Stay");
                    int fadeOut = config.getInt("Messages.First_Join.Title_Message.Fade_Out");
                    String header = placeholderManager.setPlaceholders(player, config.getString("Messages.First_Join.Title_Message.First_Join_Message.Header"));
                    String footer = placeholderManager.setPlaceholders(player, config.getString("Messages.First_Join.Title_Message.First_Join_Message.Footer"));

                    if ((ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1))) {
                        player.sendTitle(header, footer, fadeIn, stay, fadeOut);
                    } else {
                        JSONMessage.create(header).title(fadeIn, stay, fadeOut, player);
                        JSONMessage.create(footer).subtitle(player);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void JoinMessage(PlayerJoinEvent event) {
        FileConfiguration config = settingsManager.getConfig();

        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) {
            if ((config.getBoolean("Messages.Join_Quit_Messages.Join_Message.Enable"))
                    && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable"))) {
                String message = config.getString("Messages.Join_Quit_Messages.Join_Message.Message");
                boolean isAsync = config.getBoolean("Messages.Async", false);

                if (isAsync) {
                    if (event.getJoinMessage() != null) {
                        event.setJoinMessage(null);

                        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                            Bukkit.broadcastMessage(placeholderManager.setPlaceholders(player, message));
                        });
                    }
                } else {
                    event.setJoinMessage(placeholderManager.setPlaceholders(player, message));
                }

                for (Player online : plugin.getServer().getOnlinePlayers()) {
                    try {
                        online.playSound(online.getLocation(), Sound.valueOf(config.getString("Messages.Join_Quit_Messages.Join_Message.Sound")), 10, 1);
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
            if ((config.getBoolean("Messages.Join_Quit_Messages.Actionbar_Message.Enable"))
                    && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable"))) {
                String message = config.getString("Messages.Join_Quit_Messages.Actionbar_Message.Message");

                if ((ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1))) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(placeholderManager.setPlaceholders(player, message)));
                } else {
                    JSONMessage.create().actionbar(placeholderManager.setPlaceholders(player, message), player);
                }
            }

            if ((ServerProtocol.isAtLeast(ServerProtocol.v1_9_R1))) {
                if ((config.getBoolean("Messages.Join_Quit_Messages.Title_Message.Enable"))
                        && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable"))) {
                    int fadeIn = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_In");
                    int stay = config.getInt("Messages.Join_Quit_Messages.Title_Message.Stay");
                    int fadeOut = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_Out");
                    String header = placeholderManager.setPlaceholders(player, config.getString("Messages.Join_Quit_Messages.Title_Message.Message.Header"));
                    String footer = placeholderManager.setPlaceholders(player, config.getString("Messages.Join_Quit_Messages.Title_Message.Message.Footer"));

                    if ((ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1))) {
                        player.sendTitle(header, footer, fadeIn, stay, fadeOut);
                    } else {
                        JSONMessage.create(header).title(fadeIn, stay, fadeOut, player);
                        JSONMessage.create(footer).subtitle(player);
                    }
                }
            }

            if (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable")) {
                for (String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false)) {
                    String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission");
                    String joinMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Join_Message");
                    String actionbarMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Actionbar");
                    String titleHeader = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title.Header");
                    String titleFooter = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title.Footer");
                    int fadeIn = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_In");
                    int stay = config.getInt("Messages.Join_Quit_Messages.Title_Message.Stay");
                    int fadeOut = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_Out");
                    String sound = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Sound");

                    if (permission != null && player.hasPermission(permission)) {
                        if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Join_Message")) {
                            boolean isAsync = config.getBoolean("Messages.Async", false);

                            if (isAsync) {
                                if (event.getJoinMessage() != null) {
                                    event.setJoinMessage(null);

                                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                                        Bukkit.broadcastMessage(placeholderManager.setPlaceholders(player, joinMessage));
                                    });
                                }
                            } else {
                                event.setJoinMessage(placeholderManager.setPlaceholders(player, joinMessage));
                            }
                        }

                        if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Actionbar")) {
                            try {
                                if ((ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1))) {
                                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(placeholderManager.setPlaceholders(player, actionbarMessage)));
                                } else {
                                    JSONMessage.create().actionbar(placeholderManager.setPlaceholders(player, actionbarMessage), player);
                                }
                            } catch (NullPointerException ex) {
                                ex.printStackTrace();
                            }
                        }

                        if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title")) {
                            try {
                                if ((ServerProtocol.isAtLeast(ServerProtocol.v1_16_R1))) {
                                    player.sendTitle(placeholderManager.setPlaceholders(player, titleHeader), placeholderManager.setPlaceholders(player, titleFooter), fadeIn, stay, fadeOut);
                                } else {
                                    JSONMessage.create(placeholderManager.setPlaceholders(player, titleHeader)).title(fadeIn, stay, fadeOut, player);
                                    JSONMessage.create(placeholderManager.setPlaceholders(player, titleFooter)).subtitle(player);
                                }
                            } catch (NullPointerException ex) {
                                ex.printStackTrace();
                            }
                        }

                        if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Sound")) {
                            for (Player online : plugin.getServer().getOnlinePlayers()) {
                                try {
                                    online.playSound(online.getLocation(), Sound.valueOf(sound), 10, 1);
                                } catch (IllegalArgumentException ignored) {
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        FileConfiguration config = settingsManager.getConfig();

        Player player = event.getPlayer();

        if ((config.getBoolean("Messages.Join_Quit_Messages.Quit_Message.Enable"))
                && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable"))) {
            String message = config.getString("Messages.Join_Quit_Messages.Quit_Message.Message");
            System.out.println("Setting quit message 1");
            event.setQuitMessage(placeholderManager.setPlaceholders(player, message));

            for (Player online : plugin.getServer().getOnlinePlayers()) {
                try {
                    online.playSound(online.getLocation(), Sound.valueOf(config.getString("Messages.Join_Quit_Messages.Quit_Message.Sound")), 10, 1);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }

        if (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable")) {
            for (String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false)) {
                String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission");
                String quitMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Quit_Message");

                if (permission != null && player.hasPermission(permission)) {
                    if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Quit_Message")) {
                        System.out.println("Setting quit message 2");
                        event.setQuitMessage(placeholderManager.setPlaceholders(player, quitMessage));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FileConfiguration config = settingsManager.getConfig();

        Player player = event.getPlayer();
        int lines = config.getInt("Clear_Chat.Broadcasted_Lines");
        int delay = config.getInt("MOTD.Delay");

        if (config.getBoolean("Clear_Chat.Clear_On_Join")) {
            if (!player.hasPermission("chatmanager.bypass.clearchat.onjoin")) {
                for (int i = 0; i < lines; i++) {
                    player.sendMessage("");
                }
            }
        }

        if (config.getBoolean("Social_Spy.Enable_On_Join")) {
            if (player.hasPermission("chatmanager.socialspy")) Methods.cm_socialSpy.add(player.getUniqueId());
        }

        if (config.getBoolean("Command_Spy.Enable_On_Join")) {
            if (player.hasPermission("chatmanager.commandspy")) Methods.cm_commandSpy.add(player.getUniqueId());
        }

        if (config.getBoolean("Chat_Radius.Enable")) {
            if (config.getString("Chat_Radius.Default_Channel").equals("Local"))
                Methods.cm_localChat.add(player.getUniqueId());
            if (config.getString("Chat_Radius.Default_Channel").equals("Global"))
                Methods.cm_globalChat.add(player.getUniqueId());
            if (config.getString("Chat_Radius.Default_Channel").equals("World"))
                Methods.cm_worldChat.add(player.getUniqueId());
        }

        if (config.getBoolean("Chat_Radius.Enable")) {
            if (config.getBoolean("Chat_Radius.Enable_Spy_On_Join")) {
                if (player.hasPermission("chatmanager.chatradius.spy")) Methods.cm_spyChat.add(player.getUniqueId());
            }
        }

        new BukkitRunnable() {
            public void run() {
                if (config.getBoolean("MOTD.Enable")) {
                    for (String motd : config.getStringList("MOTD.Message")) {
                        player.sendMessage(placeholderManager.setPlaceholders(player, motd));
                    }
                }
            }
        }.runTaskLater(plugin, 20L * delay);
    }
}