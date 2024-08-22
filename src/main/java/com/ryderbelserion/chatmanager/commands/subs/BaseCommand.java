package com.ryderbelserion.chatmanager.commands.subs;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.Server;

@Command(value = "chatmanager", alias = "cm")
public abstract class BaseCommand {

    protected final ChatManager plugin = ChatManager.get();
    protected final Server server = this.plugin.getServer();
    protected final UserManager userManager = this.plugin.getUserManager();

    protected final SettingsManager config = ConfigManager.getConfig();
    protected final SettingsManager messages = ConfigManager.getMessages();

}