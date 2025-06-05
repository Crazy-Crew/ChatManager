package com.ryderbelserion.chatmanager.paper;

import com.ryderbelserion.chatmanager.api.ChatManagerProvider;
import com.ryderbelserion.chatmanager.api.interfaces.IChatManager;
import com.ryderbelserion.chatmanager.api.configs.ConfigManager;
import com.ryderbelserion.chatmanager.paper.api.PaperUserManager;
import com.ryderbelserion.chatmanager.paper.api.objects.PaperHelp;
import com.ryderbelserion.chatmanager.paper.commands.CommandHandler;
import com.ryderbelserion.chatmanager.paper.listeners.CacheListener;
import com.ryderbelserion.chatmanager.paper.listeners.chat.ChatListener;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.fusion.paper.FusionPaper;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import java.util.List;

public class ChatManager extends JavaPlugin implements IChatManager {

    public static ChatManager get() {
        return JavaPlugin.getPlugin(ChatManager.class);
    }

    private PaperCommandManager<CommandSourceStack> manager;
    private PaperUserManager userManager;
    private FileManager fileManager;
    private FusionPaper api;
    private PaperHelp help;

    @Override
    public void onEnable() {
        this.api = new FusionPaper(getComponentLogger(), getDataPath());
        this.api.enable(this);

        this.fileManager = this.api.getFileManager();

        ConfigManager.load();

        this.userManager = new PaperUserManager();

        ChatManagerProvider.register(this);

        final PluginManager pluginManager = getServer().getPluginManager();

        List.of(
                new CacheListener(),
                new ChatListener()
        ).forEach(listener -> pluginManager.registerEvents(listener, this));

        this.manager = PaperCommandManager.builder()
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this);

        this.help = new PaperHelp();
        this.help.init();

        new CommandHandler(this.manager);
    }

    @Override
    public void onDisable() {
        final Server server = getServer();

        if (this.api != null) {
            this.api.disable();
        }

        this.fileManager.purge();

        server.getGlobalRegionScheduler().cancelTasks(this);
        server.getAsyncScheduler().cancelTasks(this);
    }

    @Override
    public final PaperUserManager getUserManager() {
        return this.userManager;
    }

    @Override
    public final FileManager getFileManager() {
        return this.fileManager;
    }

    public final PaperCommandManager<CommandSourceStack> getManager() {
        return this.manager;
    }

    public final FusionPaper getApi() {
        return this.api;
    }

    public final PaperHelp getHelp() {
        return this.help;
    }
}