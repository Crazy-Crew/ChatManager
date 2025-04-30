package com.ryderbelserion.chatmanager.commands.types.chat;

import com.ryderbelserion.chatmanager.api.objects.PaperServer;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.core.ServerState;
import io.papermc.paper.command.brigadier.CommandSourceStack;
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
    @CommandDescription("Allows the sender to mute chat!")
    @Permission(value = "chatmanager.mutechat", mode = Permission.Mode.ANY_OF)
    public void mutechat(final CommandSender sender, @Flag(value = "silent", aliases = {"s"}, permission = "mutechat.silent") boolean isSilent) {
        final PaperServer server = this.serverManager.getServer();

        if (server.hasState(ServerState.MUTED)) {
            server.removeState(ServerState.MUTED);

            if (!isSilent) {
                Messages.MUTE_CHAT_BROADCAST_MESSAGES_ENABLED.broadcast(sender, Permissions.BYPASS_MUTE_CHAT.getNode());
            }

            return;
        }

        server.addState(ServerState.MUTED);

        Messages.MUTE_CHAT_BROADCAST_MESSAGES_DISABLED.broadcast(sender, Permissions.BYPASS_MUTE_CHAT.getNode());
    }
}