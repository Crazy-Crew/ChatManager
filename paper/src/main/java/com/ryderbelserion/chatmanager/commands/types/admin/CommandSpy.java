package com.ryderbelserion.chatmanager.commands.types.admin;

import com.ryderbelserion.chatmanager.api.objects.PaperUser;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.Permissions;
import com.ryderbelserion.chatmanager.enums.core.PlayerState;
import com.ryderbelserion.chatmanager.utils.UserUtils;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.jetbrains.annotations.NotNull;
import java.util.stream.Stream;

public class CommandSpy extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command(value = "chatmanager spy <type>", requiredSender = Player.class)
    @CommandDescription("Access the ability to spy on commands/messages!")
    @Permission(value = "chatmanager.spy", mode = Permission.Mode.ANY_OF)
    public void spy(final Player player, @Argument(value = "type", suggestions = "spy-suggestions") @NotNull final PlayerState type) {
        final Permissions node = type == PlayerState.COMMAND_SPY ? Permissions.COMMAND_SPY : Permissions.SOCIAL_SPY;

        if (!node.hasPermission(player)) return; // no permission

        final PaperUser user = UserUtils.getUser(player);

        if (user.hasState(type)) {
            user.removeState(type);

            Messages.SPY_DISABLED.sendMessage(player, "{spy-type}", type.getName());

            return;
        }

        user.addState(type);

        Messages.SPY_ENABLED.sendMessage(player, "{spy-type}", type.getName());
    }

    @Suggestions("spy-suggestions")
    public @NotNull Stream<@NotNull PlayerState> suggestions() {
        return Stream.of(PlayerState.COMMAND_SPY, PlayerState.SOCIAL_SPY);
    }
}