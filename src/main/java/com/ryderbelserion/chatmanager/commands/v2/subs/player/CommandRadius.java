package com.ryderbelserion.chatmanager.commands.v2.subs.player;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.AbstractCommand;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.chat.ChatType;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.configs.impl.messages.commands.ChatKeys;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandRadius extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        if (!data.isPlayer()) {
            Messages.must_be_a_player.sendMessage(data.getCommandSender());

            return;
        }

        final Player player = data.getPlayer();
        final String value = data.getStringArgument("chat_type");

        if (value.equalsIgnoreCase("help")) {
            Messages.chatradius_help.sendMessage(player);

            return;
        }

        final ChatType type = ChatType.getChatType(value);

        final User user = this.userManager.getUser(player);

        if (user.chatType.getName().equalsIgnoreCase(type.getName())) {
            Messages.chatradius_already_enabled.sendMessage(player, new HashMap<>() {{
                put("{state}", type.getPrettyName());
                put("{status}", messages.getProperty(ChatKeys.chatradius_enabled));
            }});

            return;
        }

        user.chatType = type;

        Messages.chatradius_toggle.sendMessage(player, new HashMap<>() {{
            put("{state}", type.getPrettyName());
            put("{status}", messages.getProperty(ChatKeys.chatradius_enabled));
        }});
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.chat_radius.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("spy").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, String> arg1 = argument("chat_type", StringArgumentType.string()).suggests((ctx, builder) -> {
            for (ChatType value : ChatType.values()) {
                builder.suggest(value.getName());
            }

            return builder.buildFuture();
        }).executes(context -> {
            execute(new CommandData(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        });

        return root.then(arg1).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.chat_radius.registerPermission();

        return this;
    }
}