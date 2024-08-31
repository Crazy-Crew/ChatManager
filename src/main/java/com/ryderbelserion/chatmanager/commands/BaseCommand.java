package com.ryderbelserion.chatmanager.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.managers.configs.impl.messages.MiscKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Map;

public class BaseCommand extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        final Map<String, List<String>> help = ConfigManager.getMessages().getProperty(MiscKeys.help).getEntry();

        final CommandSender sender = data.getCommandSender();

        help.get("1").forEach(line -> MsgUtils.sendMessage(sender, line, "{max}", String.valueOf(help.size())));
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.use.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("chatmanager").requires(source -> source.getSender().hasPermission(getPermission()));

        return root.executes(context -> {
            execute(new CommandData(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        }).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.use.registerPermission();

        return this;
    }
}