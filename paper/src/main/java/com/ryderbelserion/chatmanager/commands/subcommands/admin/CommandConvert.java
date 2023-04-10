package com.ryderbelserion.chatmanager.commands.subcommands.admin;

import com.ryderbelserion.chatmanager.api.enums.ConvertOptions;
import com.ryderbelserion.chatmanager.configs.migrate.manuel.DefaultMigration;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.Optional;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import dev.triumphteam.cmd.core.annotation.Suggestion;
import me.h1dd3nxn1nja.chatmanager.ChatManager;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

@Command(value = "chatmanager")
public class CommandConvert extends BaseCommand {

    private final ChatManager plugin = ChatManager.getPlugin();

    private final DefaultMigration defaultMigration = new DefaultMigration();

    @SubCommand("convert")
    @Permission(value = "chatmanager.command.convert", def = PermissionDefault.OP)
    public void convert(CommandSender sender, @Suggestion("convert-options") ConvertOptions convertOptions, @Optional @Suggestion("booleans") boolean deleteFile) {
        if (!plugin.getDataFolder().exists()) return;

        switch (convertOptions.getName()) {
            case "rename_files" -> {
                ArrayList<String> files = new ArrayList<>();

                File logsFolder = null;

                for (File file : Objects.requireNonNull(plugin.getDataFolder().listFiles())) {
                    files.add(file.getPath());

                    if (file.isDirectory()) logsFolder = new File(file.getPath());
                }

                if (logsFolder != null) {
                    if (logsFolder.exists()) {
                        for (File file : Objects.requireNonNull(logsFolder.listFiles())) {
                            files.add(file.getPath());
                        }
                    }
                }

                files.forEach(file -> {
                    File oldFile = new File(file);
                    File newFile = new File(file.toLowerCase());

                    if (oldFile.getPath().equals(file)) {
                        sender.sendMessage("&cRenamed &e" + oldFile.getName() + "&c to &e" + newFile.getName());
                        oldFile.renameTo(newFile);
                    }
                });
            }

            case "convert_old_files" -> defaultMigration.convert(deleteFile);
        }
    }
}