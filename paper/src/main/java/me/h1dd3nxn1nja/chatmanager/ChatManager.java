package me.h1dd3nxn1nja.chatmanager;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.api.chat.GlobalChatData;
import com.ryderbelserion.chatmanager.api.chat.LocalChatData;
import com.ryderbelserion.chatmanager.api.chat.WorldChatData;
import com.ryderbelserion.chatmanager.api.cooldowns.ChatCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CmdCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CooldownTask;
import com.ryderbelserion.chatmanager.commands.BaseCommand;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.listeners.TrafficListener;
import com.ryderbelserion.chatmanager.managers.ConfigManager;
import com.ryderbelserion.chatmanager.managers.ServerManager;
import com.ryderbelserion.chatmanager.managers.UserManager;
import com.ryderbelserion.chatmanager.plugins.papi.PlaceholderAPISupport;
import com.ryderbelserion.chatmanager.plugins.VaultSupport;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.fusion.core.managers.PluginExtension;
import com.ryderbelserion.fusion.core.managers.files.FileManager;
import com.ryderbelserion.fusion.core.managers.files.FileType;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.fusion.paper.files.LegacyFileManager;
import me.h1dd3nxn1nja.chatmanager.listeners.*;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginHandler;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ChatManager extends JavaPlugin {

    public static ChatManager get() {
        return JavaPlugin.getPlugin(ChatManager.class);
    }

    private ApiLoader api;

    private PluginHandler pluginHandler;
    private PluginExtension pluginExtension;
    private LegacyFileManager legacyFileManager;
    private FileManager fileManager;

    private ServerManager serverManager;
    private UserManager userManager;

    @Override
    public void onEnable() {
        final FusionPaper fusion = new FusionPaper(getComponentLogger(), getDataPath());

        this.legacyFileManager = fusion.getLegacyFileManager();

        this.legacyFileManager.addFile("config.yml", FileType.YAML)
                .addFile("Messages.yml", FileType.YAML)
                .addFile("bannedwords.yml", FileType.YAML)
                .addFile("AutoBroadcast.yml", FileType.YAML)
                .addFile("bannedcommands.yml", FileType.YAML)
                .addFolder("Logs", FileType.NONE);

        this.fileManager = fusion.getFileManager();

        ConfigManager.load();

        Messages.addMissingMessages();

        this.serverManager = new ServerManager();
        this.userManager = new UserManager();

        this.pluginExtension = fusion.getPluginExtension();

        List.of(
                new PlaceholderAPISupport(),
                new VaultSupport()
        ).forEach(this.pluginExtension::registerPlugin);

        new CustomMetrics().start();

        this.api = new ApiLoader();
        this.api.load();

        Methods.convert();

        this.pluginHandler = new PluginHandler();
        this.pluginHandler.load();

        registerEvents();
        check();
        setupChatRadius();

        new BaseCommand(PaperCommandManager.builder()
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this));
    }

    @Override
    public void onDisable() {
        final Server server = getServer();

        server.getGlobalRegionScheduler().cancelTasks(this);
        server.getAsyncScheduler().cancelTasks(this);

        final ChatCooldowns cooldowns = this.api.getChatCooldowns();
        final CooldownTask tasks = this.api.getCooldownTask();
        final CmdCooldowns cmd = this.api.getCmdCooldowns();

        for (final Player player : server.getOnlinePlayers()) {
            final UUID uuid = player.getUniqueId();

            cooldowns.removeUser(uuid);
            tasks.removeUser(uuid);
            cmd.removeUser(uuid);

            BossBarUtil bossBar = new BossBarUtil();
            bossBar.removeAllBossBars(player);
        }
    }

    public void registerEvents() {
        final PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new TrafficListener(), this); // register cache listener
        pluginManager.registerEvents(new ChatListener(), this); // register chat listener

        pluginManager.registerEvents(new ListenerColor(), this);

        pluginManager.registerEvents(new ListenerAntiAdvertising(), this);
        pluginManager.registerEvents(new ListenerAntiSpam(), this);
        pluginManager.registerEvents(new ListenerAntiBot(), this);

        pluginManager.registerEvents(new ListenerBannedCommand(), this);
        pluginManager.registerEvents(new ListenerAntiUnicode(), this);
        pluginManager.registerEvents(new ListenerCaps(), this);

        pluginManager.registerEvents(new ListenerChatFormat(), this);
        pluginManager.registerEvents(new ListenerRadius(), this);
        pluginManager.registerEvents(new ListenerLogs(), this);

        pluginManager.registerEvents(new ListenerPerWorldChat(), this);
        pluginManager.registerEvents(new ListenerPlayerJoin(), this);
        pluginManager.registerEvents(new ListenerStaffChat(), this);
        pluginManager.registerEvents(new ListenerMentions(), this);
        pluginManager.registerEvents(new ListenerSwear(), this);
        pluginManager.registerEvents(new ListenerSpy(), this);
    }

    public void setupChatRadius() {
        FileConfiguration config = Files.CONFIG.getConfiguration();

        if (config.getBoolean("Chat_Radius.Enable", false)) {
            final LocalChatData local = this.api.getLocalChatData();
            final GlobalChatData global = this.api.getGlobalChatData();
            final WorldChatData world = this.api.getWorldChatData();

            for (final Player player : getServer().getOnlinePlayers()) {
                final UUID uuid = player.getUniqueId();

                if (config.getString("Chat_Radius.Default_Channel", "").equalsIgnoreCase("Local")) {
                    local.addUser(uuid);
                } else if (config.getString("Chat_Radius.Default_Channel", "").equalsIgnoreCase("Global")) {
                    global.addUser(uuid);
                } else if (config.getString("Chat_Radius.Default_Channel", "").equalsIgnoreCase("World")) {
                    world.addUser(uuid);
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

    public LegacyFileManager getLegacyFileManager() {
        return this.legacyFileManager;
    }

    public PluginExtension getPluginExtension() {
        return pluginExtension;
    }

    public FileManager getFileManager() {
        return this.fileManager;
    }

    public ServerManager getServerManager() {
        return this.serverManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }
}