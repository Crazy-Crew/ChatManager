package com.ryderbelserion.chatmanager.commands.types.basic;

import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.annotation.specifier.Greedy;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.help.result.CommandEntry;
import org.incendo.cloud.minecraft.extras.MinecraftHelp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class CommandHelp extends AnnotationFeature {

    private MinecraftHelp<CommandSourceStack> help;
    private CommandManager<CommandSourceStack> manager;

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        this.manager = parser.manager();

        this.help = MinecraftHelp.create(
                "/chatmanager help",
                this.manager,
                CommandSourceStack::getSender
        );

        parser.parse(this);
    }

    @Suggestions("help_queries")
    public @NotNull List<@NotNull String> suggestHelpQueries(
            @NotNull final CommandContext<CommandSourceStack> context,
            @NotNull final String input
    ) {
        return this.manager.createHelpHandler()
                .queryRootIndex(context.sender())
                .entries()
                .stream()
                .map(CommandEntry::syntax)
                .toList();
    }

    @Command("chatmanager help <query>")
    @CommandDescription("Shows the player the help menu!")
    public void help(@NotNull final CommandSourceStack sender, final @Argument(value = "query", suggestions = "help_queries") @Greedy @Nullable String query) {
        this.help.queryCommands(query == null ? "" : query, sender);
    }
}