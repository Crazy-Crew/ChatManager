package com.ryderbelserion.chatmanager.paper.commands.brigadier.types.player;

import ch.jalu.configme.SettingsManager;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.configs.ConfigManager;
import com.ryderbelserion.chatmanager.api.configs.types.ConfigKeys;
import com.ryderbelserion.chatmanager.core.enums.Messages;
import com.ryderbelserion.fusion.paper.api.commands.objects.AbstractPaperCommand;
import com.ryderbelserion.fusion.paper.api.commands.objects.AbstractPaperContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;

public class CommandMotd extends AbstractPaperCommand {

    private final SettingsManager config = ConfigManager.getConfig();

    @Override
    public void execute(@NotNull final AbstractPaperContext context) {
        final CommandSender sender = context.getCommandSender();

        if (this.config.getProperty(ConfigKeys.motd_enabled)) {
            Messages.motd.sendMessage(sender, new HashMap<>() {{
                put("{player}", sender.getName());
            }});
        }
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