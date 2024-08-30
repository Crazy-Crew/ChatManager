package com.ryderbelserion.chatmanager.commands.v2.subs.staff.filter.subs;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.AbstractCommand;
import com.ryderbelserion.chatmanager.api.enums.chat.FilterType;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.CommandsConfig;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.WordsConfig;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandFilterList extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        final CommandSender sender = data.getCommandSender();

        final FilterType type = FilterType.valueOf(data.getStringArgument("type"));

        switch (type) {
            case banned_commands -> {
                final List<String> commands = CommandsConfig.banned_commands;
            }

            case banned_words -> {
                final List<String> words = WordsConfig.banned_words;
            }

            case allowed_words -> {
                final List<String> words = WordsConfig.allowed_words;
            }
        }
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.filter_list.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("list").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, String> arg1 = argument("type", StringArgumentType.string()).suggests((ctx, builder) -> {
            for (FilterType value : FilterType.values()) {
                builder.suggest(value.getName());
            }

            return builder.buildFuture();
        }).executes(context -> {
            execute(new CommandData(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        });

        return root.then(arg1).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.filter_list.registerPermission();

        return this;
    }
}