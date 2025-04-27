package com.ryderbelserion.chatmanager.commands.types.admin;

import com.ryderbelserion.chatmanager.ApiLoader;
import com.ryderbelserion.chatmanager.api.chat.StaffChatData;
import com.ryderbelserion.chatmanager.commands.AnnotationFeature;
import com.ryderbelserion.chatmanager.enums.Files;
import com.ryderbelserion.chatmanager.enums.Messages;
import com.ryderbelserion.chatmanager.enums.commands.FilterType;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandAntiSwear extends AnnotationFeature {

    private final ApiLoader api = this.plugin.api();

    private final StaffChatData data = this.api.getStaffChatData();

    @Override
    public void registerFeature(@NotNull final AnnotationParser<CommandSourceStack> parser) {
        parser.parse(this);
    }

    @Command("chatmanager antiswear add <type> <word>")
    @CommandDescription("Add a blacklisted or whitelisted swear word.")
    @Permission(value = "chatmanager.antiswear.add", mode = Permission.Mode.ANY_OF)
    public void add(final CommandSender sender, @Argument("type") final FilterType type, @Argument("word") final String word) {
        final FileConfiguration words = Files.BANNED_WORDS.getConfiguration();

        final List<String> list = type == FilterType.WHITELIST ? words.getStringList("Whitelisted_Words") : words.getStringList("Banned-Words");

        if (list.contains(word)) {
            Messages.ANTI_SWEAR_BLACKLISTED_WORD_EXISTS.sendMessage(sender, "{word}", word);

            return;
        }

        final String path = type == FilterType.WHITELIST ? "Whitelisted_Words" : "Banned-Words";

        list.add(word.toLowerCase());

        words.set(path, list);

        Files.BANNED_WORDS.save();
    }

    @Command("chatmanager antiswear list")
    @CommandDescription("Permission to view a list of all the banned words.")
    @Permission(value = "chatmanager.antiswear.list", mode = Permission.Mode.ANY_OF)
    public void list(final CommandSender sender) {
        final FileConfiguration words = Files.BANNED_WORDS.getConfiguration();

        final String list = words.getStringList("Banned-Words").toString().replace("[", "").replace("]", "");
        final String list2 = words.getStringList("Whitelisted_Words").toString().replace("[", "").replace("]", "");

        List.of(
                "",
                "&cSwear Words: &7" + list,
                "",
                "&cWhitelisted Words: &7" + list2
        ).forEach(msg -> Methods.sendMessage(sender, msg, false));
    }

    @Command("chatmanager antiswear remove <type> <word>")
    @CommandDescription("Remove a blacklisted or whitelisted swear word.")
    @Permission(value = "chatmanager.antiswear.remove", mode = Permission.Mode.ANY_OF)
    public void remove(final CommandSender sender, @Argument("type") final FilterType type, @Argument("word") final String word) {
        final FileConfiguration words = Files.BANNED_WORDS.getConfiguration();

        final List<String> list = type == FilterType.WHITELIST ? words.getStringList("Whitelisted_Words") : words.getStringList("Banned-Words");

        if (!list.contains(word)) {
            Messages.ANTI_SWEAR_BLACKLISTED_WORD_NOT_FOUND.sendMessage(sender, "{word}", word);

            return;
        }

        final String path = type == FilterType.WHITELIST ? "Whitelisted_Words" : "Banned-Words";

        list.remove(word.toLowerCase());

        words.set(path, list);

        Files.BANNED_WORDS.save();
    }
}