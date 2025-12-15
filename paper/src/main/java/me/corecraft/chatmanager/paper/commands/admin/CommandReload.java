package me.corecraft.chatmanager.paper.commands.admin;

import com.ryderbelserion.chatmanager.common.constants.Messages;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.corecraft.chatmanager.paper.commands.AnnotationFeature;
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
    @Permission(value = "chatmanager.reload", mode = Permission.Mode.ALL_OF)
    public void reload(final CommandSender sender) {
        this.platform.reload();

        this.registry.getMessage(Messages.reload_plugin).send(sender);
    }
}