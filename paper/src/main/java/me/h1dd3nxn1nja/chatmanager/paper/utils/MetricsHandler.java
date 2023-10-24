package me.h1dd3nxn1nja.chatmanager.paper.utils;

import com.ryderbelserion.chatmanager.paper.files.Files;
import me.h1dd3nxn1nja.chatmanager.paper.ChatManager;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class MetricsHandler {

    @NotNull
    private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    public void start() {
        Metrics metrics = new Metrics(this.plugin, 3291);

        FileConfiguration config = Files.CONFIG.getFile();

        metrics.addCustomChart(new SimplePie("chat_format", () -> config.getString("Chat_Format.Enable")));

        metrics.addCustomChart(new SimplePie("chat_radius", () -> config.getString("Chat_Radius.Enable")));

        metrics.addCustomChart(new SimplePie("per_world_chat", () -> config.getString("Per_World_Chat.Enable")));

        metrics.addCustomChart(new SimplePie("update_checker", () -> config.getString("Update_Checker")));

        this.plugin.getLogger().info("Metrics has been enabled.");
    }
}