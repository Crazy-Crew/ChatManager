package com.ryderbelserion.chatmanager.commands.types.admin;

import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.commands.BroadcastType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class CommandBroadcast extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager broadcast <type> <message>")
    @CommandDescription("Sends a message to the entire server!")
    @Permission(value = "chatmanager.broadcast", mode = Permission.Mode.ANY_OF)
    public void broadcast(final CommandSender sender, @Argument("type") final BroadcastType type, @Argument("message") final String message) {
        final FileConfiguration config = Files.CONFIG.getConfiguration();

        final String broadcastPath = "Broadcast_Commands.Command.Broadcast.sound";
        final String broadcastSound = config.getString(broadcastPath + ".value", "ENTITY_PLAYER_LEVELUP");
        final int broadcastVolume = config.getInt(broadcastPath + ".volume", 10);
        final int broadcastPitch = config.getInt(broadcastPath + ".pitch", 1);

        final String announcementPath = "Broadcast_Commands.Command.Announcement.sound";
        final String announcementSound = config.getString(announcementPath + ".value", "ENTITY_PLAYER_LEVELUP");
        final int announcementVolume = config.getInt(announcementPath + ".volume", 10);
        final int announcementPitch = config.getInt(announcementPath + ".pitch", 1);

        final String warningPath = "Broadcast_Commands.Command.Warning.sound";
        final String warningSound = config.getString(warningPath + ".value", "ENTITY_PLAYER_LEVELUP");
        final int warningVolume = config.getInt(warningPath + ".volume", 10);
        final int warningPitch = config.getInt(warningPath + ".pitch", 1);

        final List<String> announcement = config.getStringList("Broadcast_Commands.Command.Announcement.Message");
        final List<String> warning = config.getStringList("Broadcast_Commands.Command.Warning.Message");

        switch (type) {
            case ANNOUNCEMENT -> sendBroadcast(sender, message, announcementSound, announcementVolume, announcementPitch, announcement);

            case BROADCAST -> {
                for (final Player online : this.server.getOnlinePlayers()) {
                    Methods.sendMessage(online, message, true);

                    try {
                        online.playSound(online.getLocation(), Sound.valueOf(broadcastSound), broadcastVolume, broadcastPitch);
                    } catch (IllegalArgumentException ignored) {}
                }
            }

            case WARNING -> sendBroadcast(sender, message, warningSound, warningVolume, warningPitch, warning);
        }
    }

    private void sendBroadcast(@NotNull CommandSender sender, String msg, String sound, int volume, int pitch, List<String> warning) {
        for (final String announce : warning) {
            for (final Player online : this.server.getOnlinePlayers()) {
                if (sender instanceof Player player) {
                    online.sendMessage(Methods.placeholders(false, player, announce.replace("{player}", player.getName())).replace("{message}", msg).replace("\\n", "\n"));
                }

                try {
                    online.playSound(online.getLocation(), Sound.valueOf(sound), volume, pitch);
                } catch (IllegalArgumentException ignored) {}
            }

            if (sender instanceof ConsoleCommandSender)
                Methods.broadcast(null, announce.replace("{player}", sender.getName()).replace("{message}", msg).replace("\\n", "\n"));
        }
    }
}