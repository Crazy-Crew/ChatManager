package com.ryderbelserion.chatmanager.api;

import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.vital.paper.bStats;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomMetrics extends bStats {

    public CustomMetrics() {
        super(JavaPlugin.getPlugin(ChatManager.class), 3291);
    }

    public void start() {
        FileConfiguration config = Files.CONFIG.getConfiguration();

        addCustomChart(new SimplePie("chat_format", () -> config.getString("Chat_Format.Enable")));

        addCustomChart(new SimplePie("chat_radius", () -> config.getString("Chat_Radius.Enable")));

        addCustomChart(new SimplePie("per_world_chat", () -> config.getString("Per_World_Chat.Enable")));
    }
}