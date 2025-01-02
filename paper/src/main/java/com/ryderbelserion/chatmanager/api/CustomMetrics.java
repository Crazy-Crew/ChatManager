package com.ryderbelserion.chatmanager.api;

import com.ryderbelserion.chatmanager.enums.Files;
import me.clip.placeholderapi.metrics.bukkit.Metrics;
import me.clip.placeholderapi.metrics.charts.SimplePie;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;

public class CustomMetrics {

    private final ChatManager plugin = ChatManager.get();

    private final Metrics metrics;

    public CustomMetrics() {
        this.metrics = new Metrics(this.plugin, 3291);
    }

    public void start() {
        FileConfiguration config = Files.CONFIG.getConfiguration();

        addCustomChart(new SimplePie("chat_format", () -> config.getString("Chat_Format.Enable")));

        addCustomChart(new SimplePie("chat_radius", () -> config.getString("Chat_Radius.Enable")));

        addCustomChart(new SimplePie("per_world_chat", () -> config.getString("Per_World_Chat.Enable")));
    }

    public void addCustomChart(final SimplePie pie) {
        this.metrics.addCustomChart(pie);
    }

    public void close() {
        this.metrics.shutdown();
    }
}