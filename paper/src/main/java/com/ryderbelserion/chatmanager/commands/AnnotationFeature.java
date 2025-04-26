package com.ryderbelserion.chatmanager.commands;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.incendo.cloud.annotations.AnnotationParser;
import org.jetbrains.annotations.NotNull;

public interface AnnotationFeature {

    void registerFeature(@NotNull final ChatManager plugin, @NotNull final AnnotationParser<CommandSourceStack> parser);

}