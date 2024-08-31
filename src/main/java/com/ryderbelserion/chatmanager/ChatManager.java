package com.ryderbelserion.chatmanager;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.api.cache.listeners.CacheListener;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.commands.BaseCommand;
import com.ryderbelserion.chatmanager.commands.subs.misc.CommandMotd;
import com.ryderbelserion.chatmanager.commands.subs.misc.CommandRules;
import com.ryderbelserion.chatmanager.commands.subs.player.CommandHelp;
import com.ryderbelserion.chatmanager.commands.subs.player.CommandRadius;
import com.ryderbelserion.chatmanager.commands.subs.player.conversations.CommandMsg;
import com.ryderbelserion.chatmanager.commands.subs.player.conversations.CommandReply;
import com.ryderbelserion.chatmanager.commands.subs.staff.CommandReload;
import com.ryderbelserion.chatmanager.commands.subs.staff.CommandSpy;
import com.ryderbelserion.chatmanager.commands.subs.staff.CommandToggle;
import com.ryderbelserion.chatmanager.commands.subs.staff.chat.CommandClearChat;
import com.ryderbelserion.chatmanager.commands.subs.staff.chat.CommandStaffChat;
import com.ryderbelserion.chatmanager.commands.subs.staff.filter.CommandFilter;
import com.ryderbelserion.chatmanager.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.listeners.TrafficListener;
import com.ryderbelserion.chatmanager.listeners.chat.ChatListener;
import com.ryderbelserion.chatmanager.listeners.chat.DelayListener;
import com.ryderbelserion.chatmanager.listeners.chat.FilterListener;
import com.ryderbelserion.chatmanager.listeners.staff.SpyListener;
import com.ryderbelserion.chatmanager.listeners.staff.StaffListener;
import com.ryderbelserion.chatmanager.listeners.staff.logs.HistoryListener;
import com.ryderbelserion.chatmanager.managers.BroadcastManager;
import com.ryderbelserion.chatmanager.utils.LogUtils;
import com.ryderbelserion.chatmanager.utils.TaskUtils;
import com.ryderbelserion.chatmanager.utils.support.PlaceholderAPISupport;
import com.ryderbelserion.chatmanager.utils.support.VaultSupport;
import com.ryderbelserion.chatmanager.utils.support.generic.GenericVanish;
import com.ryderbelserion.vital.common.managers.PluginManager;
import com.ryderbelserion.vital.paper.Vital;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.List;
import java.util.Locale;

public class ChatManager extends Vital {

    public static ChatManager get() {
        return JavaPlugin.getPlugin(ChatManager.class);
    }

    private final long startTime;

    public ChatManager() {
        this.startTime = System.nanoTime();
    }

    private UserManager userManager;

    @Override
    public void onEnable() {
        this.userManager = new UserManager();

        getFileManager().addFile(new File(getDataFolder(), "broadcast.yml")).init();

        ConfigManager.load();

        LogUtils.zip();
        LogUtils.create();

        List.of(
                new VaultSupport(),
                new GenericVanish(),
                //new EssentialsSupport(),
                new PlaceholderAPISupport()
        ).forEach(PluginManager::registerPlugin);

        PluginManager.printPlugins();

        List.of(
                new CacheListener(),

                new TrafficListener(),
                new DelayListener(),
                new StaffListener(),

                new FilterListener(),

                new HistoryListener(),
                new ChatListener(),
                new SpyListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        BroadcastManager.start();

        // Monitor staff changes
        TaskUtils.startMonitoringTask();

        // Register commands.
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            LiteralArgumentBuilder<CommandSourceStack> root = new BaseCommand().registerPermission().literal().createBuilder();

            List.of(
                    new CommandFilter(),

                    new CommandReload(),
                    new CommandRules(),
                    new CommandHelp(),
                    new CommandMotd(),

                    new CommandSpy(),
                    new CommandRadius(),
                    new CommandToggle(),

                    new CommandStaffChat(),
                    new CommandClearChat(),

                    new CommandReply(),
                    new CommandMsg()
            ).forEach(command -> root.then(command.registerPermission().literal()));

            event.registrar().register(root.build(), "the base command for RedstonePvP");
        });

        // Start metrics
        new CustomMetrics().start();

        if (isVerbose()) {
            getComponentLogger().info("Done ({})!", String.format(Locale.ROOT, "%.3fs", (double) (System.nanoTime() - this.startTime) / 1.0E9D));
        }
    }

    @Override
    public void onDisable() {
        // Nuke all data on shutdown.
        getUserManager().purge();
    }

    public final UserManager getUserManager() {
        return this.userManager;
    }

    @Override
    public final boolean isLegacy() {
        return false;
    }
}