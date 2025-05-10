package me.h1dd3nxn1nja.chatmanager;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.api.cooldowns.ChatCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CmdCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CooldownTask;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.managers.ServerManager;
import com.ryderbelserion.chatmanager.plugins.VanishSupport;
import com.ryderbelserion.chatmanager.plugins.VaultSupport;
import com.ryderbelserion.chatmanager.plugins.papi.PlaceholderAPISupport;
import com.ryderbelserion.fusion.core.managers.PluginExtension;
import com.ryderbelserion.fusion.core.managers.files.FileType;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.fusion.paper.files.LegacyFileManager;
import me.h1dd3nxn1nja.chatmanager.commands.CommandAntiSwear;
import me.h1dd3nxn1nja.chatmanager.commands.CommandAutoBroadcast;
import me.h1dd3nxn1nja.chatmanager.commands.CommandBannedCommands;
import me.h1dd3nxn1nja.chatmanager.commands.CommandBroadcast;
import me.h1dd3nxn1nja.chatmanager.commands.CommandChatManager;
import me.h1dd3nxn1nja.chatmanager.commands.CommandClearChat;
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
import me.h1dd3nxn1nja.chatmanager.listeners.ChatListener;
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
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerPerWorldChat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerPlayerJoin;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerRadius;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerSpy;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerStaffChat;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerSwear;
import me.h1dd3nxn1nja.chatmanager.listeners.ListenerToggleChat;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginHandler;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ChatManager extends JavaPlugin {

    public static ChatManager get() {
        return JavaPlugin.getPlugin(ChatManager.class);
    }

    private PluginHandler pluginHandler;
    private PluginExtension pluginExtension;
    private LegacyFileManager legacyFileManager;
    private ServerManager serverManager;

    private ApiLoader api;

    @Override
    public void onEnable() {
        final FusionPaper fusion = new FusionPaper(getComponentLogger(), getDataPath());

        fusion.enable(this);

        this.legacyFileManager = fusion.getLegacyFileManager();

        this.legacyFileManager.addFile("config.yml", FileType.YAML)
                .addFile("Messages.yml", FileType.YAML)
                .addFile("bannedwords.yml", FileType.YAML)
                .addFile("AutoBroadcast.yml", FileType.YAML)
                .addFile("bannedcommands.yml", FileType.YAML)
                .addFolder("Logs", FileType.NONE);

        Messages.addMissingMessages();

        this.serverManager = new ServerManager();

        this.pluginExtension = fusion.getPluginExtension();

        List.of(
                new VaultSupport(),
                new VanishSupport(),
                new PlaceholderAPISupport()
        ).forEach(this.pluginExtension::registerPlugin);

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

    @Override
    public void onDisable() {
        final Server server = getServer();

        server.getGlobalRegionScheduler().cancelTasks(this);
        server.getAsyncScheduler().cancelTasks(this);

        final ChatCooldowns cooldowns = this.api.getChatCooldowns();
        final CooldownTask tasks = this.api.getCooldownTask();
        final CmdCooldowns cmds = this.api.getCmdCooldowns();

        for (final Player player : server.getOnlinePlayers()) {
            final UUID uuid = player.getUniqueId();

            cooldowns.removeUser(uuid);
            tasks.removeUser(uuid);
            cmds.removeUser(uuid);

            new BossBarUtil().removeAllBossBars(player);
        }
    }

    public void registerCommands() {
        final CommandBroadcast broadCastCommand = new CommandBroadcast();

        registerCommand(getCommand("Announcement"), null, broadCastCommand);
        registerCommand(getCommand("Warning"), null, broadCastCommand);
        registerCommand(getCommand("Broadcast"), null, broadCastCommand);

        registerCommand(getCommand("AutoBroadcast"), new TabCompleteAutoBroadcast(), new CommandAutoBroadcast());

        final CommandLists listsCommand = new CommandLists();

        registerCommand(getCommand("List"), null, listsCommand);
        registerCommand(getCommand("Staff"), null, listsCommand);

        registerCommand(getCommand("ClearChat"), null, new CommandClearChat());

        registerCommand(getCommand("BannedCommands"), new TabCompleteBannedCommands(), new CommandBannedCommands());

        registerCommand(getCommand("AntiSwear"), new TabCompleteAntiSwear(), new CommandAntiSwear());

        final CommandMessage commandMessage = new CommandMessage();

        registerCommand(getCommand("Reply"), commandMessage, commandMessage);
        registerCommand(getCommand("TogglePM"), commandMessage, commandMessage);
        registerCommand(getCommand("Message"), new TabCompleteMessage(), commandMessage);

        registerCommand(getCommand("StaffChat"), new CommandStaffChat(), new CommandStaffChat());

        registerCommand(getCommand("ChatRadius"), new CommandRadius(), new CommandRadius());

        registerCommand(getCommand("ChatManager"), new TabCompleteChatManager(), new CommandChatManager());

        final CommandSpy commandSpy = new CommandSpy();

        registerCommand(getCommand("CommandSpy"), null, commandSpy);
        registerCommand(getCommand("SocialSpy"), null, commandSpy);

        registerCommand(getCommand("MuteChat"), null, new CommandMuteChat());

        registerCommand(getCommand("PerWorldChat"), null, new CommandPerWorldChat());

        registerCommand(getCommand("Ping"), null, new CommandPing());

        registerCommand(getCommand("Rules"), null, new CommandRules());

        registerCommand(getCommand("ToggleChat"), null, new CommandToggleChat());

        registerCommand(getCommand("ToggleMentions"), null, new CommandToggleMentions());
    }

    private void registerCommand(final PluginCommand pluginCommand, final TabCompleter tabCompleter, final CommandExecutor commandExecutor) {
        if (pluginCommand != null) {
            pluginCommand.setExecutor(commandExecutor);

            if (tabCompleter != null) pluginCommand.setTabCompleter(tabCompleter);
        }
    }

    public void registerEvents() {
        final PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new ChatListener(), this); // register chat listener
        
        pluginManager.registerEvents(new ListenerColor(), this);

        pluginManager.registerEvents(new ListenerAntiAdvertising(), this);
        pluginManager.registerEvents(new ListenerAntiBot(), this);
        pluginManager.registerEvents(new ListenerAntiSpam(), this);

        pluginManager.registerEvents(new ListenerAntiUnicode(), this);
        pluginManager.registerEvents(new ListenerBannedCommand(), this);
        pluginManager.registerEvents(new ListenerCaps(), this);

        pluginManager.registerEvents(new ListenerChatFormat(), this);
        pluginManager.registerEvents(new ListenerRadius(), this);
        pluginManager.registerEvents(new ListenerGrammar(), this);
        pluginManager.registerEvents(new ListenerLogs(), this);
        pluginManager.registerEvents(new CommandMOTD(), this);

        pluginManager.registerEvents(new ListenerMentions(), this);
        pluginManager.registerEvents(new ListenerPerWorldChat(), this);
        pluginManager.registerEvents(new ListenerPlayerJoin(), this);
        pluginManager.registerEvents(new ListenerSpy(), this);
        pluginManager.registerEvents(new ListenerStaffChat(), this);
        pluginManager.registerEvents(new ListenerSwear(), this);
        pluginManager.registerEvents(new ListenerToggleChat(), this);
    }

    public void setupChatRadius() {
        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if (config.getBoolean("Chat_Radius.Enable", false)) {
            for (Player all : getServer().getOnlinePlayers()) {
                if (config.getString("Chat_Radius.Default_Channel", "").equalsIgnoreCase("Local")) {
                    this.api.getLocalChatData().addUser(all.getUniqueId());
                } else if (config.getString("Chat_Radius.Default_Channel", "").equalsIgnoreCase("Global")) {
                    this.api.getGlobalChatData().addUser(all.getUniqueId());
                } else if (config.getString("Chat_Radius.Default_Channel", "").equalsIgnoreCase("World")) {
                    this.api.getWorldChatData().addUser(all.getUniqueId());
                }
            }
        }
    }

    public void check() {
        final FileConfiguration autoBroadcast = Files.AUTO_BROADCAST.getConfiguration();

        if (autoBroadcast.getBoolean("Auto_Broadcast.Actionbar_Messages.Enable", false)) AutoBroadcastManager.actionbarMessages();
        if (autoBroadcast.getBoolean("Auto_Broadcast.Global_Messages.Enable", false)) AutoBroadcastManager.globalMessages();
        if (autoBroadcast.getBoolean("Auto_Broadcast.Per_World_Messages.Enable", false)) AutoBroadcastManager.perWorldMessages();
        if (autoBroadcast.getBoolean("Auto_Broadcast.Title_Messages.Enable", false)) AutoBroadcastManager.titleMessages();
        if (autoBroadcast.getBoolean("Auto_Broadcast.Bossbar_Messages.Enable", false)) AutoBroadcastManager.bossBarMessages();
    }

    public PluginExtension getPluginExtension() {
        return this.pluginExtension;
    }

    public ServerManager getServerManager() {
        return this.serverManager;
    }

    public PluginHandler getPluginManager() {
        return this.pluginHandler;
    }
    
    public ApiLoader api() {
        return this.api;
    }

    private void registerPermissions() {
        final PluginManager pluginManager = getServer().getPluginManager();

        Arrays.stream(Permissions.values()).toList().forEach(permission -> {
            final Permission newPermission = new Permission(
                    permission.getNode(),
                    permission.getDescription(),
                    permission.isDefault(),
                    permission.getChildren()
            );

            pluginManager.addPermission(newPermission);
        });
    }

    public LegacyFileManager getFileManager() {
        return this.legacyFileManager;
    }
}