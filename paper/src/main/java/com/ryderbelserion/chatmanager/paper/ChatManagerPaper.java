package com.ryderbelserion.chatmanager.paper;

import com.ryderbelserion.chatmanager.core.ChatProvider;
import com.ryderbelserion.chatmanager.core.api.IChatManager;
import com.ryderbelserion.chatmanager.paper.api.PaperUserManager;
import com.ryderbelserion.chatmanager.paper.listeners.CoreListener;
import com.ryderbelserion.chatmanager.paper.listeners.chat.ChatListener;
import com.ryderbelserion.core.api.enums.FileType;
import com.ryderbelserion.core.files.FileManager;
import com.ryderbelserion.paper.Fusion;
import com.ryderbelserion.paper.FusionApi;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import java.util.List;

public class ChatManagerPaper implements IChatManager {

    private final ChatManagerPlugin plugin;
    private final Server server;

    public ChatManagerPaper(final ChatManagerPlugin plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    private final FusionApi api = FusionApi.get();

    private final Fusion fusion = this.api.getFusion();

    private final FileManager fileManager = this.fusion.getFileManager();

    private PaperUserManager userManager;

    @Override
    public void start() {
        this.api.enable(this.plugin);

        this.fileManager.addFile("config.yml", FileType.YAML).addFile("chat.yml", FileType.YAML).addFolder("locale", FileType.YAML);

        this.userManager = new PaperUserManager();

        // register api method #1 for internal use or platform independent use where we don't have a service provider.
        ChatProvider.register(this);

        // register api method #2 for the service provider which is the preferred way to access the api.
        this.server.getServicesManager().register(IChatManager.class, this, this.plugin, ServicePriority.Normal);

        final PluginManager pluginManager = this.server.getPluginManager();

        List.of(
                new ChatListener(),
                new CoreListener()
        ).forEach(event -> pluginManager.registerEvents(event, this.plugin));
    }

    @Override
    public void refresh() {
        this.fileManager.reloadFiles();
    }

    @Override
    public void stop() {
        if (this.api != null) {
            this.api.disable();
        }

        this.fileManager.purge();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);
    }

    @Override
    public FileManager getManager() {
        return this.fileManager;
    }

    @Override
    public PaperUserManager getUserManager() {
        return this.userManager;
    }
}