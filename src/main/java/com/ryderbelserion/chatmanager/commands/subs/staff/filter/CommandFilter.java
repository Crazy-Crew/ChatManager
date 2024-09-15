package com.ryderbelserion.chatmanager.commands.subs.staff.filter;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.commands.AbstractCommand;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.commands.subs.staff.filter.subs.CommandFilterAdd;
import com.ryderbelserion.chatmanager.commands.subs.staff.filter.subs.CommandFilterHelp;
import com.ryderbelserion.chatmanager.commands.subs.staff.filter.subs.CommandFilterList;
import com.ryderbelserion.chatmanager.commands.subs.staff.filter.subs.CommandFilterRemove;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.jetbrains.annotations.NotNull;

public class CommandFilter extends AbstractCommand {

    @Override
    public void execute(final PaperCommandInfo info) {
        Messages.filter_command_help.sendMessage(info.getCommandSender());
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.filter_add.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("filter").requires(source -> source.getSender().hasPermission(getPermission()));

        return root.executes(context -> {
                    execute(new PaperCommandInfo((context)));

                    return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                })
                .then(new CommandFilterAdd().registerPermission().literal())
                .then(new CommandFilterHelp().registerPermission().literal())
                .then(new CommandFilterList().registerPermission().literal())
                .then(new CommandFilterRemove().registerPermission().literal())
                .build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.filter_add.registerPermission();

        return this;
    }
}