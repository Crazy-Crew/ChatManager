package com.ryderbelserion.chatmanager.commands.types.admin;

import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.h1dd3nxn1nja.chatmanager.Methods;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class CommandBannedCommands extends AnnotationFeature {

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager bannedcommands add <command>")
    @CommandDescription("Add a command to the bannedcommands.yml file.")
    @Permission(value = "chatmanager.bannedcommands.add", mode = Permission.Mode.ANY_OF)
    public void add(final CommandSender sender, @Argument("word") final String command) {
        final FileConfiguration commands = Files.BANNED_COMMANDS.getConfiguration();

        final List<String> list = commands.getStringList("Banned-Commands");

        if (!list.contains(command)) {
            Messages.BANNED_COMMANDS_EXISTS.sendMessage(sender, "{word}", command);

            return;
        }

        list.add(command.toLowerCase());

        commands.set("Banned-Commands", list);

        Files.BANNED_COMMANDS.save();
    }

    @Command("chatmanager bannedcommands list")
    @CommandDescription("Permission to view a list of all the banned commands.")
    @Permission(value = "chatmanager.bannedcommands.list", mode = Permission.Mode.ANY_OF)
    public void list(final CommandSender sender) {
        final FileConfiguration commands = Files.BANNED_COMMANDS.getConfiguration();

        final String list = commands.getStringList("Banned-Commands").toString().replace("[", "").replace("]", "");

        Methods.sendMessage(sender, "", true);
        Methods.sendMessage(sender, "&cCommands: &7" + list, true);

        sender.sendMessage(" ");
    }

    @Command("chatmanager bannedcommands remove <command>")
    @CommandDescription("Remove a command from the bannedcommands.yml file.")
    @Permission(value = "chatmanager.bannedcommands.remove", mode = Permission.Mode.ANY_OF)
    public void remove(final CommandSender sender, @Argument("command") final String command) {
        final FileConfiguration commands = Files.BANNED_COMMANDS.getConfiguration();

        final List<String> list = commands.getStringList("Banned-Commands");

        if (!list.contains(command)) {
            Messages.BANNED_COMMANDS_NOT_FOUND.sendMessage(sender, "{word}", command);

            return;
        }

        list.remove(command.toLowerCase());

        commands.set("Banned-Commands", list);

        Files.BANNED_COMMANDS.save();
    }
}