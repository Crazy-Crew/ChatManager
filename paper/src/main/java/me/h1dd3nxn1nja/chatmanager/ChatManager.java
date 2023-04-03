package me.h1dd3nxn1nja.chatmanager;

import me.h1dd3nxn1nja.chatmanager.api.CrazyManager;
import me.h1dd3nxn1nja.chatmanager.commands.*;
import me.h1dd3nxn1nja.chatmanager.commands.tabcompleter.*;
import me.h1dd3nxn1nja.chatmanager.listeners.*;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import me.h1dd3nxn1nja.chatmanager.utils.MetricsHandler;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager extends JavaPlugin {

    private static ChatManager plugin;

    private boolean isEnabled;

    private SettingsManager settingsManager;

    private CrazyManager crazyManager;

    private PluginManager pluginManager;

    public void onEnable() {
        plugin = this;

        settingsManager = new SettingsManager();

        settingsManager.setup();

        String version = settingsManager.getConfig().getString("Config_Version");

        if (version == null) {
            settingsManager.getConfig().set("Config_Version", 1);
            settingsManager.saveConfig();
        }

        String hexColorFormat = settingsManager.getConfig().getString("Hex_Color_Format");

        if (hexColorFormat == null) {
            settingsManager.getConfig().set("Hex_Color_Format", "#");
            settingsManager.saveConfig();
        }

        String metricsValue = settingsManager.getConfig().getString("Metrics_Enabled");

        if (metricsValue == null) {
            settingsManager.getConfig().set("Metrics_Enabled", false);
            settingsManager.saveConfig();
        }

        String asyncMessages = settingsManager.getConfig().getString("Messages.Async");
        if (asyncMessages == null) {
            settingsManager.getConfig().set("Messages.Async", true);
            settingsManager.saveConfig();
        }

        boolean metricsEnabled = settingsManager.getConfig().getBoolean("Metrics_Enabled");

        int configVersion = 1;
        if (configVersion != settingsManager.getConfig().getInt("Config_Version")) {
            plugin.getLogger().warning("========================================================================");
            plugin.getLogger().warning("You have an outdated config, Please run the command /chatmanager update!");
            plugin.getLogger().warning("This will take a backup of your entire folder & update your configs.");
            plugin.getLogger().warning("Default values will be used in place of missing options!");
            plugin.getLogger().warning("If you have any issues, Please contact Discord Support.");
            plugin.getLogger().warning("https://discord.gg/mh7Ydaf");
            plugin.getLogger().warning("========================================================================");
        }

        pluginManager = new PluginManager();

        crazyManager = new CrazyManager();

        if (metricsEnabled) {
            MetricsHandler metricsHandler = new MetricsHandler();

            metricsHandler.start();
        }

        crazyManager.load(true);

        if (!PluginSupport.LUCKPERMS.isPluginEnabled()) plugin.getLogger().severe("A permissions plugin was not found. You will likely have issues without one.");

        if (PluginSupport.VAULT.isPluginEnabled()) {
            registerCommands();
            registerEvents();
            setupAutoBroadcast();
            setupChatRadius();
        }
    }

    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        for (Player all : getServer().getOnlinePlayers()) {
            Methods.cm_chatCooldown.remove(all);
            Methods.cm_cooldownTask.remove(all);
            Methods.cm_commandCooldown.remove(all);
            Methods.cm_cooldownTask.remove(all);

            BossBarUtil bossBar = new BossBarUtil();
            bossBar.removeAllBossBars(all);
        }
    }

    public void registerCommands() {
        CommandBroadcast broadCastCommand = new CommandBroadcast();

        registerCommand(getCommand("Announcement"), null, broadCastCommand);
        registerCommand(getCommand("Warning"), null, broadCastCommand);
        registerCommand(getCommand("Broadcast"), null, broadCastCommand);

        registerCommand(getCommand("AutoBroadcast"), new TabCompleteAutoBroadcast(), new CommandAutoBroadcast());

        CommandLists listsCommand = new CommandLists();

        registerCommand(getCommand("List"), null, listsCommand);
        registerCommand(getCommand("Staff"), null, listsCommand);

        registerCommand(getCommand("ClearChat"), null, new CommandClearChat());

        registerCommand(getCommand("BannedCommands"), new TabCompleteBannedCommands(), new CommandBannedCommands());

        registerCommand(getCommand("AntiSwear"), new TabCompleteAntiSwear(), new CommandAntiSwear());

        CommandColor commandColor = new CommandColor();

        registerCommand(getCommand("Colors"), null, commandColor);
        registerCommand(getCommand("Formats"), null, commandColor);

        CommandMessage commandMessage = new CommandMessage();

        registerCommand(getCommand("Reply"), null, commandMessage);
        registerCommand(getCommand("TogglePM"), null, commandMessage);
        registerCommand(getCommand("Message"), new TabCompleteMessage(), commandMessage);

        registerCommand(getCommand("StaffChat"), null, new CommandStaffChat());

        registerCommand(getCommand("ChatRadius"), null, new CommandRadius());

        registerCommand(getCommand("ChatManager"), new TabCompleteChatManager(), new CommandChatManager());

        CommandSpy commandSpy = new CommandSpy();

        registerCommand(getCommand("CommandSpy"), null, commandSpy);
        registerCommand(getCommand("SocialSpy"), null, commandSpy);

        registerCommand(getCommand("MuteChat"), null, new CommandMuteChat());

        registerCommand(getCommand("PerWorldChat"), null, new CommandPerWorldChat());

        registerCommand(getCommand("Ping"), null, new CommandPing());

        registerCommand(getCommand("Rules"), null, new CommandRules());

        registerCommand(getCommand("ToggleChat"), null, new CommandToggleChat());

        registerCommand(getCommand("ToggleMentions"), null, new CommandToggleMentions());
    }

    private void registerCommand(PluginCommand pluginCommand, TabCompleter tabCompleter, CommandExecutor commandExecutor) {
        if (pluginCommand != null) {
            pluginCommand.setExecutor(commandExecutor);

            if (tabCompleter != null) pluginCommand.setTabCompleter(tabCompleter);
        }
    }

    public void registerEvents() {
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
        getServer().getPluginManager().registerEvents(new CommandMOTD(), this);
        getServer().getPluginManager().registerEvents(new ListenerMentions(), this);
        getServer().getPluginManager().registerEvents(new ListenerMuteChat(), this);
        getServer().getPluginManager().registerEvents(new ListenerPerWorldChat(), this);
        getServer().getPluginManager().registerEvents(new ListenerPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new ListenerSpy(), this);
        getServer().getPluginManager().registerEvents(new ListenerStaffChat(), this);
        getServer().getPluginManager().registerEvents(new ListenerSwear(), this);
        getServer().getPluginManager().registerEvents(new ListenerToggleChat(), this);
    }

    public void setupChatRadius() {
        if (settingsManager.getConfig().getBoolean("Chat_Radius.Enable")) {
            for (Player all : getServer().getOnlinePlayers()) {
                if (settingsManager.getConfig().getString("Chat_Radius.Default_Channel").equalsIgnoreCase("Local")) {
                    Methods.cm_localChat.add(all.getUniqueId());
                } else if (settingsManager.getConfig().getString("Chat_Radius.Default_Channel").equalsIgnoreCase("Global")) {
                    Methods.cm_globalChat.add(all.getUniqueId());
                } else if (settingsManager.getConfig().getString("Chat_Radius.Default_Channel").equalsIgnoreCase("World")) {
                    Methods.cm_worldChat.add(all.getUniqueId());
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
        } catch (Exception ex) {
            getLogger().severe("There has been an error setting up auto broadcast. Stacktrace...");
            ex.printStackTrace();
        }
    }

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