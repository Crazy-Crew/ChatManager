package com.ryderbelserion.chatmanager.common.plugin;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManagerProvider;
import com.ryderbelserion.chatmanager.api.ChatManager;
import com.ryderbelserion.chatmanager.common.plugin.logger.PluginLogger;

public abstract class AbstractChatPlugin implements ChatManager {

    public final void enable() {
        registerListeners(); // register listeners

        ChatManagerProvider.register(this); // register the api's singleton
    }

    protected abstract void onLoad();

    protected abstract void onEnable();

    protected abstract void onDisable();

    protected abstract void registerPlatformAPI(final ChatManager api);

    protected abstract void registerListeners();

    protected abstract PluginLogger getLogger();

    protected abstract SettingsManager getConfig();

}