package com.ryderbelserion.chatmanager.paper.commands.features.relations;

import com.ryderbelserion.chatmanager.core.enums.Messages;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.bukkit.message.BukkitMessageKey;
import dev.triumphteam.cmd.core.message.MessageKey;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ArgumentRelations {

    private final BukkitCommandManager<CommandSender> commandManager;

    public ArgumentRelations(@NotNull final BukkitCommandManager<CommandSender> commandManager) {
        this.commandManager = commandManager;
    }

    public void build() {
        this.commandManager.registerMessage(MessageKey.NOT_ENOUGH_ARGUMENTS, (sender, context) -> Messages.correct_usage.sendMessage(sender, "{usage}", context.getSyntax()));

        this.commandManager.registerMessage(MessageKey.TOO_MANY_ARGUMENTS, (sender, context) -> Messages.correct_usage.sendMessage(sender, "{usage}", context.getSyntax()));

        this.commandManager.registerMessage(MessageKey.INVALID_ARGUMENT, (sender, context) -> Messages.correct_usage.sendMessage(sender, "{usage}", context.getSyntax()));

        this.commandManager.registerMessage(BukkitMessageKey.NO_PERMISSION, (sender, context) -> Messages.no_permission.sendMessage(sender, "{permission}", context.getPermission().toString()));

        this.commandManager.registerMessage(BukkitMessageKey.UNKNOWN_COMMAND, (sender, context) -> Messages.unknown_command.sendMessage(sender, "{command}", context.getInvalidInput()));

        this.commandManager.registerMessage(BukkitMessageKey.CONSOLE_ONLY, (sender, context) -> Messages.must_be_console_sender.sendMessage(sender));

        this.commandManager.registerMessage(BukkitMessageKey.PLAYER_ONLY, (sender, context) -> Messages.must_be_a_player.sendMessage(sender));
    }
}