package com.ryderbelserion.chatmanager.paper;

import com.ryderbelserion.fusion.paper.FusionPaper;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ChatManagerPlugin extends JavaPlugin {

    public static ChatManagerPlugin get() {
        return JavaPlugin.getPlugin(ChatManagerPlugin.class);
    }

    private ChatManagerPlatform platform;

    @Override
    public void onEnable() {
        this.platform = new ChatManagerPlatform(this, new FusionPaper(getComponentLogger(), getDataPath()));
        this.platform.start();
    }

    @Override
    public void onDisable() {
        this.platform.stop();
    }

    public @NotNull final ChatManagerPlatform getPlatform() {
        return this.platform;
    }
}