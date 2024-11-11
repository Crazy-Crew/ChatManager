package com.ryderbelserion.chatmanager.loader;

import com.ryderbelserion.chatmanager.ChatManagerPaper;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManagerPlugin extends JavaPlugin {

    private final ChatManagerPaper chatManager;

    public ChatManagerPlugin() {
        this.chatManager = new ChatManagerPaper(this);
    }

    @Override
    public void onLoad() {
        this.chatManager.onLoad();
    }

    @Override
    public void onEnable() {
        this.chatManager.onEnable();
    }

    @Override
    public void onDisable() {
        this.chatManager.onDisable();
    }
}