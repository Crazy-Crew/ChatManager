package com.ryderbelserion.chatmanager.listeners.staff.logs;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.enums.Files;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.impl.types.ConfigKeys;
import com.ryderbelserion.chatmanager.utils.LogUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import java.io.File;
import java.util.Calendar;

public class HistoryListener implements Listener {

    private final SettingsManager config = ConfigManager.getConfig();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncChatEvent event) {
        if (!this.config.getProperty(ConfigKeys.log_chat)) return;

        LogUtils.write(Files.chat_log_file.getFile(), Calendar.getInstance().getTime(), event.getPlayer(), ": " + event.signedMessage().message());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onConsoleCommand(ServerCommandEvent event) {
        if (!this.config.getProperty(ConfigKeys.log_commands)) return;

        final String message = event.getCommand();

        if ((message.equals("/")) || (message.equals("//"))) return;

        LogUtils.write(Files.command_log_file.getFile(), Calendar.getInstance().getTime(), event.getSender(), ": " + message);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (!this.config.getProperty(ConfigKeys.log_commands)) return;

        final String message = event.getMessage();

        for (String command : this.config.getProperty(ConfigKeys.log_blacklist_commands)) {
            if (message.toLowerCase().startsWith(command)) return;
        }

        if ((message.equals("/")) || (message.equals("//"))) return;

        LogUtils.write(Files.command_log_file.getFile(), Calendar.getInstance().getTime(), event.getPlayer(), ": " + message);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSignChangeEvent(SignChangeEvent event) {
        if (!this.config.getProperty(ConfigKeys.log_signs)) return;

        final Location location = event.getBlock().getLocation();

        final File file = Files.sign_log_file.getFile();

        for (int line = 0; line < event.lines().size(); line++) {
            final Component component = event.line(line);

            if (component == null) continue;

            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();

            final String message = PlainTextComponentSerializer.plainText().serialize(component);

            LogUtils.write(file, Calendar.getInstance().getTime(), event.getPlayer(), " | Location: X: " + x + " Y: " + y + " Z: " + z + " | Line: " + line + " | " + message.replaceAll("§", "&"));
        }
    }
}