package me.h1dd3nxn1nja.chatmanager.paper.listeners;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import me.h1dd3nxn1nja.chatmanager.paper.Methods;
import me.h1dd3nxn1nja.chatmanager.paper.managers.PlaceholderManager;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ListenerPlayerJoin implements Listener {

    private final ChatManager plugin = ChatManager.getPlugin();
    private final Methods methods = plugin.getMethods();
    private final PlaceholderManager placeholderManager = this.plugin.getCrazyManager().getPlaceholderManager();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void firstJoinMessage(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        FileConfiguration config = Files.CONFIG.getFile();

        if (!player.hasPlayedBefore()) {
            if (config.getBoolean("Messages.First_Join.Welcome_Message.Enable")) {
                String message = config.getString("Messages.First_Join.Welcome_Message.First_Join_Message");
                event.setJoinMessage(placeholderManager.setPlaceholders(player, message));

                String path = "Messages.First_Join.Welcome_Message.First_Join_Message.sound";
                String sound = config.getString(path + ".value");
                boolean isEnabled = config.contains(path + ".toggle") && config.getBoolean(path + ".toggle");
                int volume = config.contains(path + ".volume") ? config.getInt(path + ".volume") : 10;
                int pitch = config.contains(path + ".pitch") ? config.getInt(path + ".pitch") : 1;

                if (isEnabled) {
                    for (Player online : plugin.getServer().getOnlinePlayers()) {
                        try {
                            online.playSound(online.getLocation(), Sound.valueOf(sound), volume, pitch);
                        } catch (IllegalArgumentException ignored) {}
                    }
                }
            }

            if (config.getBoolean("Messages.First_Join.Actionbar_Message.Enable")) {
                String message = config.getString("Messages.First_Join.Actionbar_Message.First_Join_Message");

                CraftPlayer craftPlayer = (CraftPlayer) player;

                craftPlayer.sendActionBar(placeholderManager.setPlaceholders(player, message));
            }

            if (config.getBoolean("Messages.First_Join.Title_Message.Enable")) {
                int fadeIn = config.getInt("Messages.First_Join.Title_Message.Fade_In");
                int stay = config.getInt("Messages.First_Join.Title_Message.Stay");
                int fadeOut = config.getInt("Messages.First_Join.Title_Message.Fade_Out");
                String header = placeholderManager.setPlaceholders(player, config.getString("Messages.First_Join.Title_Message.First_Join_Message.Header"));
                String footer = placeholderManager.setPlaceholders(player, config.getString("Messages.First_Join.Title_Message.First_Join_Message.Footer"));

                player.sendTitle(header, footer, fadeIn, stay, fadeOut);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void joinMessage(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        FileConfiguration config = Files.CONFIG.getFile();

        if (player.hasPlayedBefore()) {
            if ((config.getBoolean("Messages.Join_Quit_Messages.Join_Message.Enable")) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable"))) {
                String message = config.getString("Messages.Join_Quit_Messages.Join_Message.Message");
                boolean isAsync = config.getBoolean("Messages.Async", false);

                String path = "Messages.Join_Quit_Messages.Join_Message.sound";
                String sound = config.getString(path + ".value");
                boolean isEnabled = config.contains(path + ".toggle") && config.getBoolean(path + ".toggle");
                int volume = config.contains(path + ".volume") ? config.getInt(path + ".volume") : 10;
                int pitch = config.contains(path + ".pitch") ? config.getInt(path + ".pitch") : 1;

                if (isAsync) {
                    if (event.getJoinMessage() != null) {
                        event.setJoinMessage(null);

                        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> plugin.getServer().broadcastMessage(placeholderManager.setPlaceholders(player, message)));
                    }
                } else {
                    event.setJoinMessage(placeholderManager.setPlaceholders(player, message));
                }

                if (isEnabled) {
                    for (Player online : plugin.getServer().getOnlinePlayers()) {
                        try {
                            online.playSound(online.getLocation(), Sound.valueOf(sound), volume, pitch);
                        } catch (IllegalArgumentException ignored) {}
                    }
                }
            }

            if ((config.getBoolean("Messages.Join_Quit_Messages.Actionbar_Message.Enable")) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable"))) {
                String message = config.getString("Messages.Join_Quit_Messages.Actionbar_Message.Message");

                CraftPlayer craftPlayer = (CraftPlayer) player;

                craftPlayer.sendActionBar(placeholderManager.setPlaceholders(player, message));
            }

            if ((config.getBoolean("Messages.Join_Quit_Messages.Title_Message.Enable")) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable"))) {
                int fadeIn = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_In");
                int stay = config.getInt("Messages.Join_Quit_Messages.Title_Message.Stay");
                int fadeOut = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_Out");
                String header = placeholderManager.setPlaceholders(player, config.getString("Messages.Join_Quit_Messages.Title_Message.Message.Header"));
                String footer = placeholderManager.setPlaceholders(player, config.getString("Messages.Join_Quit_Messages.Title_Message.Message.Footer"));

                player.sendTitle(header, footer, fadeIn, stay, fadeOut);
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

                    if (permission != null && player.hasPermission(permission)) {
                        if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Join_Message")) {
                            boolean isAsync = config.getBoolean("Messages.Async", false);

                            if (isAsync) {
                                if (event.getJoinMessage() != null) {
                                    event.setJoinMessage(null);

                                    plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> plugin.getServer().broadcastMessage(placeholderManager.setPlaceholders(player, joinMessage)));
                                }
                            } else {
                                event.setJoinMessage(placeholderManager.setPlaceholders(player, joinMessage));
                            }
                        }

                        if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Actionbar")) {
                            try {
                                CraftPlayer craftPlayer = (CraftPlayer) player;

                                craftPlayer.sendActionBar(placeholderManager.setPlaceholders(player, actionbarMessage));
                            } catch (NullPointerException ex) {
                                ex.printStackTrace();
                            }
                        }

                        if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title")) {
                            try {
                                player.sendTitle(placeholderManager.setPlaceholders(player, titleHeader), placeholderManager.setPlaceholders(player, titleFooter), fadeIn, stay, fadeOut);
                            } catch (NullPointerException ex) {
                                ex.printStackTrace();
                            }
                        }

                        String path = "Messages.Join_Quit_Messages.Group_Messages." + key;

                        methods.playSound(config, path);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        FileConfiguration config = Files.CONFIG.getFile();

        if ((config.getBoolean("Messages.Join_Quit_Messages.Quit_Message.Enable")) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable"))) {
            String message = config.getString("Messages.Join_Quit_Messages.Quit_Message.Message");
            event.setQuitMessage(placeholderManager.setPlaceholders(player, message));

            String path = "Messages.Join_Quit_Messages.Quit_Message";

            methods.playSound(config, path);
        }

        if (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable")) {
            for (String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false)) {
                String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission");
                String quitMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Quit_Message");

                if (permission != null && player.hasPermission(permission)) {
                    if (config.contains("Messages.Join_Quit_Messages.Group_Messages." + key + ".Quit_Message")) {
                        event.setQuitMessage(placeholderManager.setPlaceholders(player, quitMessage));
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        FileConfiguration config = Files.CONFIG.getFile();

        int lines = config.getInt("Clear_Chat.Broadcasted_Lines");
        int delay = config.getInt("MOTD.Delay");

        if (config.getBoolean("Clear_Chat.Clear_On_Join")) {
            if (player.hasPermission("chatmanager.bypass.clearchat.onjoin")) return;

            for (int i = 0; i < lines; i++) {
                player.sendMessage("");
            }
        }

        if (config.getBoolean("Social_Spy.Enable_On_Join")) {
            if (player.hasPermission("chatmanager.socialspy")) plugin.api().getSocialSpyData().addUser(player.getUniqueId());
        }

        if (config.getBoolean("Command_Spy.Enable_On_Join")) {
            if (player.hasPermission("chatmanager.commandspy")) plugin.api().getCommandSpyData().addUser(player.getUniqueId());
        }

        if (config.getBoolean("Chat_Radius.Enable")) {
            if (config.getString("Chat_Radius.Default_Channel").equalsIgnoreCase("Local")) plugin.api().getLocalChatData().addUser(player.getUniqueId());
            if (config.getString("Chat_Radius.Default_Channel").equalsIgnoreCase("Global")) plugin.api().getGlobalChatData().addUser(player.getUniqueId());
            if (config.getString("Chat_Radius.Default_Channel").equalsIgnoreCase("World")) plugin.api().getWorldChatData().addUser(player.getUniqueId());
        }

        if (config.getBoolean("Chat_Radius.Enable")) {
            if (!config.getBoolean("Chat_Radius.Enable_Spy_On_Join")) return;

            if (player.hasPermission("chatmanager.chatradius.spy")) plugin.api().getSpyChatData().addUser(player.getUniqueId());
        }

        if (config.getBoolean("MOTD.Enable")) {
            new BukkitRunnable() {
                public void run() {
                    for (String motd : config.getStringList("MOTD.Message")) {
                        player.sendMessage(placeholderManager.setPlaceholders(player, motd));
                    }
                }
            }.runTaskLater(plugin, 20L * delay);
        }
    }
}