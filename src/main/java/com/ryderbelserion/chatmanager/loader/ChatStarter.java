package com.ryderbelserion.chatmanager.loader;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.ApiManager;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ChatStarter implements PluginBootstrap {

    private ApiManager apiManager;

    @Override
    public void bootstrap(@NotNull PluginProviderContext context) {
        apiManager = new ApiManager(context);
    }

    @Override
    public @NotNull JavaPlugin createPlugin(@NotNull PluginProviderContext context) {
        //this.crazyCore.setPrefix(this.configBuilder.getPluginSettings().getProperty(PluginSettings.CONSOLE_PREFIX));

        //CrazyLogger.setName(CrazyCore.getProjectPrefix());

        //LogManager.getLogManager().addLogger(CrazyLogger.getLogger());

        this.apiManager.load();

        return new ChatManager(this.apiManager);
    }
}