package com.ryderbelserion.chatmanager.paper.commands.features;

import com.ryderbelserion.chatmanager.core.enums.Messages;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

@Command("chatmanager")
public class ReloadFeature extends BaseFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("reload")
    @Permission(value = "chatmanager.reload", mode = Permission.Mode.ALL_OF)
    public void reload(final @NotNull CommandSourceStack source) {
        final CommandSender sender = source.getSender();

        this.paper.refresh();

        Messages.reload_plugin.sendMessage(sender);
    }
}