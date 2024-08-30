package com.ryderbelserion.chatmanager.commands.v2.subs.staff.filter.subs;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.AbstractCommand;
import com.ryderbelserion.chatmanager.api.enums.chat.FilterType;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.CommandsConfig;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.WordsConfig;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;

public class CommandFilterAdd extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        final FilterType type = FilterType.valueOf(key);

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
        return Commands.literal("filter")
                .requires(source -> source.getSender().hasPermission(getPermission()))
                .executes(context -> {
                    execute(new CommandData(context));

                    return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                }).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.filter_add.registerPermission();

        return this;
    }
}