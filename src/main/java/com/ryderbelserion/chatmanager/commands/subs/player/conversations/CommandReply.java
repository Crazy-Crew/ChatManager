package com.ryderbelserion.chatmanager.commands.subs.player.conversations;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.AbstractCommand;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.configs.impl.types.ConfigKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import com.ryderbelserion.vital.paper.api.builders.PlayerBuilder;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandReply extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        final CommandSender sender = data.getCommandSender();

        final User user = this.userManager.getUser(sender);

        final String lastPlayer = user.replyPlayer;

        if (lastPlayer.isEmpty()) {
            //todo() send message saying no one to reply to.

            return;
        }

        final String playerName = data.getStringArgument("player");

        final PlayerBuilder builder = new PlayerBuilder(playerName);

        if (builder.getPlayer() == null || !builder.getPlayer().isOnline()) { //todo() add a mailbox feature, i.e. if you send a message to an offline player. it'll notify them on join
            Messages.player_not_found.sendMessage(sender, "{target}", playerName);

            return;
        }

        MsgUtils.sendMessage(sender, builder.getPlayer(), data.getStringArgument("message"));
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.reply.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("reply").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, String> arg1 = argument("player", StringArgumentType.string()).suggests((ctx, builder) -> {
            for (final Player player : this.server.getOnlinePlayers()) {
                builder.suggest(player.getName());
            }

            if (this.config.getProperty(ConfigKeys.private_message_load_offline_players)) {
                for (final OfflinePlayer offlinePlayer : this.server.getOfflinePlayers()) {
                    builder.suggest(offlinePlayer.getName());
                }
            }

            return builder.buildFuture();
        });

        final RequiredArgumentBuilder<CommandSourceStack, String> arg2 = argument("message", StringArgumentType.string()).suggests((ctx, builder) -> builder.suggest("<message>").buildFuture()).executes(context -> {
            execute(new CommandData(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        });

        return root.then(arg1.then(arg2)).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.reply.registerPermission();

        return this;
    }
}