package com.ryderbelserion.chatmanager.paper.api.objects;

import com.ryderbelserion.chatmanager.paper.ChatManagerPaper;
import com.ryderbelserion.chatmanager.paper.ChatManagerPlugin;
import com.ryderbelserion.chatmanager.paper.api.PaperUserManager;

public abstract class ChatManagerBase {

    protected final ChatManagerPlugin plugin = ChatManagerPlugin.getPlugin();

    protected final ChatManagerPaper paper = this.plugin.getPaper();

    protected final PaperUserManager userManager = this.paper.getUserManager();

}