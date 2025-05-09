package com.ryderbelserion.chatmanager.commands.types.toggles;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

public class CommandToggleChat extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager togglechat")
    @CommandDescription("Allows the sender to toggle chat!")
    @Permission(value = "chatmanager.togglechat", mode = Permission.Mode.ANY_OF)
    public void togglechat(final Player player, @Flag(value = "silent", aliases = {"s"}, permission = "togglechat.silent") boolean isSilent) {
        final PaperUser user = UserUtils.getUser(player);

        if (user.hasState(PlayerState.CHAT)) {
            user.removeState(PlayerState.CHAT);

            if (!isSilent) Messages.TOGGLE_CHAT_ENABLED.sendMessage(player);

            return;
        }

        user.addState(PlayerState.CHAT);

        if (!isSilent) Messages.TOGGLE_CHAT_DISABLED.sendMessage(player);
    }
}