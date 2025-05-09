package me.h1dd3nxn1nja.chatmanager.listeners;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.commands.RadiusType;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.UUID;

public class ListenerPlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void firstJoinMessage(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (player.hasPlayedBefore()) return;

        /*if (this.extension.isEnabled("GenericVanish")) { //todo() bad code
            final IPlugin plugin = this.extension.getPlugin("GenericVanish");

            if (plugin != null && plugin.isVanished(player.getUniqueId())) {
                return;
            }
        }*/

        final FileConfiguration config = Files.CONFIG.getConfiguration();

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
        final Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) return;

        /*if (this.extension.isEnabled("GenericVanish")) { //todo() bad code
            final IPlugin plugin = this.extension.getPlugin("GenericVanish");

            if (plugin != null && plugin.isVanished(player.getUniqueId())) {
                return;
            }
        }*/

        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if ((config.getBoolean("Messages.Join_Quit_Messages.Join_Message.Enable", false)) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false))) {
            final String message = config.getString("Messages.Join_Quit_Messages.Join_Message.Message", "&b{player} &ajoined the server");

            final String path = "Messages.Join_Quit_Messages.Join_Message.sound";
            final boolean isEnabled = config.contains(path + ".toggle", false) && config.getBoolean(path + ".toggle", false);

            event.setJoinMessage(Methods.placeholders(false, player, Methods.color(message)));

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
            final ConfigurationSection section = config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages");

            if (section == null) return;

            for (final String key : section.getKeys(false)) {
                final String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission", "");
                final String joinMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Join_Message", "");
                final String actionbarMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Actionbar", "");
                final String titleHeader = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title.Header", "");
                final String titleFooter = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Title.Footer", "");
                final int fadeIn = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_In", 40);
                final int stay = config.getInt("Messages.Join_Quit_Messages.Title_Message.Stay", 20);
                final int fadeOut = config.getInt("Messages.Join_Quit_Messages.Title_Message.Fade_Out", 40);

                if (!permission.isEmpty() && player.hasPermission(permission)) {
                    if (!joinMessage.isEmpty()) {
                        event.setJoinMessage(Methods.placeholders(false, player, Methods.color(joinMessage)));
                    }

                    if (!actionbarMessage.isEmpty()) {
                        player.sendActionBar(Methods.placeholders(false, player, Methods.color(actionbarMessage)));
                    }

                    if (!titleHeader.isEmpty() && !titleFooter.isEmpty()) {
                        player.sendTitle(Methods.placeholders(false, player, Methods.color(titleHeader)), Methods.placeholders(false, player, Methods.color(titleFooter)), fadeIn, stay, fadeOut);
                    }

                    Methods.playSound(config, "Messages.Join_Quit_Messages.Group_Messages." + key + ".sound");
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        /*if (this.extension.isEnabled("GenericVanish")) { //todo() bad code
            final IPlugin plugin = this.extension.getPlugin("GenericVanish");

            if (plugin != null && plugin.isVanished(player.getUniqueId())) {
                return;
            }
        }*/

        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if ((config.getBoolean("Messages.Join_Quit_Messages.Quit_Message.Enable", false)) && !(config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false))) {
            event.setQuitMessage(Methods.placeholders(false, player, Methods.color(config.getString("Messages.Join_Quit_Messages.Quit_Message.Message", "&b{player} &cleft the server"))));

            Methods.playSound(config, "Messages.Join_Quit_Messages.Quit_Message.sound");
        }

        if (config.getBoolean("Messages.Join_Quit_Messages.Group_Messages.Enable", false)) {
            for (final String key : config.getConfigurationSection("Messages.Join_Quit_Messages.Group_Messages").getKeys(false)) {
                final String permission = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Permission", "");
                final String quitMessage = config.getString("Messages.Join_Quit_Messages.Group_Messages." + key + ".Quit_Message", "");

                if (!permission.isEmpty() && !quitMessage.isEmpty() && player.hasPermission(permission)) {
                    event.setQuitMessage(Methods.placeholders(false, player, Methods.color(quitMessage)));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        final FileConfiguration config = Files.CONFIG.getConfiguration();

        final int lines = config.getInt("Clear_Chat.Broadcasted_Lines", 300);
        final int delay = config.getInt("MOTD.Delay", 2);

        if (config.getBoolean("Clear_Chat.Clear_On_Join", false)) {
            if (Permissions.BYPASS_CLEAR_CHAT_ON_JOIN.hasPermission(player)) return;

            for (int i = 0; i < lines; i++) {
                player.sendMessage("");
            }
        }

        if (config.getBoolean("MOTD.Enable", false)) {
            new FoliaScheduler(Scheduler.global_scheduler) {
                @Override
                public void run() {
                    for (final String motd : config.getStringList("MOTD.Message")) {
                        Methods.sendMessage(player, motd, false);
                    }
                }
            }.runDelayed(20L * delay);
        }

        final PaperUser user = UserUtils.getUser(uuid);

        if (config.getBoolean("Social_Spy.Enable_On_Join", false) && Permissions.SOCIAL_SPY.hasPermission(player)) {
            user.addState(PlayerState.SOCIAL_SPY);
        }

        if (config.getBoolean("Command_Spy.Enable_On_Join", false) && Permissions.COMMAND_SPY.hasPermission(player)) {
            user.addState(PlayerState.COMMAND_SPY);
        }

        if (config.getBoolean("Chat_Radius.Enable", false)) {
            user.setRadius(RadiusType.getType(config.getString("Chat_Radius.Default_Channel", "global")));
        }
    }
}