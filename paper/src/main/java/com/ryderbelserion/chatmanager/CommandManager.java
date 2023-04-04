package com.ryderbelserion.chatmanager;

import com.ryderbelserion.chatmanager.commands.ChatBaseCommand;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.command.CommandSender;

public class CommandManager {

    private final ChatManager plugin = ChatManager.getPlugin();

    private final BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(plugin);

    public void setup() {
        commandManager.registerCommand(new ChatBaseCommand());
    }
}