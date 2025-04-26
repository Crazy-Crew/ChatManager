package com.ryderbelserion.chatmanager.commands.types.basic;

import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

public class CommandMotd extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager motd")
    @CommandDescription("Shows the message of the day!")
    @Permission(value = "chatmanager.motd", mode = Permission.Mode.ANY_OF)
    public void motd(final CommandSender sender) {
        final FileConfiguration config = Files.CONFIG.getConfiguration();

        if (config.getBoolean("MOTD.Enable", false)) return;

        for (final String motd : config.getStringList("MOTD.Message")) {
            Methods.sendMessage(sender, motd, true);
        }
    }
}