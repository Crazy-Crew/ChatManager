package com.ryderbelserion.chatmanager.commands.subs.staff.chat;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.commands.AbstractCommand;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.managers.configs.impl.types.ConfigKeys;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandClearChat extends AbstractCommand {

    @Override
    public void execute(final PaperCommandInfo info) {
        final int amount = info.getIntegerArgument("amount");

        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            if (player.hasPermission(Permissions.BYPASS_CLEAR_CHAT.getNode())) return;

            for (int count = 0; count < amount; count++) {
                player.sendMessage("");
            }
        });

        final CommandSender sender = info.getCommandSender();

        Messages.clear_chat_broadcast_message.broadcast(new HashMap<>() {{
            put("{player}", sender.getName());
        }});

        if (sender instanceof ConsoleCommandSender) {
            Messages.clear_chat_staff_message.sendMessage(sender, "{player}", sender.getName());
        }
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.staff_clear.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("clearchat").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, Integer> arg1 = argument("amount", IntegerArgumentType.integer()).suggests((ctx, builder) -> suggestIntegers(builder, 1, this.config.getProperty(ConfigKeys.clear_chat_lines))).executes(context -> {
            execute(new PaperCommandInfo((context)));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        });

        return root.then(arg1).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.staff_clear.registerPermission();

        return this;
    }
}