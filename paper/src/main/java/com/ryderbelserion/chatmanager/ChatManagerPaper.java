package com.ryderbelserion.chatmanager;

import com.ryderbelserion.FusionApi;
import com.ryderbelserion.chatmanager.api.ChatManager;
import com.ryderbelserion.chatmanager.api.users.UserManager;
import com.ryderbelserion.chatmanager.common.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.common.plugin.AbstractChatPlugin;
import com.ryderbelserion.chatmanager.listeners.chat.ChatListener;
import com.ryderbelserion.chatmanager.loader.ChatManagerPlugin;
import com.ryderbelserion.chatmanager.api.users.PaperUserManager;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.Locale;

public class ChatManagerPaper extends AbstractChatPlugin {

    private PaperUserManager userManager;

    private final ChatManagerPlugin plugin;
    private final long startTime;
    private final Server server;

    private final FusionApi fusionApi = FusionApi.get();

    public ChatManagerPaper(final ChatManagerPlugin plugin) {
        this.startTime = System.nanoTime();

        this.plugin = plugin;
        this.server = plugin.getServer();
    }

    @Override
    public void onLoad() {
        this.fusionApi.enable(this.plugin);

        ConfigManager.load();
    }

    @Override
    public void onEnable() {
        this.userManager = new PaperUserManager(this);

        super.enable(); // enable api

        getLogger().info("Done ({})!", String.format(Locale.ROOT, "%.3fs", (double) (System.nanoTime() - this.startTime) / 1.0E9D));
    }

    @Override
    public void onDisable() {
        if (this.fusionApi != null) {
            this.fusionApi.disable();
        }

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);
    }

    @Override
    protected void registerPlatformAPI(final ChatManager api) {
        this.server.getServicesManager().register(ChatManager.class, api, this.plugin, ServicePriority.Normal);
    }

    @Override
    protected void registerListeners() {
        final PluginManager pluginManager = this.server.getPluginManager();

        pluginManager.registerEvents(new ChatListener(), this.plugin);
    }

    @Override
    protected void registerCommands() {
        /*this.plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            LiteralArgumentBuilder<CommandSourceStack> root = new BaseCommand(this).registerPermission().literal().createBuilder();

            List.of(
                    new CommandReload(this)
            ).forEach(command -> root.then(command.registerPermission().literal()));

            event.registrar().register(root.build(), "the base command for ChatManager");
        });*/
    }

    @Override
    public @NotNull final UserManager getUserManager() {
        return this.userManager;
    }

    @Override
    public @NotNull final File getDataFolder() {
        return this.plugin.getDataFolder();
    }

    public @NotNull final ChatManagerPlugin getPlugin() {
        return this.plugin;
    }
}