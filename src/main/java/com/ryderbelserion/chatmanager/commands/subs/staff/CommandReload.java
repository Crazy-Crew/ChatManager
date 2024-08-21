package com.ryderbelserion.chatmanager.commands.subs.staff;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.utils.TaskUtils;
import com.ryderbelserion.vital.paper.api.commands.Command;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Server;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

public class CommandReload extends Command {

    private final ChatManager plugin = ChatManager.get();
    private final Server server = this.plugin.getServer();

    @Override
    public void execute(final CommandData data) {
        this.plugin.getFileManager().reloadFiles(); // all static and dynamic files get reloaded here

        ConfigManager.refresh();

        // Cancel current events
        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);

        // Monitor staff changes
        TaskUtils.startMonitoringTask();

        Messages.PLUGIN_RELOAD.sendMessage(data.getCommandSender());
    }

    @Override
    public @NotNull final String getPermission() {
        return "chatmanager.reload";
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        return Commands.literal("reload")
                .requires(source -> source.getSender().hasPermission(getPermission()))
                .executes(context -> {
                    execute(new CommandData(context));

                    return com.mojang.brigadier.Command.SINGLE_SUCCESS;
                }).build();
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