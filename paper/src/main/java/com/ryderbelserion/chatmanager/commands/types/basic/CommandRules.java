package com.ryderbelserion.chatmanager.commands.types.basic;

import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.incendo.cloud.annotation.specifier.Range;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.paper.util.sender.Source;
import org.jetbrains.annotations.NotNull;

public class CommandRules implements AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final ChatManager plugin, @NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager rules <page>")
    @CommandDescription("Allows the player to view the server rules!")
    @Permission(value = "chatmanager.rules", mode = Permission.Mode.ANY_OF)
    public void rules(final CommandSender sender, @Argument("page") @Range(min = "1", max = "10") final int page) {
        final FileConfiguration config = Files.CONFIG.getConfiguration();

        for (final String rule : config.getStringList("Server_Rules.Rules." + page)) {
            Methods.sendMessage(sender, rule, true);
        }
    }
}