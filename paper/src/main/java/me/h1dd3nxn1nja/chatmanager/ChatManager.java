package me.h1dd3nxn1nja.chatmanager;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.managers.ServerManager;
import com.ryderbelserion.chatmanager.plugins.papi.PlaceholderAPISupport;
import com.ryderbelserion.chatmanager.plugins.VanishSupport;
import com.ryderbelserion.chatmanager.plugins.VaultSupport;
import com.ryderbelserion.fusion.core.managers.PluginExtension;
import com.ryderbelserion.fusion.core.managers.files.FileType;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.fusion.paper.files.LegacyFileManager;
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
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;
import java.util.List;

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
        FileConfiguration config = Files.CONFIG.getConfiguration();

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
        FileConfiguration autoBroadcast = Files.AUTO_BROADCAST.getConfiguration();
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
            Permission newPermission = new Permission(
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