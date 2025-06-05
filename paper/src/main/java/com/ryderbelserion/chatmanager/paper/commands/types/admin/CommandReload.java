package com.ryderbelserion.chatmanager.paper.commands.types.admin;

import com.ryderbelserion.chatmanager.api.configs.ConfigManager;
import com.ryderbelserion.chatmanager.core.enums.Messages;
import com.ryderbelserion.chatmanager.paper.commands.types.AnnotationFeature;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

public class CommandReload extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager reload")
    @CommandDescription("Reloads the plugin!")
    @Permission(value = "chatmanager.reload", mode = Permission.Mode.ANY_OF)
    public void reload(final CommandSender sender) {
        this.fusion.reload(false);

        ConfigManager.reload();

        this.help.init();

        Messages.reload_plugin.sendMessage(sender);
    }
}