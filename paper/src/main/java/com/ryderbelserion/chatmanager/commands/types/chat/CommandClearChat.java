package com.ryderbelserion.chatmanager.commands.types.chat;

import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.identity.Identity;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class CommandClearChat extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager clearchat")
    @CommandDescription("Allows the sender to clear chat!")
    @Permission(value = "chatmanager.clearchat", mode = Permission.Mode.ANY_OF)
    public void clearchat(final CommandSender sender) {
        final Collection<? extends Player> players = this.plugin.getServer().getOnlinePlayers();

        final Optional<UUID> uuid = sender.get(Identity.UUID);

        if (uuid.isEmpty()) {
            Messages.CLEAR_CHAT_STAFF_MESSAGE.sendMessage(sender, "{player}", sender.getName());
        }

        final FileConfiguration config = Files.CONFIG.getConfiguration();

        int lines = config.getInt("Clear_Chat.Broadcasted_Lines", 300);

        for (final Player player : players) {
            if (Permissions.BYPASS_CLEAR_CHAT.hasPermission(player)) {
                Messages.CLEAR_CHAT_STAFF_MESSAGE.sendMessage(player, "{player}", sender.getName());

                continue;
            }

            for (int i = 0; i < lines; i++) {
                sender.sendMessage("");
            }

            Messages.CLEAR_CHAT_BROADCAST_MESSAGE.sendMessage(player, "{player}", sender.getName());
        }
    }
}