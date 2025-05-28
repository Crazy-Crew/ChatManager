package com.ryderbelserion.chatmanager.paper.commands.features;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.core.configs.ConfigManager;
import com.ryderbelserion.chatmanager.paper.ChatManager;
import com.ryderbelserion.chatmanager.paper.api.PaperUserManager;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.fusion.paper.FusionPaper;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.plugin.java.JavaPlugin;

@Command("chatmanager")
public abstract class BaseCommand {

    protected final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    protected final FusionPaper fusion = this.plugin.getApi();

    protected final FileManager fileManager = this.plugin.getFileManager();

    protected final PaperUserManager userManager = this.plugin.getUserManager();

    protected final SettingsManager locale = ConfigManager.getLocale();

}