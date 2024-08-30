package com.ryderbelserion.chatmanager.commands.v2.subs.staff.filter.subs;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.AbstractCommand;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.jetbrains.annotations.NotNull;

public class CommandFilterHelp extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        Messages.filter_command_help.sendMessage(data.getCommandSender());
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.filter_help.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("help").requires(source -> source.getSender().hasPermission(getPermission()));

        return root.executes(context -> {
            execute(new CommandData(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        }).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.filter_help.registerPermission();

        return this;
    }
}