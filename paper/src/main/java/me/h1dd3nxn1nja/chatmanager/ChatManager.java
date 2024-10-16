package me.h1dd3nxn1nja.chatmanager;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.plugins.papi.PlaceholderAPISupport;
import com.ryderbelserion.chatmanager.plugins.VanishSupport;
import com.ryderbelserion.chatmanager.plugins.VaultSupport;
import com.ryderbelserion.vital.common.api.managers.PluginManager;
import com.ryderbelserion.vital.paper.Vital;
import me.h1dd3nxn1nja.chatmanager.commands.*;
import me.h1dd3nxn1nja.chatmanager.commands.tabcompleter.*;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.listeners.*;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginHandler;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;
import java.util.List;

public class ChatManager extends Vital {

    public static ChatManager get() {
        return JavaPlugin.getPlugin(ChatManager.class);
    }

    private ApiLoader api;

    private PluginHandler pluginHandler;

    public void onEnable() {
        getFileManager().addFile("config.yml")
                .addFile("Messages.yml")
                .addFile("bannedwords.yml")
                .addFile("AutoBroadcast.yml")
                .addFile("bannedcommands.yml")
                .addFolder("Logs")
                .init();

        List.of(
                new VaultSupport(),
                new VanishSupport(),
                new PlaceholderAPISupport()
        ).forEach(PluginManager::registerPlugin);

        PluginManager.printPlugins();

        new CustomMetrics().start();

        this.api = new ApiLoader();
        this.api.load();

        Methods.convert();

        this.pluginHandler = new PluginHandler();
        this.pluginHandler.load();

        registerCommands();
        registerEvents();
        check();
        setupChatRadius();

        registerPermissions();
    }

    public void onDisable() {
        getServer().getGlobalRegionScheduler().cancelTasks(this);
        getServer().getAsyncScheduler().cancelTasks(this);

        for (Player player : getServer().getOnlinePlayers()) {
            this.api.getChatCooldowns().removeUser(player.getUniqueId());
            this.api.getCooldownTask().removeUser(player.getUniqueId());
            this.api.getCmdCooldowns().removeUser(player.getUniqueId());

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

        CommandMessage commandMessage = new CommandMessage();

        registerCommand(getCommand("Reply"), commandMessage, commandMessage);
        registerCommand(getCommand("TogglePM"), commandMessage, commandMessage);
        registerCommand(getCommand("Message"), new TabCompleteMessage(), commandMessage);

        registerCommand(getCommand("StaffChat"), new CommandStaffChat(), new CommandStaffChat());

        registerCommand(getCommand("ChatRadius"), new CommandRadius(), new CommandRadius());

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
        getServer().getPluginManager().registerEvents(new ListenerColor(), this);

        getServer().getPluginManager().registerEvents(new ListenerAntiAdvertising(), this);
        getServer().getPluginManager().registerEvents(new ListenerAntiBot(), this);
        getServer().getPluginManager().registerEvents(new ListenerAntiSpam(), this);

        getServer().getPluginManager().registerEvents(new ListenerAntiUnicode(), this);
        getServer().getPluginManager().registerEvents(new ListenerBannedCommand(), this);
        getServer().getPluginManager().registerEvents(new ListenerCaps(), this);

        getServer().getPluginManager().registerEvents(new ListenerChatFormat(), this);
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
        FileConfiguration config = Files.CONFIG.getConfiguration();
        if (config.getBoolean("Chat_Radius.Enable")) {
            for (Player all : getServer().getOnlinePlayers()) {
                if (config.getString("Chat_Radius.Default_Channel").equalsIgnoreCase("Local")) {
                    this.api.getLocalChatData().addUser(all.getUniqueId());
                } else if (config.getString("Chat_Radius.Default_Channel").equalsIgnoreCase("Global")) {
                    this.api.getGlobalChatData().addUser(all.getUniqueId());
                } else if (config.getString("Chat_Radius.Default_Channel").equalsIgnoreCase("World")) {
                    this.api.getWorldChatData().addUser(all.getUniqueId());
                }
            }
        }
    }

    public void check() {
        FileConfiguration autoBroadcast = Files.AUTO_BROADCAST.getConfiguration();
        if (autoBroadcast.getBoolean("Auto_Broadcast.Actionbar_Messages.Enable")) AutoBroadcastManager.actionbarMessages();
        if (autoBroadcast.getBoolean("Auto_Broadcast.Global_Messages.Enable")) AutoBroadcastManager.globalMessages();
        if (autoBroadcast.getBoolean("Auto_Broadcast.Per_World_Messages.Enable")) AutoBroadcastManager.perWorldMessages();
        if (autoBroadcast.getBoolean("Auto_Broadcast.Title_Messages.Enable")) AutoBroadcastManager.titleMessages();
        if (autoBroadcast.getBoolean("Auto_Broadcast.Bossbar_Messages.Enable")) AutoBroadcastManager.bossBarMessages();
    }

    public ApiLoader api() {
        return this.api;
    }

    public PluginHandler getPluginManager() {
        return this.pluginHandler;
    }

    private void registerPermissions() {
        Arrays.stream(Permissions.values()).toList().forEach(permission -> {
            Permission newPermission = new Permission(
                    permission.getNode(),
                    permission.getDescription(),
                    permission.isDefault(),
                    permission.getChildren()
            );

            getServer().getPluginManager().addPermission(newPermission);
        });
    }
}