package com.ryderbelserion.chatmanager.commands.types.chat;

import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

public class CommandMuteChat extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager mutechat")
    @CommandDescription("Allows the sender to mutechat!")
    @Permission(value = "chatmanager.mutechat", mode = Permission.Mode.ANY_OF)
    public void mutechat(final CommandSender sender, @Flag(value = "silent", aliases = {"s"}, permission = "mutechat.silent") boolean isSilent) {
        Methods.setMuted();

        final boolean isMuted = Methods.isMuted();

        if (!isSilent) {
            if (isMuted) {
                Messages.MUTE_CHAT_BROADCAST_MESSAGES_ENABLED.broadcast(sender);
            } else {
                Messages.MUTE_CHAT_BROADCAST_MESSAGES_DISABLED.broadcast(sender);
            }

            return;
        }

        if (isMuted) {
            Messages.MUTE_CHAT_BROADCAST_MESSAGES_ENABLED.broadcast(sender, Permissions.BYPASS_MUTE_CHAT.getNode());

            return;
        }

        Messages.MUTE_CHAT_BROADCAST_MESSAGES_DISABLED.broadcast(sender, Permissions.BYPASS_MUTE_CHAT.getNode());
    }
}