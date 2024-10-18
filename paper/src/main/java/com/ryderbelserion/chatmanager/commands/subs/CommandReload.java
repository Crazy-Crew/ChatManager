package com.ryderbelserion.chatmanager.commands.subs;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.chatmanager.ChatManagerPaper;
import com.ryderbelserion.chatmanager.common.enums.Messages;
import com.ryderbelserion.chatmanager.common.managers.configs.ConfigManager;
import com.ryderbelserion.chatmanager.loader.ChatManagerPlugin;
import com.ryderbelserion.vital.paper.commands.PaperCommand;
import com.ryderbelserion.vital.paper.commands.context.PaperCommandInfo;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class CommandReload extends PaperCommand {

    private final ConfigManager configManager;
    private final Server server;

    public CommandReload(final ChatManagerPaper chatManager) {
        this.configManager = chatManager.getConfigManager();

        final ChatManagerPlugin plugin = chatManager.getPlugin();

        this.server = plugin.getServer();
    }

    @Override
    public void execute(final PaperCommandInfo info) {
        final CommandSender sender = info.getCommandSender();

        this.configManager.reload();

        Messages.reload_plugin.sendMessage(sender);
    }

    @Override
    public @NotNull final String getPermission() {
        return "chatmanager.reload";
    }

    @Override
    public @NotNull final LiteralCommandNode<CommandSourceStack> literal() {
        final LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("reload").requires(source -> source.getSender().hasPermission(getPermission()));

        return root.executes(context -> {
            execute(new PaperCommandInfo(context));

            return com.mojang.brigadier.Command.SINGLE_SUCCESS;
        }).build();
    }

    @Override
    public @NotNull final PaperCommand registerPermission() {
        final PluginManager pluginManager = this.server.getPluginManager();

        final Permission permission = pluginManager.getPermission(getPermission());

        if (permission == null) {
            pluginManager.addPermission(new Permission(getPermission(), PermissionDefault.OP));
        }

        return this;
    }
}