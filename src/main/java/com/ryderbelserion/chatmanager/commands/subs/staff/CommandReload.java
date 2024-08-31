package com.ryderbelserion.chatmanager.commands.subs.staff;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.api.cache.objects.User;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.api.enums.other.Permissions;
import com.ryderbelserion.chatmanager.commands.AbstractCommand;
import com.ryderbelserion.chatmanager.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.managers.BroadcastManager;
import com.ryderbelserion.chatmanager.utils.LogUtils;
import com.ryderbelserion.chatmanager.utils.TaskUtils;
import com.ryderbelserion.vital.paper.api.commands.CommandData;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandReload extends AbstractCommand {

    @Override
    public void execute(final CommandData data) {
        this.plugin.getFileManager().reloadFiles();

        ConfigManager.refresh();

        LogUtils.zip();
        LogUtils.create();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);

        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            final User user = this.userManager.getUser(player);

            user.hideBossBar();

            //todo() remove cooldowns
            //this.plugin.api().getChatCooldowns().removeUser(player.getUniqueId());
            //this.plugin.api().getCooldownTask().removeUser(player.getUniqueId());
            //this.plugin.api().getCmdCooldowns().removeUser(player.getUniqueId());

            if (user.isStaffChat) {
                if (Permissions.COMMAND_STAFF.hasPermission(player)) {
                    user.showBossBar();
                } else {
                    user.isStaffChat = !user.isStaffChat;
                }
            }
        }

        BroadcastManager.start();

        TaskUtils.startMonitoringTask();

        Messages.plugin_reload.sendMessage(data.getCommandSender());
    }

    @Override
    public @NotNull final String getPermission() {
        return Permissions.reload_plugin.getNode();
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("reload").requires(source -> source.getSender().hasPermission(getPermission()));

        return root.executes(context -> {
            execute(new CommandData(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        }).build();
    }

    @Override
    public @NotNull final AbstractCommand registerPermission() {
        Permissions.reload_plugin.registerPermission();

        return this;
    }
}