package com.ryderbelserion.chatmanager.paper.commands;

import com.ryderbelserion.chatmanager.paper.ChatManager;
import com.ryderbelserion.chatmanager.paper.commands.features.base.CommandReload;
import com.ryderbelserion.chatmanager.paper.commands.features.relations.ArgumentRelations;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public class CommandHandler {

    private final ChatManager plugin = JavaPlugin.getPlugin(ChatManager.class);

    private final BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(plugin);

    public CommandHandler() {
        load();
    }

    public void load() {
        new ArgumentRelations(this.commandManager).build();

        List.of(
                new CommandReload()
        ).forEach(this.commandManager::registerCommand);
    }

    public final BukkitCommandManager<CommandSender> getCommandManager() {
        return this.commandManager;
    }
}