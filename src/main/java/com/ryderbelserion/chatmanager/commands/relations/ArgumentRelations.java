package com.ryderbelserion.chatmanager.commands.relations;

import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.commands.MessageManager;
import dev.triumphteam.cmd.bukkit.message.BukkitMessageKey;
import dev.triumphteam.cmd.core.extention.meta.MetaKey;
import dev.triumphteam.cmd.core.message.MessageKey;
import java.util.Optional;

public class ArgumentRelations extends MessageManager {

    private String getContext(String command, String order) {
        if (command.isEmpty() || order.isEmpty()) return "";

        String usage = null;

        switch (command) {
            case "staffchat" -> usage = order + " [message]";
            case "rules" -> usage = order + " <page>";
        }

        return usage;
    }

    @Override
    public void build() {
        this.commandManager.registerMessage(BukkitMessageKey.UNKNOWN_COMMAND, (sender, context) -> Messages.unknown_command.sendMessage(sender, "{command}", context.getInvalidInput()));

        this.commandManager.registerMessage(MessageKey.TOO_MANY_ARGUMENTS, (sender, context) -> {
            Optional<String> meta = context.getMeta().get(MetaKey.NAME);

            meta.ifPresent(key -> {
                Messages.correct_usage.sendMessage(sender, "{usage}", getContext(key, "/chatmanager " + key));
            });
        });

        this.commandManager.registerMessage(MessageKey.NOT_ENOUGH_ARGUMENTS, (sender, context) -> {
            Optional<String> meta = context.getMeta().get(MetaKey.NAME);

            meta.ifPresent(key -> {
                Messages.correct_usage.sendMessage(sender, "{usage}", getContext(key, "/chatmanager " + key));
            });
        });

        this.commandManager.registerMessage(MessageKey.INVALID_ARGUMENT, (sender, context) -> Messages.correct_usage.sendMessage(sender, "{usage}", context.getSyntax()));

        this.commandManager.registerMessage(BukkitMessageKey.NO_PERMISSION, (sender, context) -> Messages.no_permission.sendMessage(sender, "{permission}", context.getPermission().toString()));

        this.commandManager.registerMessage(BukkitMessageKey.PLAYER_ONLY, (sender, context) -> Messages.must_be_a_player.sendMessage(sender));

        this.commandManager.registerMessage(BukkitMessageKey.CONSOLE_ONLY, (sender, context) -> Messages.must_be_console_sender.sendMessage(sender));
    }
}