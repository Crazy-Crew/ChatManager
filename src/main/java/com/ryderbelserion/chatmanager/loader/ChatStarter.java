package com.ryderbelserion.chatmanager.loader;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.ConfigBuilder;
import com.ryderbelserion.chatmanager.api.configs.migrate.manuel.FilterMigration;
import com.ryderbelserion.chatmanager.api.configs.types.PluginSettings;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import us.crazycrew.crazycore.paper.CrazyCore;
import us.crazycrew.crazycore.paper.CrazyLogger;
import java.util.logging.LogManager;

public class ChatStarter implements PluginBootstrap {

    private CrazyCore crazyCore;
    private ConfigBuilder configBuilder;

    @Override
    public void bootstrap(@NotNull PluginProviderContext context) {
        this.crazyCore = new CrazyCore(context.getDataDirectory(), context.getConfiguration().getName());

        FilterMigration.copyFilterSettings(false);

        this.configBuilder = new ConfigBuilder(context.getDataDirectory());
        this.configBuilder.load();
    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        this.crazyCore.setPrefix(this.configBuilder.getPluginSettings().getProperty(PluginSettings.CONSOLE_PREFIX));

        CrazyLogger.setName(CrazyCore.getProjectPrefix());

        LogManager.getLogManager().addLogger(CrazyLogger.getLogger());

        return new ChatManager(this.crazyCore, this.configBuilder);
    }
}