package com.ryderbelserion.chatmanager.managers;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.chatmanager.api.objects.CustomWorld;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.utils.Methods;
import com.ryderbelserion.vital.paper.util.AdvUtil;
import com.ryderbelserion.vital.paper.util.scheduler.FoliaRunnable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BroadcastManager {

    private static final ChatManager plugin = ChatManager.get();

    private static final UserManager userManager = plugin.getUserManager();

    private static final List<CustomWorld> worlds = new ArrayList<>();

    private static final SettingsManager config = ConfigManager.getConfig();

    public static void start() {
        final YamlConfiguration configuration = Files.auto_broadcast_file.getConfiguration();

        if (configuration == null) return;

        final String prefix = configuration.getString("Auto_Broadcast.Global_Messages.Prefix", "&7[&6AutoBroadcast&7] &r");
        final int interval = configuration.getInt("Auto_Broadcast.Global_Messages.Interval", 30);
        final List<String> messages = configuration.getStringList("Auto_Broadcast.Global_Messages.Messages");

        new FoliaRunnable(plugin.getServer().getGlobalRegionScheduler()) {
            int line = 0;

            @Override
            public void run() {
                if (configuration.getBoolean("Auto_Broadcast.Global_Messages.Enable", false)) {
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        if (configuration.getBoolean("Auto_Broadcast.Global_Messages.Header_And_Footer")) {
                            Methods.sendMessage(player, prefix, configuration.getString("Auto_Broadcast.Global_Messages.Header"), false);
                            Methods.sendMessage(player, prefix, messages.get(line).replace("{prefix}", prefix).replace("\\n", "\n"), false);
                            Methods.sendMessage(player, prefix, configuration.getString("Auto_Broadcast.Global_Messages.Footer"), false);
                        } else {
                            Methods.sendMessage(player, prefix, messages.get(line).replace("{prefix}", prefix).replace("\\n", "\n"), false);
                        }

                        Methods.playSound(player, configuration, "Auto_Broadcast.Global_Messages.sound");
                    }
                }

                line++;

                if (line >= messages.size()) line = 0;
            }
        }.runAtFixedRate(plugin, 0L, 20L * interval);

        startPerWorldFeatures(configuration);
        startActionBarMessages(configuration);
        startTitleMessages(configuration);
        startBossBar(configuration);
    }

    public static void startPerWorldFeatures(final YamlConfiguration configuration) {
        String prefix = configuration.getString("Auto_Broadcast.Per_World_Messages.Prefix", "&7[&6AutoBroadcast&7] &r");
        int interval = configuration.getInt("Auto_Broadcast.Per_World_Messages.Interval", 60);

        worlds.clear();

        for (String key : configuration.getConfigurationSection("Auto_Broadcast.Per_World_Messages.Messages").getKeys(false)) {
            worlds.add(new CustomWorld(key, configuration.getStringList("Auto_Broadcast.Per_World_Messages.Messages." + key), 0));
        }

        new FoliaRunnable(plugin.getServer().getGlobalRegionScheduler()) {
            @Override
            public void run() {
                for (final CustomWorld world : getWorlds()) {
                    if (configuration.getBoolean("Auto_Broadcast.Per_World_Messages.Enable", false)) {
                        for (Player player : plugin.getServer().getOnlinePlayers()) {
                            if (player.getWorld().getName().equals(world.getName())) {
                                if (configuration.getBoolean("Auto_Broadcast.Per_World_Messages.Header_And_Footer", false)) {
                                    Methods.sendMessage(player, prefix, configuration.getString("Auto_Broadcast.Per_World_Messages.Header", "&7*&7&m--------------------------------&7*"), false);
                                    Methods.sendMessage(player, prefix, world.getMessages().get(world.getIndex()), false);
                                    Methods.sendMessage(player, prefix, configuration.getString("Auto_Broadcast.Per_World_Messages.Footer", "&7*&7&m--------------------------------&7*"), false);
                                } else {
                                    Methods.sendMessage(player, prefix, world.getMessages().get(world.getIndex()).replaceAll("\\n", "\n"), false);
                                }

                                Methods.playSound(player, configuration, "Auto_Broadcast.Per_World_Messages.sound");
                            }
                        }
                    }

                    int index = world.getIndex();

                    if (index + 1 < world.getMessages().size())
                        world.setIndex(index + 1);
                    else {
                        world.setIndex(0);
                    }
                }
            }
        }.runAtFixedRate(plugin, 0L, 20L * interval);
    }

    public static void startActionBarMessages(final YamlConfiguration configuration) {
        String prefix = configuration.getString("Auto_Broadcast.Actionbar_Messages.Prefix", "&7[&6AutoBroadcast&7] &r");
        int interval = configuration.getInt("Auto_Broadcast.Actionbar_Messages.Interval", 60);
        List<String> messages = configuration.getStringList("Auto_Broadcast.Actionbar_Messages.Messages");

        new FoliaRunnable(plugin.getServer().getGlobalRegionScheduler()) {
            int line = 0;

            @Override
            public void run() {
                if (configuration.getBoolean("Auto_Broadcast.Actionbar_Messages.Enable", false)) {
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        player.sendActionBar(AdvUtil.parse(Methods.placeholders(true, player, messages.get(line).replace("{prefix}", prefix).replace("{Prefix}", prefix))));

                        Methods.playSound(player, configuration, "Auto_Broadcast.Actionbar_Messages.sound");
                    }
                }

                line++;

                if (line >= messages.size() ) line = 0;
            }
        }.runAtFixedRate(plugin, 0L, 20L * interval);
    }

    public static void startTitleMessages(final YamlConfiguration configuration) {
        final int interval = configuration.getInt("Auto_Broadcast.Title_Messages.Interval", 60);
        final List<String> messages = configuration.getStringList("Auto_Broadcast.Title_Messages.Messages");

        new FoliaRunnable(plugin.getServer().getGlobalRegionScheduler()) {
            int line = 0;

            @Override
            public void run() {
                if (configuration.getBoolean("Auto_Broadcast.Title_Messages.Enable", false)) {
                    for (final Player player : plugin.getServer().getOnlinePlayers()) {
                        final String text = Methods.placeholders(true, player, configuration.getString("Auto_Broadcast.Title_Messages.Title"));

                        final Title.Times times = Title.Times.times(Duration.ofMillis(400), Duration.ofMillis(200), Duration.ofMillis(400));
                        final Title title = Title.title(AdvUtil.parse(text), Component.empty(), times);

                        player.showTitle(title);

                        Methods.playSound(player, configuration, "Auto_Broadcast.Title_Messages.sound");
                    }
                }

                line++;

                if (line >= messages.size() ) line = 0;
            }
        }.runAtFixedRate(plugin, 0L, 20L * interval);
    }

    public static void startBossBar(final YamlConfiguration configuration) {
        final int interval = configuration.getInt("Auto_Broadcast.Bossbar_Messages.Interval", 60);
        final int time = configuration.getInt("Auto_Broadcast.Bossbar_Messages.Bar_Time", 10);
        final List<String> messages = configuration.getStringList("Auto_Broadcast.Bossbar_Messages.Messages");

        new FoliaRunnable(plugin.getServer().getGlobalRegionScheduler()) {
            int line = 0;

            @Override
            public void run() {
                if (configuration.getBoolean("Auto_Broadcast.Bossbar_Messages.Enable", false)) {
                    for (final Player player : plugin.getServer().getOnlinePlayers()) {
                        final User user = userManager.getUser(player);

                        user.createBossBar(player, messages.get(line)).showBossBar();

                        if (time == -1) {
                            user.hideBossBar();
                        } else if (time >= 0) {
                            new FoliaRunnable(plugin.getServer().getGlobalRegionScheduler()) {
                                @Override
                                public void run() {
                                    user.hideBossBar();
                                }
                            }.runDelayed(plugin, 20L * time);
                        }

                        Methods.playSound(player, configuration, "Auto_Broadcast.Bossbar_Messages.sound");
                    }
                }

                line++;

                if (line >= messages.size() ) line = 0;
            }
        }.runAtFixedRate(plugin, 0L, 20L * interval);
    }

    private static List<CustomWorld> getWorlds() {
        return worlds;
    }
}