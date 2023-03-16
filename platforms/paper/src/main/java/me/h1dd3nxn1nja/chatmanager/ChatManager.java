package me.h1dd3nxn1nja.chatmanager;

import com.ryderbelserion.ApiLoader;
import com.ryderbelserion.api.CrazyManager;
import me.h1dd3nxn1nja.chatmanager.commands.CommandAntiSwear;
import me.h1dd3nxn1nja.chatmanager.commands.CommandAutoBroadcast;
import me.h1dd3nxn1nja.chatmanager.commands.CommandBannedCommands;
import me.h1dd3nxn1nja.chatmanager.commands.CommandBroadcast;
import me.h1dd3nxn1nja.chatmanager.commands.CommandChatManager;
import me.h1dd3nxn1nja.chatmanager.commands.CommandClearChat;
import me.h1dd3nxn1nja.chatmanager.commands.CommandColor;
import me.h1dd3nxn1nja.chatmanager.commands.CommandLists;
import me.h1dd3nxn1nja.chatmanager.commands.CommandMOTD;
import me.h1dd3nxn1nja.chatmanager.commands.CommandMessage;
import me.h1dd3nxn1nja.chatmanager.commands.CommandMuteChat;
import me.h1dd3nxn1nja.chatmanager.commands.CommandPerWorldChat;
import me.h1dd3nxn1nja.chatmanager.commands.CommandPing;
import me.h1dd3nxn1nja.chatmanager.commands.CommandRadius;
import me.h1dd3nxn1nja.chatmanager.commands.CommandRules;
import me.h1dd3nxn1nja.chatmanager.commands.CommandSpy;
import me.h1dd3nxn1nja.chatmanager.commands.CommandStaffChat;
import me.h1dd3nxn1nja.chatmanager.commands.CommandToggleChat;
import me.h1dd3nxn1nja.chatmanager.commands.CommandToggleMentions;
import me.h1dd3nxn1nja.chatmanager.commands.tabcompleter.TabCompleteAntiSwear;
import me.h1dd3nxn1nja.chatmanager.commands.tabcompleter.TabCompleteAutoBroadcast;
import me.h1dd3nxn1nja.chatmanager.commands.tabcompleter.TabCompleteBannedCommands;
import me.h1dd3nxn1nja.chatmanager.commands.tabcompleter.TabCompleteChatManager;
import me.h1dd3nxn1nja.chatmanager.commands.tabcompleter.TabCompleteMessage;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerAntiAdvertising;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerAntiBot;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerAntiSpam;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerAntiUnicode;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerBannedCommand;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerCaps;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerChatFormat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerColor;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerGrammar;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerLogs;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerMentions;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerMuteChat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerPerWorldChat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerPlayerJoin;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerRadius;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerSpy;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerStaffChat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerSwear;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerToggleChat;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import com.ryderbelserion.support.PluginManager;
import com.ryderbelserion.support.PluginSupport;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager extends JavaPlugin implements Listener {

    private static ChatManager plugin;

    private SettingsManager settingsManager;

    private CrazyManager crazyManager;

    private PluginManager pluginManager;

    public void onEnable() {
        plugin = this;

        ApiLoader.load();

        settingsManager = new SettingsManager();

        settingsManager.setup();

        pluginManager = new PluginManager();

        crazyManager = new CrazyManager();

        crazyManager.load(true);

        if (!PluginSupport.LUCKPERMS.isPluginEnabled()) plugin.getLogger().severe("A permissions plugin was not found. You will likely have issues without one.");

        if (PluginSupport.VAULT.isPluginEnabled()) {
            registerCommands();
            registerEvents();
            setupAutoBroadcast(this.settingsManager);
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

    private void registerCommands() {
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

    private void registerEvents() {
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

        getServer().getPluginManager().registerEvents(this, this);
    }

    private void setupChatRadius() {
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

    public static void setupAutoBroadcast(SettingsManager settingsManager) {
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