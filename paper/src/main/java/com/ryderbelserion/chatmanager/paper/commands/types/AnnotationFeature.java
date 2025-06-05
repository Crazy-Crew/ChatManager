package com.ryderbelserion.chatmanager.paper.commands.types;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.api.configs.ConfigManager;
import com.ryderbelserion.chatmanager.paper.ChatManager;
import com.ryderbelserion.chatmanager.paper.api.PaperUserManager;
import com.ryderbelserion.chatmanager.paper.api.objects.PaperHelp;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.fusion.paper.FusionPaper;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.incendo.cloud.annotations.AnnotationParser;
import org.jetbrains.annotations.NotNull;

public abstract class AnnotationFeature {

    protected final ChatManager plugin = ChatManager.get();

    protected final FusionPaper fusion = this.plugin.getApi();

    protected final FileManager fileManager = this.plugin.getFileManager();

    protected final PaperUserManager userManager = this.plugin.getUserManager();

    protected final SettingsManager locale = ConfigManager.getLocale();

    protected final PaperHelp help = this.plugin.getHelp();

    public abstract void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser);

}