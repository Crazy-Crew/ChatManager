package me.h1dd3nxn1nja.chatmanager.utils;

import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.SettingsManager;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

public class MetricsHandler {

    private final ChatManager plugin = ChatManager.getPlugin();

    private final SettingsManager settingsManager = plugin.getSettingsManager();

    public void start() {
        Metrics metrics = new Metrics(plugin, 3291);

        metrics.addCustomChart(new SimplePie("chat_format", () -> settingsManager.getConfig().getString("Chat_Format.Enable")));

        metrics.addCustomChart(new SimplePie("chat_radius", () -> settingsManager.getConfig().getString("Chat_Radius.Enable")));

        metrics.addCustomChart(new SimplePie("per_world_chat", () -> settingsManager.getConfig().getString("Per_World_Chat.Enable")));

        metrics.addCustomChart(new SimplePie("update_checker", () -> settingsManager.getConfig().getString("Update_Checker")));

        plugin.getLogger().info("Metrics has been enabled.");
    }
}