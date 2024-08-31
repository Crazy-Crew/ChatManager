package com.ryderbelserion.chatmanager.commands.subs.staff;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.commands.AbstractCommand;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.chat.ToggleType;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.managers.configs.impl.messages.commands.ToggleKeys;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import static io.papermc.paper.command.brigadier.Commands.argument;

public class CommandToggle extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        if (!data.isPlayer()) {
            Messages.must_be_a_player.sendMessage(data.getCommandSender());

            return;
        }

        final Player player = data.getPlayer();
        final String value = data.getStringArgument("toggle_type");

        final ToggleType type = ToggleType.getToggleType(value);

        final User user = this.userManager.getUser(player);

        if (user.activeChatTypes.contains(type.getName())) {
            user.activeChatTypes.remove(type.getName());

            Messages.chat_toggle.sendMessage(player, new HashMap<>() {{
                put("{state}", type.getPrettyName());
                put("{status}", messages.getProperty(ToggleKeys.toggle_disabled));
            }});

            return;
        }

        user.activeChatTypes.add(type.getName());

        Messages.chat_toggle.sendMessage(player, new HashMap<>() {{
            put("{state}", type.getPrettyName());
            put("{status}", messages.getProperty(ToggleKeys.toggle_enabled));
        }});
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.toggle.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("toggle").requires(source -> source.getSender().hasPermission(getPermission()));

        final RequiredArgumentBuilder<CommandSourceStack, String> arg1 = argument("toggle_type", StringArgumentType.string()).suggests((ctx, builder) -> {
            for (ToggleType value : ToggleType.values()) {
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
        Permissions.toggle.registerPermission();

        return this;
    }
}