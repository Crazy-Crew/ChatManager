package com.ryderbelserion.chatmanager.commands;

import com.ryderbelserion.chatmanager.managers.ServerManager;
import com.ryderbelserion.chatmanager.managers.UserManager;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.Server;
import org.incendo.cloud.annotations.AnnotationParser;
import org.jetbrains.annotations.NotNull;

public abstract class AnnotationFeature {

    protected final ChatManager plugin = ChatManager.get();

    protected final UserManager userManager = this.plugin.getUserManager();

    protected final ServerManager serverManager = this.plugin.getServerManager();

    protected final Server server = this.plugin.getServer();

    public abstract void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser);

}