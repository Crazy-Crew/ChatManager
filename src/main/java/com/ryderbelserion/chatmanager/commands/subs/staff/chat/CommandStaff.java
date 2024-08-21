package com.ryderbelserion.chatmanager.commands.subs.staff.chat;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.vital.paper.api.commands.Command;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Server;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

public class CommandStaff extends Command {

    private final ChatManager plugin = ChatManager.get();
    private final Server server = this.plugin.getServer();

    @Override
    public void execute(final CommandData data) {
        //todo() send help menu
    }

    @Override
    public @NotNull final String getPermission() {
        return "chatmanager.staff";
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        return Commands.literal("staff")
                .requires(source -> source.getSender().hasPermission(getPermission()))
                .executes(context -> {
                    execute(new CommandData(context));

                    return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                })

                .then(new CommandStaffChat().registerPermission().literal())

                .build();
    }

    @Override
    public @NotNull final Command registerPermission() {
        final Permission permission = this.server.getPluginManager().getPermission(getPermission());

        if (permission == null) {
            this.server.getPluginManager().addPermission(new Permission(getPermission(), PermissionDefault.OP));
        }

        return this;
    }
}