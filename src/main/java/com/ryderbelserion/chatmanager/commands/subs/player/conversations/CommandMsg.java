package com.ryderbelserion.chatmanager.commands.subs.player.conversations;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.commands.AbstractCommand;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.managers.configs.impl.types.ConfigKeys;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import com.ryderbelserion.vital.paper.api.builders.PlayerBuilder;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandMsg extends AbstractCommand {

    @Override
    public void execute(final PaperCommandInfo info) {
        final CommandSender sender = info.getCommandSender();

        final String playerName = info.getStringArgument("player");

        final PlayerBuilder builder = new PlayerBuilder(playerName);

        if (builder.getPlayer() == null || !builder.getPlayer().isOnline()) { //todo() add a mailbox feature, i.e. if you send a message to an offline player. it'll notify them on join
            Messages.player_not_found.sendMessage(sender, "{target}", playerName);

            return;
        }

        final Player target = builder.getPlayer();

        MsgUtils.sendMessage(sender, target, info.getStringArgument("message"));
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.msg.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("msg").requires(source -> source.getSender().hasPermission(getPermission()));

        final String bypass_toggle_mentions = Permissions.bypass_toggle_mentions.getNode();
        final String bypass_ignored = Permissions.bypass_ignored.getNode();
        final String bypass_vanish = Permissions.bypass_vanish.getNode();

        final RequiredArgumentBuilder<CommandSourceStack, String> arg1 = argument("player", StringArgumentType.string()).suggests((ctx, builder) -> {
            final CommandSender sender = ctx.getSource().getSender();

            final boolean isValid = sender.hasPermission(bypass_vanish) || sender.hasPermission(bypass_ignored) || sender.hasPermission(bypass_toggle_mentions);

            for (final Player player : this.server.getOnlinePlayers()) {
                if (MsgUtils.isVanished(player)) continue;

                if (sender instanceof ConsoleCommandSender || isValid) builder.suggest(player.getName());
            }

            if (this.config.getProperty(ConfigKeys.private_message_load_offline_players)) {
                for (final OfflinePlayer offlinePlayer : this.server.getOfflinePlayers()) {
                    builder.suggest(offlinePlayer.getName());
                }
            }

            return builder.buildFuture();
        });

        final RequiredArgumentBuilder<CommandSourceStack, String> arg2 = argument("message", StringArgumentType.string()).suggests((ctx, builder) -> builder.suggest("<message>").buildFuture()).executes(context -> {
            execute(new PaperCommandInfo((context)));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        });

        return root.then(arg1.then(arg2)).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.msg.registerPermission();

        return this;
    }
}