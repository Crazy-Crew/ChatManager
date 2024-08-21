package com.ryderbelserion.chatmanager;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.ryderbelserion.chatmanager.api.CustomMetrics;
import com.ryderbelserion.chatmanager.api.cache.CacheListener;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.commands.BaseCommand;
import com.ryderbelserion.chatmanager.commands.subs.simple.CommandMotd;
import com.ryderbelserion.chatmanager.commands.subs.simple.CommandRules;
import com.ryderbelserion.chatmanager.commands.subs.staff.CommandReload;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.listeners.chat.ChatListener;
import com.ryderbelserion.chatmanager.listeners.chat.DelayListener;
import com.ryderbelserion.chatmanager.listeners.staff.SpyListener;
import com.ryderbelserion.chatmanager.listeners.staff.StaffListener;
import com.ryderbelserion.chatmanager.utils.TaskUtils;
import com.ryderbelserion.vital.paper.Vital;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public class ChatManager extends Vital {

    public static ChatManager get() {
        return JavaPlugin.getPlugin(ChatManager.class);
    }

    private final UserManager userManager;

    public ChatManager() {
        this.userManager = new UserManager();
    }

    @Override
    public void onEnable() {
        // Load configuration file!
        ConfigManager.load();

        // Register cache listener!
        getServer().getPluginManager().registerEvents(new CacheListener(), this);

        // Register core listeners!
        getServer().getPluginManager().registerEvents(new DelayListener(), this);
        getServer().getPluginManager().registerEvents(new StaffListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new SpyListener(), this);

        // Monitor staff changes
        TaskUtils.startMonitoringTask();

        // Register commands.
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            LiteralArgumentBuilder<CommandSourceStack> root = new BaseCommand().registerPermission().literal().createBuilder();

            List.of(
                    new CommandReload(),
                    new CommandRules(),
                    new CommandMotd()
            ).forEach(command -> root.then(command.registerPermission().literal()));

            event.registrar().register(root.build(), "the base command for RedstonePvP");
        });

        new CustomMetrics().start();
    }

    @Override
    public void onDisable() {
        // Nuke all data on shutdown.
        getUserManager().purge();
    }

    public final UserManager getUserManager() {
        return this.userManager;
    }
}