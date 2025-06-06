package com.ryderbelserion.chatmanager.paper.commands.brigadier.types.admin;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.configs.ConfigManager;
import com.ryderbelserion.chatmanager.core.enums.Messages;
import com.ryderbelserion.chatmanager.paper.ChatManager;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.fusion.paper.api.commands.objects.AbstractPaperCommand;
import com.ryderbelserion.fusion.paper.api.commands.objects.AbstractPaperContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class CommandReload extends AbstractPaperCommand {

    private final ChatManager plugin = ChatManager.get();

    private final FusionPaper fusion = this.plugin.getApi();

    @Override
    public void execute(@NotNull final AbstractPaperContext context) {
        this.fusion.reload(false);

        ConfigManager.reload();

        Messages.reload_plugin.sendMessage(context.getCommandSender());
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