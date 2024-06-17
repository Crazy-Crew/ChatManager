package me.h1dd3nxn1nja.chatmanager;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.api.CrazyManager;
import com.ryderbelserion.vital.paper.files.config.FileManager;
import me.h1dd3nxn1nja.chatmanager.commands.*;
import me.h1dd3nxn1nja.chatmanager.commands.tabcompleter.*;
import com.ryderbelserion.chatmanager.enums.Permissions;
import me.h1dd3nxn1nja.chatmanager.listeners.*;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginSupport;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import me.h1dd3nxn1nja.chatmanager.utils.MetricsHandler;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

public class ChatManager extends JavaPlugin {

    @NotNull
    public static ChatManager get() {
        return JavaPlugin.getPlugin(ChatManager.class);
    }

    private ApiLoader api;
    
    private FileManager fileManager;

    private CrazyManager crazyManager;

    private PluginManager pluginManager;
    private Methods methods;

    public void onEnable() {
        if (!PluginSupport.VAULT.isPluginEnabled()) {
            getLogger().warning("Vault is required to use ChatManager, Disabling plugin!");
            getServer().getPluginManager().disablePlugin(this);

            return;
        }

        this.fileManager = new FileManager();
        this.fileManager
                .addFile("config.yml")
                .addFile("Messages.yml")
                .addFile("bannedwords.yml")
                .addFile("AutoBroadcast.yml")
                .addFile("bannedcommands.yml")
                .addFolder("Logs");

        this.methods = new Methods();

        this.api = new ApiLoader();
        this.api.load();

        FileConfiguration config = Files.CONFIG.getConfiguration();

        this.methods.convert();

        boolean metricsEnabled = config.getBoolean("Metrics_Enabled", false);

        this.pluginManager = new PluginManager();

        this.crazyManager = new CrazyManager();

        if (metricsEnabled) {
            MetricsHandler metricsHandler = new MetricsHandler();

            metricsHandler.start();
        }

        this.crazyManager.load(true);

        if (!PluginSupport.LUCKPERMS.isPluginEnabled()) {
            getLogger().severe("A permissions plugin was not found. You will likely have issues without one.");
        }

        if (PluginSupport.VAULT.isPluginEnabled()) {
            registerCommands();
            registerEvents();
            check();
            setupChatRadius();
        }

        registerPermissions();
    }

    public void onDisable() {
        if (!PluginSupport.VAULT.isPluginEnabled()) return;

        getServer().getScheduler().cancelTasks(this);

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

    public Methods getMethods() {
        return this.methods;
    }

    public ApiLoader api() {
        return this.api;
    }

    public CrazyManager getCrazyManager() {
        return this.crazyManager;
    }

    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public FileManager getFileManager() {
        return this.fileManager;
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