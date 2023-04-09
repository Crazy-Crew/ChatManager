package com.ryderbelserion.chatmanager.commands.subcommands.admin;

import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.permissions.PermissionDefault;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;

@Command(value = "chatmanager")
public class CommandConvert extends BaseCommand {

    private final ChatManager plugin = ChatManager.getPlugin();

    private final HashMap<String, File> oldFiles = new HashMap<>();

    @SubCommand("convert")
    @Permission(value = "chatmanager.command.convert", def = PermissionDefault.FALSE)
    public void convert() {
        if (!plugin.getDataFolder().exists()) return;

        for (File file : Objects.requireNonNull(plugin.getDataFolder().listFiles())) {
            if (!oldFiles.containsKey(file.getName())) oldFiles.put(file.getName(), file);

            if (file.isDirectory()) plugin.getLogger().warning(file.getName() + " is a directory.");
        }

        oldFiles.forEach((type, oldFile) -> {
            if (oldFile.isDirectory()) {
                File renamedDirectory = new File(plugin.getDataFolder() + "/" + type);

                plugin.getLogger().warning(renamedDirectory.getAbsolutePath());
                plugin.getLogger().warning(oldFile.getAbsolutePath());

                if (oldFile.renameTo(renamedDirectory)) plugin.getLogger().warning("Successfully renamed " + oldFile.getName() + " to " + renamedDirectory.getName());
            }
        });
    }
}