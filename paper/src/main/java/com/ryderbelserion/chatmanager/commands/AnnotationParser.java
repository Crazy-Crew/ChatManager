package com.ryderbelserion.chatmanager.commands;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.plugin.Plugin;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

public class AnnotationParser {

    private final PaperCommandManager<CommandSourceStack> manager;
    private final org.incendo.cloud.annotations.AnnotationParser<CommandSourceStack> parser;

    public AnnotationParser(final Plugin plugin) {
        this.manager = PaperCommandManager.builder()
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(plugin);

        this.parser = new org.incendo.cloud.annotations.AnnotationParser<>(this.manager, CommandSourceStack.class);
    }

    public void enable() {

    }
}