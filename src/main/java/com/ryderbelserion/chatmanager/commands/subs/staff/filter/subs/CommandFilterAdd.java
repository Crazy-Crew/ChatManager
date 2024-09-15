package com.ryderbelserion.chatmanager.commands.subs.staff.filter.subs;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.commands.AbstractCommand;
import com.ryderbelserion.chatmanager.api.enums.chat.FilterType;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.managers.configs.persist.blacklist.CommandsConfig;
import com.ryderbelserion.chatmanager.managers.configs.persist.blacklist.WordsConfig;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandFilterAdd extends AbstractCommand {

    @Override
    public void execute(final PaperCommandInfo info) {
        final CommandSender sender = info.getCommandSender();

        final FilterType type = FilterType.valueOf(info.getStringArgument("type"));
        final String value = info.getStringArgument("word");

        switch (type) {
            case banned_commands -> {
                final List<String> commands = CommandsConfig.banned_commands;

                if (commands.contains(value)) {
                    Messages.filter_value_exists.sendMessage(sender, new HashMap<>() {{
                        put("{value}", value);
                        put("{type}", type.getPrettyName());
                    }});

                    return;
                }

                commands.add(value);

                ConfigManager.getCommandsConfig().save();

                Messages.filter_value_added.sendMessage(sender, new HashMap<>() {{
                    put("{value}", value);
                    put("{type}", type.getPrettyName());
                }});
            }

            case banned_words -> {
                final List<String> words = WordsConfig.banned_words;

                if (words.contains(value)) {
                    Messages.filter_value_exists.sendMessage(sender, new HashMap<>() {{
                        put("{value}", value);
                        put("{type}", type.getPrettyName());
                    }});

                    return;
                }

                words.add(value);

                ConfigManager.getWordsConfig().save();

                Messages.filter_value_added.sendMessage(sender, new HashMap<>() {{
                    put("{value}", value);
                    put("{type}", type.getPrettyName());
                }});
            }

            case allowed_words -> {
                final List<String> words = WordsConfig.allowed_words;

                if (words.contains(value)) {
                    Messages.filter_value_exists.sendMessage(sender, new HashMap<>() {{
                        put("{value}", value);
                        put("{type}", type.getPrettyName());
                    }});

                    return;
                }

                words.add(value);

                ConfigManager.getWordsConfig().save();

                Messages.filter_value_added.sendMessage(sender, new HashMap<>() {{
                    put("{value}", value);
                    put("{type}", type.getPrettyName());
                }});
            }
        }
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.filter_add.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("add").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, String> arg1 = argument("type", StringArgumentType.string()).suggests((ctx, builder) -> {
            for (FilterType value : FilterType.values()) {
                builder.suggest(value.getName());
            }

            return builder.buildFuture();
        });

        final RequiredArgumentBuilder<CommandSourceStack, String> arg2 = argument("word", StringArgumentType.string()).suggests((ctx, builder) -> builder.suggest("<word>").buildFuture()).executes(context -> {
            execute(new PaperCommandInfo((context)));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        });

        return root.then(arg1.then(arg2)).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.filter_add.registerPermission();

        return this;
    }
}