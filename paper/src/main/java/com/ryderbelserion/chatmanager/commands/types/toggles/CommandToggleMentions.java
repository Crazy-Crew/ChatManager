package com.ryderbelserion.chatmanager.commands.types.toggles;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.cmds.ToggleMentionsData;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Messages;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Flag;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public class CommandToggleMentions extends AnnotationFeature {

    private final ApiLoader api = this.plugin.api();

    private final ToggleMentionsData data = this.api.getToggleMentionsData();

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager togglementions")
    @CommandDescription("Allows the sender to toggle mentions!")
    @Permission(value = "chatmanager.togglementions", mode = Permission.Mode.ANY_OF)
    public void togglementions(final Player player, @Flag(value = "silent", aliases = {"s"}, permission = "togglementions.silent") boolean isSilent) {
        final UUID uuid = player.getUniqueId();

        final boolean hasUser = this.data.containsUser(uuid);

        if (hasUser) {
            this.data.removeUser(uuid);

            if (!isSilent) {
                Messages.TOGGLE_MENTIONS_DISABLED.sendMessage(player);
            }

            return;
        }

        this.data.addUser(uuid);

        if (!isSilent) {
            Messages.TOGGLE_MENTIONS_ENABLED.sendMessage(player);
        }
    }
}