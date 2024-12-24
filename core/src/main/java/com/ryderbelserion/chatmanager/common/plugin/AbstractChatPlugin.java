package com.ryderbelserion.chatmanager.common.plugin;

import com.ryderbelserion.FusionProvider;
import com.ryderbelserion.chatmanager.ChatManagerProvider;
import com.ryderbelserion.chatmanager.api.ChatManager;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public abstract class AbstractChatPlugin implements ChatManager {

    public final void enable() {
        registerCommands(); // register commands

        registerListeners(); // register listeners

        ChatManagerProvider.register(this); // register the api's singleton

        registerPlatformAPI(this); // register platform api
    }

    protected abstract void onLoad();

    protected abstract void onEnable();

    protected abstract void onDisable();

    protected abstract void registerPlatformAPI(final ChatManager api);

    protected abstract void registerListeners();

    protected abstract void registerCommands();

    protected ComponentLogger getLogger() {
        return FusionProvider.get().getLogger();
    }
}