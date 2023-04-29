package me.h1dd3nxn1nja.chatmanager;

import com.ryderbelserion.chatmanager.v1.api.CrazyManager;
import com.ryderbelserion.chatmanager.commands.v1.subcommands.admin.CommandStaffChat;
import me.h1dd3nxn1nja.chatmanager.listeners.*;
import me.h1dd3nxn1nja.chatmanager.support.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager extends JavaPlugin {

    private static ChatManager plugin;

    private SettingsManager settingsManager;

    private CrazyManager crazyManager;

    private PluginManager pluginManager;

    public void onEnable() {
        plugin = this;

        plugin.getLogger().warning("""
                Notifying you of deprecated placeholders:
                ==========================================================
                {server_online}, {server_max_players}, {server_name}
                
                {vault_prefix}, {vault_suffix}
                
                {ess_player_balance}, {ess_player_nickname}
                
                {displayname}, {display_name}
                
                These variables are considered deprecated & marked for removal, It is recommended to use:
                https://github.com/PlaceholderAPI/PlaceholderAPI/wiki/Placeholders
                
                You would use the command /papi expansion download <name>
                ==========================================================
                """);

        settingsManager = new SettingsManager();
        settingsManager.setup();

        pluginManager = new PluginManager();

        crazyManager = new CrazyManager();

        crazyManager.load(true);

        //boolean metricsEnabled = settingsManager.getConfig().getBoolean("Metrics_Enabled");

        //if (metricsEnabled) {
        //    MetricsHandler metricsHandler = new MetricsHandler();

        //    metricsHandler.start();
        //}

        //if (!PluginSupport.LUCKPERMS.isPluginEnabled()) plugin.getLogger().severe("A permissions plugin was not found. You will likely have issues without one.");

        //if (PluginSupport.VAULT.isPluginEnabled()) {
            //registerCommands();
            //registerEvents();
            //setupAutoBroadcast();
            //setupChatRadius();
        //}
    }

    public void onDisable() {
        crazyManager.stop(true);
    }

    public void registerEvents() {
        //getServer().getPluginManager().registerEvents(new CommandStaffChat(), this);

        getServer().getPluginManager().registerEvents(new ListenerAntiAdvertising(), this);
        getServer().getPluginManager().registerEvents(new ListenerAntiBot(), this);
        getServer().getPluginManager().registerEvents(new ListenerAntiSpam(), this);
        getServer().getPluginManager().registerEvents(new ListenerAntiUnicode(), this);
        getServer().getPluginManager().registerEvents(new ListenerBannedCommand(), this);
        getServer().getPluginManager().registerEvents(new ListenerCaps(), this);
        getServer().getPluginManager().registerEvents(new ListenerChatFormat(), this);
        getServer().getPluginManager().registerEvents(new ListenerColor(), this);
        getServer().getPluginManager().registerEvents(new ListenerRadius(), this);
        getServer().getPluginManager().registerEvents(new ListenerGrammar(), this);
        getServer().getPluginManager().registerEvents(new ListenerLogs(), this);
        getServer().getPluginManager().registerEvents(new ListenerMentions(), this);
        getServer().getPluginManager().registerEvents(new ListenerMuteChat(), this);
        getServer().getPluginManager().registerEvents(new ListenerPerWorldChat(), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new ListenerSpy(), this);
        getServer().getPluginManager().registerEvents(new ListenerSwear(), this);
        getServer().getPluginManager().registerEvents(new ListenerToggleChat(), this);
    }

    /*public void setupChatRadius() {
        if (settingsManager.getConfig().getBoolean("Chat_Radius.Enable")) {
            for (Player all : getServer().getOnlinePlayers()) {
                if (settingsManager.getConfig().getString("Chat_Radius.Default_Channel").equalsIgnoreCase("Local")) {
                    plugin.api().getLocalChatData().addUser(all.getUniqueId());
                } else if (settingsManager.getConfig().getString("Chat_Radius.Default_Channel").equalsIgnoreCase("Global")) {
                    plugin.api.getGlobalChatData().addUser(all.getUniqueId());
                } else if (settingsManager.getConfig().getString("Chat_Radius.Default_Channel").equalsIgnoreCase("World")) {
                    plugin.api.getWorldChatData().addUser(all.getUniqueId());
                }
            }
        }
    }

    public void setupAutoBroadcast() {
        try {
            if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Actionbar_Messages.Enable"))
                AutoBroadcastManager.actionbarMessages();
            if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Global_Messages.Enable"))
                AutoBroadcastManager.globalMessages();
            if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Per_World_Messages.Enable"))
                AutoBroadcastManager.perWorldMessages();
            if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Title_Messages.Enable"))
                AutoBroadcastManager.titleMessages();
            if (settingsManager.getAutoBroadcast().getBoolean("Auto_Broadcast.Bossbar_Messages.Enable"))
                AutoBroadcastManager.bossBarMessages();
        } catch (Exception e) {
            getLogger().severe("There has been an error setting up auto broadcast. Stacktrace...");

            e.printStackTrace();
        }
    }*/

    public static ChatManager getPlugin() {
        return plugin;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public CrazyManager getCrazyManager() {
        return crazyManager;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }
}