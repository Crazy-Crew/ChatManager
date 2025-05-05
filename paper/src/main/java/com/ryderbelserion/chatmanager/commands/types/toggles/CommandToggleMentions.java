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

public class CommandToggleMentions extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager togglementions")
    @CommandDescription("Allows the sender to toggle mentions!")
    @Permission(value = "chatmanager.togglementions", mode = Permission.Mode.ANY_OF)
    public void togglementions(final Player player, @Flag(value = "silent", aliases = {"s"}, permission = "togglementions.silent") boolean isSilent) {
        final PaperUser user = UserUtils.getUser(player);

        if (user.hasState(PlayerState.DIRECT_MESSAGES)) {
            user.removeState(PlayerState.DIRECT_MESSAGES);

            if (!isSilent) Messages.TOGGLE_MENTIONS_DISABLED.sendMessage(player);

            return;
        }

        user.addState(PlayerState.DIRECT_MESSAGES);

        if (!isSilent) Messages.TOGGLE_MENTIONS_ENABLED.sendMessage(player);
    }
}