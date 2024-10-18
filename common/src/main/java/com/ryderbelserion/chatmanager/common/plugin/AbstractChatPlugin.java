package com.ryderbelserion.chatmanager.common.plugin;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManagerProvider;
import com.ryderbelserion.chatmanager.api.ChatManager;
import com.ryderbelserion.chatmanager.common.plugin.logger.PluginLogger;

public abstract class AbstractChatPlugin implements ChatManager {

    public final void enable() {
        // register the api's singleton.
        ChatManagerProvider.register(this);
    }

    public abstract void onLoad();

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract PluginLogger getLogger();

    public abstract SettingsManager getConfig();

}