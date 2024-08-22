package com.ryderbelserion.chatmanager.commands.subs;

import com.ryderbelserion.chatmanager.ChatManager;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.Server;

@Command(value = "chatmanager", alias = "cm")
public abstract class BaseCommand {

    protected final ChatManager plugin = ChatManager.get();
    protected final Server server = this.plugin.getServer();

}