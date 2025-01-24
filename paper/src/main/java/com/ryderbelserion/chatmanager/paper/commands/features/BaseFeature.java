package com.ryderbelserion.chatmanager.paper.commands.features;

import com.ryderbelserion.chatmanager.paper.ChatManagerPaper;
import com.ryderbelserion.chatmanager.paper.ChatManagerPlugin;
import com.ryderbelserion.chatmanager.paper.commands.AnnotationFeature;
import org.incendo.cloud.annotations.Command;

@Command("chatmanager")
public abstract class BaseFeature implements AnnotationFeature {

    protected ChatManagerPlugin plugin = ChatManagerPlugin.getPlugin();

    protected ChatManagerPaper paper = this.plugin.getPaper();

}