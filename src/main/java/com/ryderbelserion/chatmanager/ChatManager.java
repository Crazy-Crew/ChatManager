package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.api.configs.ConfigBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import us.crazycrew.crazycore.paper.CrazyCore;
import us.crazycrew.crazycore.paper.CrazyLogger;
import java.util.logging.Logger;

public class ChatManager extends JavaPlugin {

    private static ChatManager plugin;

    private final CrazyCore crazyCore;
    private final ConfigBuilder configBuilder;

    public ChatManager(CrazyCore crazyCore, ConfigBuilder configBuilder) {
        this.crazyCore = crazyCore;

        this.configBuilder = configBuilder;
    }

    @Override
    public @NotNull Logger getLogger() {
        return CrazyLogger.getLogger();
    }

    public CrazyCore getCrazyCore() {
        return this.crazyCore;
    }

    public ConfigBuilder getConfigBuilder() {
        return this.configBuilder;
    }

    public static ChatManager getPlugin() {
        return plugin;
    }
}