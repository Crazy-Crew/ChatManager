package com.ryderbelserion.chatmanager.commands.subs.misc;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.commands.AbstractCommand;
import com.ryderbelserion.chatmanager.managers.configs.impl.types.ConfigKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandMotd extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        final CommandSender sender = data.getCommandSender();

        if (!this.config.getProperty(ConfigKeys.motd_enabled)) {
            Messages.feature_disabled.sendMessage(sender);

            return;
        }

        for (final String line : this.config.getProperty(ConfigKeys.motd_message)) {
            MsgUtils.sendMessage(sender, line, "{player}", sender.getName());
        }
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.motd.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("motd").requires(source -> source.getSender().hasPermission(getPermission()));

        return root.executes(context -> {
            execute(new CommandData(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        }).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.motd.registerPermission();

        return this;
    }
}