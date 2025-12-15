package me.corecraft.chatmanager.paper;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.api.cooldowns.ChatCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CmdCooldowns;
import com.ryderbelserion.chatmanager.api.cooldowns.CooldownTask;
import com.ryderbelserion.chatmanager.common.ChatManager;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.managers.ServerManager;
import com.ryderbelserion.chatmanager.plugins.VanishSupport;
import com.ryderbelserion.chatmanager.plugins.VaultSupport;
import com.ryderbelserion.chatmanager.plugins.papi.PlaceholderAPISupport;
import com.ryderbelserion.fusion.core.api.interfaces.permissions.enums.Mode;
import com.ryderbelserion.fusion.files.enums.FileType;
import com.ryderbelserion.fusion.kyori.mods.ModManager;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.fusion.paper.api.files.PaperFileManager;
import me.corecraft.chatmanager.paper.commands.BaseCommand;
import me.corecraft.chatmanager.paper.listeners.CacheListener;
import me.corecraft.chatmanager.paper.listeners.chat.ChatListener;
import me.h1dd3nxn1nja.chatmanager.Methods;
import me.h1dd3nxn1nja.chatmanager.commands.*;
import me.h1dd3nxn1nja.chatmanager.commands.tabcompleter.*;
import me.h1dd3nxn1nja.chatmanager.listeners.*;
import me.h1dd3nxn1nja.chatmanager.managers.AutoBroadcastManager;
import me.h1dd3nxn1nja.chatmanager.support.PluginHandler;
import me.h1dd3nxn1nja.chatmanager.utils.BossBarUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChatManagerPlatform extends ChatManager {

    private final me.h1dd3nxn1nja.chatmanager.ChatManager plugin;
    private final FusionPaper fusion;
    private final Server server;

    private boolean isLegacy;

    public ChatManagerPlatform(@NotNull final me.h1dd3nxn1nja.chatmanager.ChatManager plugin, @NotNull final FusionPaper fusion) {
        super(fusion, plugin.getDataPath());

        this.plugin = plugin;
        this.fusion = fusion;
        this.server = plugin.getServer();
    }

    private PaperFileManager legacyFileManager;
    private ServerManager serverManager;
    private PluginHandler pluginHandler;
    private ModManager modManager;

    private ApiLoader api;

    @Override
    public void start(@NotNull final Audience audience) {
        this.fusion.init();

        this.fileManager.addFile(path.resolve("root.yml"), FileType.YAML);

        final CommentedConfigurationNode config = com.ryderbelserion.chatmanager.common.enums.Files.root.getYamlConfig();

        this.isLegacy = config.node("use-new-system").getBoolean(false);

        if (this.isLegacy) {
            this.legacyFileManager = (PaperFileManager) this.fileManager;

            this.legacyFileManager.addPaperFile(path.resolve("config.yml"))
                    .addPaperFile(path.resolve("Messages.yml"))
                    .addPaperFile(path.resolve("bannedwords.yml"))
                    .addPaperFile(path.resolve("AutoBroadcast.yml"))
                    .addPaperFile(path.resolve("bannedcommands.yml"))
                    .addFolder(path.resolve("Logs"), FileType.LOG);

            Messages.addMissingMessages();

            this.serverManager = new ServerManager();

            this.modManager = fusion.getModManager();

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

            List.of(
                    new VaultSupport(fusion),
                    new VanishSupport(fusion),
                    new PlaceholderAPISupport(fusion)
            ).forEach(mod -> this.modManager.addMod(mod.key(), mod));

            return;
        }

        super.start(audience);

        getUserRegistry().addUser(this.server.getConsoleSender());

        final PluginManager pluginManager = this.server.getPluginManager();

        List.of(
                new CacheListener(this),
                new ChatListener(this)
        ).forEach(listener -> pluginManager.registerEvents(listener, this.plugin));

        registerCommands();
    }

    @Override
    public void reload() {
        this.fusion.reload();

        if (this.isLegacy) {
            final FileConfiguration config = Files.CONFIG.getConfiguration();

            for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
                this.api.getChatCooldowns().removeUser(player.getUniqueId());
                this.api.getCooldownTask().removeUser(player.getUniqueId());
                this.api.getCmdCooldowns().removeUser(player.getUniqueId());

                BossBarUtil bossBar = new BossBarUtil();
                bossBar.removeAllBossBars(player);

                BossBarUtil bossBarStaff = new BossBarUtil(Methods.placeholders(true, player, Methods.color(config.getString("Staff_Chat.Boss_Bar.Title", "&eStaff Chat"))));

                if (this.api.getStaffChatData().containsUser(player.getUniqueId()) && player.hasPermission("chatmanager.staffchat")) {
                    bossBarStaff.removeStaffBossBar(player);
                    bossBarStaff.setStaffBossBar(player);
                }
            }

            this.legacyFileManager.refresh(false);

            Files.CONFIG.reload();

            Messages.addMissingMessages();

            Files.MESSAGES.reload();
            Files.BANNED_COMMANDS.reload();
            Files.BANNED_WORDS.reload();
            Files.AUTO_BROADCAST.reload();

            this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
            this.server.getAsyncScheduler().cancelTasks(this.plugin);

            check();

            return;
        }

        super.reload();
    }

    @Override
    public void stop() {
        super.stop();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);

        if (this.isLegacy) {
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
    }

    @Override
    public void registerCommands() {
        getPermissionRegistry().start();

        if (this.isLegacy) {
            final CommandBroadcast broadCastCommand = new CommandBroadcast();

            registerCommand(this.plugin.getCommand("Announcement"), null, broadCastCommand);
            registerCommand(this.plugin.getCommand("Warning"), null, broadCastCommand);
            registerCommand(this.plugin.getCommand("Broadcast"), null, broadCastCommand);

            registerCommand(this.plugin.getCommand("AutoBroadcast"), new TabCompleteAutoBroadcast(), new CommandAutoBroadcast());

            final CommandLists listsCommand = new CommandLists();

            registerCommand(this.plugin.getCommand("List"), null, listsCommand);
            registerCommand(this.plugin.getCommand("Staff"), null, listsCommand);

            registerCommand(this.plugin.getCommand("ClearChat"), null, new CommandClearChat());

            registerCommand(this.plugin.getCommand("BannedCommands"), new TabCompleteBannedCommands(), new CommandBannedCommands());

            registerCommand(this.plugin.getCommand("AntiSwear"), new TabCompleteAntiSwear(), new CommandAntiSwear());

            final CommandMessage commandMessage = new CommandMessage();

            registerCommand(this.plugin.getCommand("Reply"), commandMessage, commandMessage);
            registerCommand(this.plugin.getCommand("TogglePM"), commandMessage, commandMessage);
            registerCommand(this.plugin.getCommand("Message"), new TabCompleteMessage(), commandMessage);

            registerCommand(this.plugin.getCommand("StaffChat"), new CommandStaffChat(), new CommandStaffChat());

            registerCommand(this.plugin.getCommand("ChatRadius"), new CommandRadius(), new CommandRadius());

            registerCommand(this.plugin.getCommand("ChatManager"), new TabCompleteChatManager(), new CommandChatManager());

            final CommandSpy commandSpy = new CommandSpy();

            registerCommand(this.plugin.getCommand("CommandSpy"), null, commandSpy);
            registerCommand(this.plugin.getCommand("SocialSpy"), null, commandSpy);

            registerCommand(this.plugin.getCommand("MuteChat"), null, new CommandMuteChat());

            registerCommand(this.plugin.getCommand("PerWorldChat"), null, new CommandPerWorldChat());

            registerCommand(this.plugin.getCommand("Ping"), null, new CommandPing());

            registerCommand(this.plugin.getCommand("Rules"), null, new CommandRules());

            registerCommand(this.plugin.getCommand("ToggleChat"), null, new CommandToggleChat());

            registerCommand(this.plugin.getCommand("ToggleMentions"), null, new CommandToggleMentions());

            registerCommand(this.plugin.getCommand("Motd"), null, new CommandMOTD());

            return;
        }

        new BaseCommand(PaperCommandManager.builder()
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this.plugin));
    }

    @Override
    public void broadcast(@NotNull final Component component, @NotNull final String permission) {
        if (permission.isEmpty()) {
            this.server.broadcast(component);

            return;
        }

        this.server.broadcast(component, permission);
    }

    @Override
    public final boolean hasPermission(@NotNull final Audience audience, @NotNull final String permission) {
        final CommandSender sender = (CommandSender) audience;

        return sender.hasPermission(permission);
    }

    @Override
    public final boolean isConsoleSender(@NotNull final Audience audience) {
        return audience instanceof ConsoleCommandSender;
    }

    @Override
    public void registerPermission(@NotNull final Mode mode, @NotNull final String parent, @NotNull final String description, @NotNull final Map<String, Boolean> children) {
        PermissionDefault permissionDefault;

        switch (mode) {
            case NOT_OP -> permissionDefault = PermissionDefault.NOT_OP;
            case TRUE -> permissionDefault = PermissionDefault.TRUE;
            case FALSE -> permissionDefault = PermissionDefault.FALSE;
            default -> permissionDefault = PermissionDefault.OP;
        }

        if (isPermissionRegistered(parent)) return;

        final PluginManager pluginManager = this.server.getPluginManager();

        final Permission permission = new Permission(
                parent,
                description,
                permissionDefault,
                children
        );

        pluginManager.addPermission(permission);
    }

    @Override
    public void unregisterPermission(@NotNull final String parent) {
        if (!isPermissionRegistered(parent)) return;

        final PluginManager pluginManager = this.server.getPluginManager();

        pluginManager.removePermission(parent);
    }

    @Override
    public boolean isPermissionRegistered(@NotNull final String parent) {
        final PluginManager pluginManager = this.server.getPluginManager();

        return pluginManager.getPermission(parent) != null;
    }

    @Override
    public void broadcast(@NotNull Component component) {
        broadcast(component, "");
    }

    @Override
    public @NotNull FusionPaper getFusion() {
        return this.fusion;
    }

    public @NotNull final me.h1dd3nxn1nja.chatmanager.ChatManager getPlugin() {
        return this.plugin;
    }

    private void registerCommand(final PluginCommand pluginCommand, final TabCompleter tabCompleter, final CommandExecutor commandExecutor) {
        if (pluginCommand != null) {
            pluginCommand.setExecutor(commandExecutor);

            if (tabCompleter != null) pluginCommand.setTabCompleter(tabCompleter);
        }
    }

    private void registerEvents() {
        final PluginManager pluginManager = this.plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new me.h1dd3nxn1nja.chatmanager.listeners.ChatListener(), this.plugin); // register chat listener

        pluginManager.registerEvents(new ListenerColor(), this.plugin);

        pluginManager.registerEvents(new ListenerAntiAdvertising(), this.plugin);
        pluginManager.registerEvents(new ListenerAntiBot(), this.plugin);
        pluginManager.registerEvents(new ListenerAntiSpam(), this.plugin);

        pluginManager.registerEvents(new ListenerAntiUnicode(), this.plugin);
        pluginManager.registerEvents(new ListenerBannedCommand(), this.plugin);
        pluginManager.registerEvents(new ListenerCaps(), this.plugin);

        pluginManager.registerEvents(new ListenerChatFormat(), this.plugin);
        pluginManager.registerEvents(new ListenerRadius(), this.plugin);
        pluginManager.registerEvents(new ListenerLogs(), this.plugin);

        pluginManager.registerEvents(new ListenerMentions(), this.plugin);
        pluginManager.registerEvents(new ListenerPerWorldChat(), this.plugin);
        pluginManager.registerEvents(new ListenerPlayerJoin(), this.plugin);
        pluginManager.registerEvents(new ListenerSpy(), this.plugin);
        pluginManager.registerEvents(new ListenerStaffChat(), this.plugin);
        pluginManager.registerEvents(new ListenerSwear(), this.plugin);
        pluginManager.registerEvents(new ListenerToggleChat(), this.plugin);
    }

    public void setupChatRadius() {
        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if (config.getBoolean("Chat_Radius.Enable", false)) {
            for (Player all : this.plugin.getServer().getOnlinePlayers()) {
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

    private void registerPermissions() {
        final PluginManager pluginManager = this.plugin.getServer().getPluginManager();

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

    public @NotNull final ServerManager getServerManager() {
        return this.serverManager;
    }

    public @NotNull final PluginHandler getPluginManager() {
        return this.pluginHandler;
    }

    public @NotNull final PaperFileManager getLegacyFileManager() {
        return this.legacyFileManager;
    }

    public @NotNull final ModManager getModManager() {
        return this.modManager;
    }

    public @NotNull final ApiLoader api() {
        return this.api;
    }
}