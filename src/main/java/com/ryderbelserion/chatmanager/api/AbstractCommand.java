package com.ryderbelserion.chatmanager.api;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.vital.paper.api.commands.Command;
import org.bukkit.Server;

public abstract class AbstractCommand extends Command {

    protected final ChatManager plugin = ChatManager.get();
    protected final Server server = this.plugin.getServer();
    protected final UserManager userManager = this.plugin.getUserManager();

    protected final SettingsManager config = ConfigManager.getConfig();
    protected final SettingsManager messages = ConfigManager.getMessages();

}