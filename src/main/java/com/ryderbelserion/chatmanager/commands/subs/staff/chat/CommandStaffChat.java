package com.ryderbelserion.chatmanager.commands.subs.staff.chat;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.commands.AbstractCommand;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.managers.configs.impl.types.ConfigKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandStaffChat extends AbstractCommand {

    @Override
    public void execute(final PaperCommandInfo info) {
        final CommandSender sender = info.getCommandSender();

        if (!this.config.getProperty(ConfigKeys.staff_chat_toggle)) {
            Messages.feature_disabled.sendMessage(sender);

            return;
        }

        final String message = info.getStringArgument("message");

        if (sender instanceof Player player) {
            final User user = this.userManager.getUser(player);

            if (!message.isEmpty()) {
                MsgUtils.send(player, message, false);

                return;
            }

            if (user.isStaffChat) {
                user.isStaffChat = false;

                if (this.config.getProperty(ConfigKeys.staff_bossbar_toggle)) {
                    user.hideBossBar();
                }

                Messages.staff_chat_disabled.sendMessage(player);

                return;
            }

            user.isStaffChat = true;

            if (this.config.getProperty(ConfigKeys.staff_bossbar_toggle)) {
                user.showBossBar();
            }

            Messages.staff_chat_enabled.sendMessage(player);

            return;
        }

        MsgUtils.send(sender, message, true);
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.staff_chat.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("staffchat").requires(source -> source.getSender().hasPermission(getPermission()))
                .executes(context -> {
                    execute(new PaperCommandInfo((context)));

                    return Command.SINGLE_SUCCESS;
                });

        final RequiredArgumentBuilder<CommandSourceStack, String> arg1 = argument("message", StringArgumentType.string()).suggests((ctx, builder) -> builder.buildFuture()).executes(context -> {
            execute(new PaperCommandInfo((context)));

            return Command.SINGLE_SUCCESS;
        });

        return root.then(arg1).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.staff_chat.registerPermission();

        return this;
    }
}