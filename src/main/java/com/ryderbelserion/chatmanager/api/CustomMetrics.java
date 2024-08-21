package com.ryderbelserion.chatmanager.api;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.vital.paper.api.bStats;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleyaml.configuration.file.YamlConfiguration;

public class CustomMetrics extends bStats {

    public CustomMetrics() {
        super(JavaPlugin.getPlugin(ChatManager.class), 3291);
    }

    public void start() {
        YamlConfiguration config = Files.CONFIG.getConfiguration();

        addCustomChart(new SimplePie("chat_format", () -> config.getString("Chat_Format.Enable")));

        addCustomChart(new SimplePie("chat_radius", () -> config.getString("Chat_Radius.Enable")));

        addCustomChart(new SimplePie("per_world_chat", () -> config.getString("Per_World_Chat.Enable")));
    }
}