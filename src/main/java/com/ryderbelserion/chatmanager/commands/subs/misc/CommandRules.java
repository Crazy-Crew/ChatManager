package com.ryderbelserion.chatmanager.commands.subs.misc;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.api.AbstractCommand;
import com.ryderbelserion.chatmanager.configs.impl.types.RuleKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandRules extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        final int page = data.getIntegerArgument("page");

        final CommandSender sender = data.getCommandSender();

        final List<String> rules = this.rules.getProperty(RuleKeys.rules).getEntry().get(page);

        rules.forEach(line -> MsgUtils.sendMessage(sender, line, "{max}", String.valueOf(rules.size())));
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.rules.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("rules").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, Integer> arg1 = argument("page", IntegerArgumentType.integer()).suggests((ctx, builder) -> {
            this.rules.getProperty(RuleKeys.rules).getEntry().keySet().forEach(builder::suggest);

            return builder.buildFuture();
        }).executes(context -> {
            execute(new CommandData(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        });

        return root.then(arg1).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.rules.registerPermission();

        return this;
    }
}