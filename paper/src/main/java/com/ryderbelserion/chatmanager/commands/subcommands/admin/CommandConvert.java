package com.ryderbelserion.chatmanager.commands.subcommands.admin;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import dev.triumphteam.cmd.core.annotation.Suggestion;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;

@Command(value = "chatmanager")
public class CommandConvert extends BaseCommand {

    private final ChatManager plugin = ChatManager.getPlugin();

    @SubCommand("convert")
    @Permission(value = "chatmanager.command.convert", def = PermissionDefault.OP)
    public void convert(CommandSender sender, @Suggestion("file-convert") String fileName) {
        if (!plugin.getDataFolder().exists()) return;

        File file = new File(fileName);
        File newFile = new File(fileName.toLowerCase());

        if (file.getPath().equals(fileName)) file.renameTo(newFile);
    }
}