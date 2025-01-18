package com.ryderbelserion.chatmanager.paper;

import org.bukkit.plugin.java.JavaPlugin;

public class ChatManagerPlugin extends JavaPlugin {

    public static ChatManagerPlugin getPlugin() {
        return JavaPlugin.getPlugin(ChatManagerPlugin.class);
    }

    private ChatManagerPaper paper;

    @Override
    public void onEnable() {
        this.paper = new ChatManagerPaper(this);
        this.paper.start();
    }

    @Override
    public void onDisable() {
        this.paper.stop();
    }

    public final ChatManagerPaper getPaper() {
        return this.paper;
    }
}