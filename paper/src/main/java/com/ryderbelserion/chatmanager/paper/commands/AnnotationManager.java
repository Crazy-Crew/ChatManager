package com.ryderbelserion.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.ChatManagerPlugin;
import com.ryderbelserion.chatmanager.paper.commands.features.ReloadFeature;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.incendo.cloud.annotations.AnnotationParser;
import java.util.List;

public class AnnotationManager {

    private static final List<AnnotationFeature> features = List.of(
            new ReloadFeature()
    );

    private final AnnotationParser<CommandSourceStack> parser;

    public AnnotationManager(final ChatManagerPlugin plugin) {
        this.parser = new AnnotationParser<>(plugin.getPaper().getCommandManager(), CommandSourceStack.class);

        features.forEach(feature -> feature.registerFeature(this.parser));
    }
}