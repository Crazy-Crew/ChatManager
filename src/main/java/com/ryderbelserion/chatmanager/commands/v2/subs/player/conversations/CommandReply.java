package com.ryderbelserion.chatmanager.commands.v2.subs.player.conversations;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.AbstractCommand;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.jetbrains.annotations.NotNull;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandReply extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        /*if (message.isEmpty()) {
            Messages.message_empty.sendMessage(sender);

            return;
        }

        final User receiver = this.userManager.getUser(sender);

        final String replyPlayer = receiver.replyPlayer;

        if (replyPlayer.isEmpty()) {
            // no one to reply to, so send message

            return;
        }

        final PlayerBuilder builder = new PlayerBuilder(replyPlayer);

        if (builder.getPlayer() == null || !builder.getPlayer().isOnline()) { //todo() add a mailbox feature, i.e. if you send a message to an offline player. it'll notify them on join
            Messages.player_not_found.sendMessage(sender, "{target}", builder.name());

            return;
        }

        final Player target = builder.getPlayer();

        MsgUtils.sendMessage(sender, target);*/
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.reply.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("reply").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, Integer> arg1 = argument("page", IntegerArgumentType.integer()).suggests((ctx, builder) -> {
            ConfigManager.getHelp().keySet().forEach(builder::suggest);

            return builder.buildFuture();
        }).executes(context -> {
            execute(new CommandData(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        });

        return root.then(arg1).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.reply.registerPermission();

        return this;
    }
}