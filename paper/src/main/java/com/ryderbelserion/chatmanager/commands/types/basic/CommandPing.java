package com.ryderbelserion.chatmanager.commands.types.basic;

import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Messages;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;

public class CommandPing extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager ping")
    @CommandDescription("Allows the player to view their ping or other players ping!")
    @Permission(value = "chatmanager.ping", mode = Permission.Mode.ANY_OF)
    public void ping(final CommandSender sender, @Flag(value = "p", permission = "chatmanager.ping.others", description = "View the ping of another player!") @Nullable final Player target) {
        if (target != null) {
            Messages.PING_TARGETS_PING.sendMessage(sender, new HashMap<>() {{
                put("{target}", target.getName());
                put("{ping}", String.valueOf(target.getPing()));
            }});

            return;
        }

        if (sender instanceof Player player) {
            Messages.PING_PLAYERS_PING.sendMessage(player, "{ping}", String.valueOf(player.getPing()));

            return;
        }

        Methods.sendMessage(sender, "&cError: You can only use that command in-game", true);
    }
}