package com.ryderbelserion.chatmanager.paper.commands;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.incendo.cloud.annotations.AnnotationParser;
import org.jetbrains.annotations.NotNull;

public interface AnnotationFeature {

    void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser);

}