package com.ryderbelserion.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.api.interfaces.registry.IAnnotationFeature;
import com.ryderbelserion.chatmanager.common.registry.MessageRegistry;
import com.ryderbelserion.chatmanager.common.registry.UserRegistry;
import com.ryderbelserion.chatmanager.paper.ChatManagerPlatform;
import com.ryderbelserion.chatmanager.paper.ChatManagerPlugin;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.incendo.cloud.annotations.AnnotationParser;

public abstract class AnnotationFeature implements IAnnotationFeature<AnnotationParser<CommandSourceStack>> {

    protected final ChatManagerPlugin plugin = ChatManagerPlugin.getPlugin();

    protected final ChatManagerPlatform platform = this.plugin.getPlatform();

    protected final MessageRegistry registry = this.platform.getMessageRegistry();

    protected final UserRegistry userRegistry = this.platform.getUserRegistry();

}