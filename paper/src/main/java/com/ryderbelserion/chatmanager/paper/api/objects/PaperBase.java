package com.ryderbelserion.chatmanager.paper.api.objects;

import com.ryderbelserion.chatmanager.paper.ChatManager;
import com.ryderbelserion.chatmanager.paper.api.PaperUserManager;
import com.ryderbelserion.fusion.paper.FusionPaper;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PaperBase {

    protected final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    protected final FusionPaper fusion = this.plugin.getApi();

    protected final PaperUserManager userManager = this.plugin.getUserManager();

}