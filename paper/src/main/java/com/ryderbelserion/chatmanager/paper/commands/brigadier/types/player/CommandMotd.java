package com.ryderbelserion.chatmanager.paper.commands.brigadier.types.player;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.common.constants.MessageKeys;
import com.ryderbelserion.chatmanager.common.enums.Files;
import com.ryderbelserion.chatmanager.common.registry.MessageRegistry;
import com.ryderbelserion.chatmanager.paper.ChatManagerPlatform;
import com.ryderbelserion.fusion.paper.api.commands.objects.AbstractPaperCommand;
import com.ryderbelserion.fusion.paper.api.commands.objects.AbstractPaperContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.util.HashMap;
import java.util.List;

public class CommandMotd extends AbstractPaperCommand {

    private final MessageRegistry messageRegistry;

    public CommandMotd(@NotNull final ChatManagerPlatform platform) {
        this.messageRegistry = platform.getMessageRegistry();
    }

    @Override
    public void execute(@NotNull final AbstractPaperContext context) {
        final CommandSender sender = context.getCommandSender();

        final CommentedConfigurationNode config = Files.config.getConfig();

        if (config.node("root", "motd", "toggle").getBoolean(false)) {
            this.messageRegistry.getMessage(MessageKeys.message_of_the_day).send(sender, new HashMap<>() {{
                put("{player}", sender.getName());
            }});

            return;
        }

        this.messageRegistry.getMessage(MessageKeys.feature_disabled).send(sender);
    }

    @Override
    public final boolean requirement(@NotNull final CommandSourceStack source) {
        return source.getSender().hasPermission(getPermissions().getFirst());
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("motd")
                .requires(this::requirement)
                .executes(context -> {
                    execute(new AbstractPaperContext(context));

                    return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                }).build();
    }

    @Override
    public @NotNull final PermissionDefault getPermissionMode() {
        return PermissionDefault.OP;
    }

    @Override
    public @NotNull final List<String> getPermissions() {
        return List.of("chatmanager.motd");
    }
}