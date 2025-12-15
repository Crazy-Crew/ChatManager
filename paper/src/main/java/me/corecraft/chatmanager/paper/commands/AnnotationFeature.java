package me.corecraft.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.api.interfaces.registry.IAnnotationFeature;
import com.ryderbelserion.chatmanager.common.registry.MessageRegistry;
import com.ryderbelserion.chatmanager.common.registry.UserRegistry;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.corecraft.chatmanager.paper.ChatManagerPlatform;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.incendo.cloud.annotations.AnnotationParser;

public abstract class AnnotationFeature implements IAnnotationFeature<AnnotationParser<CommandSourceStack>> {

    protected final ChatManager plugin = ChatManager.get();

    protected final ChatManagerPlatform platform = this.plugin.getPlatform();

    protected final MessageRegistry registry = this.platform.getMessageRegistry();

    protected final UserRegistry userRegistry = this.platform.getUserRegistry();

}