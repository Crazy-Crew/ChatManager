package com.ryderbelserion.chatmanager.commands;

import com.ryderbelserion.chatmanager.commands.types.admin.CommandDebug;
import com.ryderbelserion.chatmanager.commands.types.admin.CommandReload;
import com.ryderbelserion.chatmanager.commands.types.admin.CommandSpy;
import com.ryderbelserion.chatmanager.commands.types.basic.CommandHelp;
import com.ryderbelserion.chatmanager.commands.types.basic.CommandMotd;
import com.ryderbelserion.chatmanager.commands.types.basic.CommandRules;
import com.ryderbelserion.chatmanager.commands.types.chat.CommandClearChat;
import com.ryderbelserion.chatmanager.commands.types.chat.CommandMuteChat;
import com.ryderbelserion.chatmanager.commands.types.toggles.CommandPerWorldChat;
import com.ryderbelserion.chatmanager.commands.types.toggles.CommandToggleChat;
import com.ryderbelserion.chatmanager.commands.types.toggles.CommandToggleMentions;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.injection.ParameterInjectorRegistry;
import org.incendo.cloud.paper.PaperCommandManager;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.List;

public class BaseCommand {

    private static final List<AnnotationFeature> features = Arrays.asList(
            new CommandToggleMentions(),
            new CommandPerWorldChat(),
            new CommandToggleChat(),

            new CommandClearChat(),
            new CommandMuteChat(),
            new CommandSpy(),

            new CommandReload(),
            new CommandDebug(),

            new CommandRules(),
            new CommandMotd(),

            new CommandHelp()
    );

    private final AnnotationParser<CommandSourceStack> parser;

    public BaseCommand(@NotNull final PaperCommandManager<CommandSourceStack> manager) {
        final ParameterInjectorRegistry<CommandSourceStack> injector = manager.parameterInjectorRegistry();

        injector.registerInjector(CommandSender.class, (context, accessor) -> context.sender().getSender());
        injector.registerInjector(Player.class, (context, accessor) -> {
            final CommandSender sender = context.sender().getSender();

            if (sender instanceof Player player) {
                return player;
            }

            sender.sendRichMessage("<red>You must be a player to run this command!");

            return null;
        });

        this.parser = new AnnotationParser<>(manager, CommandSourceStack.class);

        register();
    }

    private void register() {
        features.forEach(feature -> feature.registerFeature(this.parser));
    }
}