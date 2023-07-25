package me.h1dd3nxn1nja.chatmanager;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.CrazyManager;
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

    private SettingsManager settingsManager;

    private CrazyManager crazyManager;

    private PluginManager pluginManager;

    private ApiLoader api;

    public void onEnable() {
        plugin = this;

        if (!PluginSupport.VAULT.isPluginEnabled()) {
            getLogger().warning("Vault is required to use ChatManager, Disabling plugin!");
            getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        settingsManager = new SettingsManager();

        settingsManager.setup();

        api = new ApiLoader();

        api.load();

        boolean metricsEnabled = settingsManager.getConfig().getBoolean("Metrics_Enabled");

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
        if (!PluginSupport.VAULT.isPluginEnabled()) return;

        getServer().getScheduler().cancelTasks(this);

        for (Player player : getServer().getOnlinePlayers()) {
            api.getChatCooldowns().removeUser(player.getUniqueId());
            api.getCooldownTask().removeUser(player.getUniqueId());
            api.getCmdCooldowns().removeUser(player.getUniqueId());

            BossBarUtil bossBar = new BossBarUtil();
            bossBar.removeAllBossBars(player);
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
            check(settingsManager);
        } catch (Exception e) {
            getLogger().severe("There has been an error setting up auto broadcast. Stacktrace...");

            e.printStackTrace();
        }
    }

    public static void check(SettingsManager settingsManager) {
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
    }

    public static ChatManager getPlugin() {
        return plugin;
    }

    public ApiLoader api() {
        return this.api;
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