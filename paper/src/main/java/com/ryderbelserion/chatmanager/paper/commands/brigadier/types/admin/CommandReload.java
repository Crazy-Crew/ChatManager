package com.ryderbelserion.chatmanager.paper.commands.brigadier.types.admin;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.common.constants.MessageKeys;
import com.ryderbelserion.chatmanager.common.registry.MessageRegistry;
import com.ryderbelserion.chatmanager.paper.ChatManagerPlatform;
import com.ryderbelserion.fusion.paper.api.commands.objects.AbstractPaperCommand;
import com.ryderbelserion.fusion.paper.api.commands.objects.AbstractPaperContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class CommandReload extends AbstractPaperCommand {

    private final ChatManagerPlatform platform;
    private final MessageRegistry messageRegistry;

    public CommandReload(@NotNull final ChatManagerPlatform platform) {
        this.platform = platform;
        this.messageRegistry = platform.getMessageRegistry();
    }

    @Override
    public void execute(@NotNull final AbstractPaperContext context) {
        this.platform.reload();

        this.messageRegistry.getMessage(MessageKeys.reload_plugin).send(context.getCommandSender());
    }

    @Override
    public final boolean requirement(@NotNull final CommandSourceStack source) {
        return source.getSender().hasPermission(getPermissions().getFirst());
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> build() {
        return Commands.literal("reload")
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
        return List.of("chatmanager.reload");
    }
}