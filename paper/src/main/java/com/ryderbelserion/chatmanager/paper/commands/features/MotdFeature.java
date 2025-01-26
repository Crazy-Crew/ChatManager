package com.ryderbelserion.chatmanager.paper.commands.features;

import com.ryderbelserion.chatmanager.core.enums.Messages;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

@Command("chatmanager")
public class MotdFeature extends BaseFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("motd")
    @Permission(value = "chatmanager.motd", mode = Permission.Mode.ALL_OF)
    public void motd(final @NotNull CommandSourceStack source) {
        final CommandSender sender = source.getSender();

        Messages.motd.sendMessage(sender);
    }
}