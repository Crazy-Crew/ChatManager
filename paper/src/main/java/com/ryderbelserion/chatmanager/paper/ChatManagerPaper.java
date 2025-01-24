package com.ryderbelserion.chatmanager.paper;

import com.ryderbelserion.chatmanager.core.ChatProvider;
import com.ryderbelserion.chatmanager.core.api.IChatManager;
import com.ryderbelserion.chatmanager.core.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.paper.api.PaperUserManager;
import com.ryderbelserion.chatmanager.paper.commands.AnnotationManager;
import com.ryderbelserion.chatmanager.paper.listeners.CoreListener;
import com.ryderbelserion.chatmanager.paper.listeners.chat.ChatListener;
import com.ryderbelserion.core.util.FileUtils;
import com.ryderbelserion.paper.FusionApi;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

import java.io.File;
import java.util.List;

public class ChatManagerPaper implements IChatManager {

    private final ChatManagerPlugin plugin;
    private final Server server;

    private final PaperCommandManager<CommandSourceStack> commandManager;

    public ChatManagerPaper(final ChatManagerPlugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();

        this.commandManager = PaperCommandManager.builder().executionCoordinator(ExecutionCoordinator.simpleCoordinator()).buildOnEnable(this.plugin);
    }

    private final FusionApi api = FusionApi.get();

    private PaperUserManager userManager;

    @Override
    public void start() {
        // enable fusion api
        this.api.enable(this.plugin);

        // extract locale folder
        FileUtils.extracts("/locale/", new File(this.plugin.getDataFolder(), "locale").toPath(), false);

        // load configuration
        ConfigManager.load();

        // create user manager
        this.userManager = new PaperUserManager();

        // register api method #1 for internal use or platform independent use where we don't have a service provider.
        ChatProvider.register(this);

        // register api method #2 for the service provider which is the preferred way to access the api.
        this.server.getServicesManager().register(IChatManager.class, this, this.plugin, ServicePriority.Normal);

        // register events
        final PluginManager pluginManager = this.server.getPluginManager();

        List.of(
                new ChatListener(),
                new CoreListener()
        ).forEach(event -> pluginManager.registerEvents(event, this.plugin));

        // register commands
        new AnnotationManager(this.plugin);
    }

    @Override
    public void refresh() {
        // reload config
        ConfigManager.reload();
    }

    @Override
    public void stop() {
        if (this.api != null) {
            this.api.disable();
        }

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);
    }

    @Override
    public PaperUserManager getUserManager() {
        return this.userManager;
    }

    public final PaperCommandManager<CommandSourceStack> getCommandManager() {
        return commandManager;
    }
}