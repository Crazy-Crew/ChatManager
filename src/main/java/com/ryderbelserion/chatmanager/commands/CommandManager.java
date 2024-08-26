package com.ryderbelserion.chatmanager.commands;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.chat.ChatType;
import com.ryderbelserion.chatmanager.api.enums.chat.FilterType;
import com.ryderbelserion.chatmanager.api.enums.chat.SpyType;
import com.ryderbelserion.chatmanager.api.enums.chat.ToggleType;
import com.ryderbelserion.chatmanager.commands.relations.ArgumentRelations;
import com.ryderbelserion.chatmanager.commands.subs.BaseCommand;
import com.ryderbelserion.chatmanager.commands.subs.misc.CommandMotd;
import com.ryderbelserion.chatmanager.commands.subs.misc.CommandRules;
import com.ryderbelserion.chatmanager.commands.subs.player.CommandHelp;
import com.ryderbelserion.chatmanager.commands.subs.player.CommandRadius;
import com.ryderbelserion.chatmanager.commands.subs.player.CommandToggle;
import com.ryderbelserion.chatmanager.commands.subs.player.conversations.CommandMsg;
import com.ryderbelserion.chatmanager.commands.subs.player.conversations.CommandReply;
import com.ryderbelserion.chatmanager.commands.subs.staff.CommandReload;
import com.ryderbelserion.chatmanager.commands.subs.staff.CommandSpy;
import com.ryderbelserion.chatmanager.commands.subs.staff.chat.CommandClearChat;
import com.ryderbelserion.chatmanager.commands.subs.staff.chat.CommandStaffChat;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.CommandsConfig;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.WordsConfig;
import com.ryderbelserion.vital.paper.api.builders.PlayerBuilder;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.core.suggestion.SuggestionKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    private final static @NotNull ChatManager plugin = ChatManager.get();

    private final static @NotNull BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(plugin);

    /**
     * Loads commands.
     */
    public static void load() {
        new ArgumentRelations().build();

        // plugin specific
        commandManager.registerSuggestion(SuggestionKey.of("pages"), (sender, context) -> {
            final List<String> numbers = new ArrayList<>();

            ConfigManager.getRules().keySet().forEach(value -> numbers.add(String.valueOf(value)));

            return numbers;
        });

        commandManager.registerSuggestion(SuggestionKey.of("help_pages"), (sender, context) -> {
            final List<String> numbers = new ArrayList<>();

            ConfigManager.getHelp().keySet().forEach(value -> numbers.add(String.valueOf(value)));

            return numbers;
        });

        commandManager.registerSuggestion(SuggestionKey.of("blacklisted_commands"), (sender, context) -> CommandsConfig.banned_commands);
        commandManager.registerSuggestion(SuggestionKey.of("blacklisted_words"), (sender, context) -> WordsConfig.banned_words);
        commandManager.registerSuggestion(SuggestionKey.of("whitelisted_words"), (sender, context) -> WordsConfig.allowed_words);

        commandManager.registerSuggestion(SuggestionKey.of("spy_type"), (sender, context) -> Arrays.stream(SpyType.values()).map(SpyType::getName).toList());

        commandManager.registerSuggestion(SuggestionKey.of("chat_type"), (sender, context) -> Arrays.stream(ChatType.values()).map(ChatType::getName).toList());

        commandManager.registerSuggestion(SuggestionKey.of("toggle_type"), (sender, context) -> Arrays.stream(ToggleType.values()).map(ToggleType::getName).toList());

        commandManager.registerSuggestion(SuggestionKey.of("filter_type"), (sender, context) -> Arrays.stream(FilterType.values()).map(FilterType::getName).toList());

        // default
        commandManager.registerSuggestion(SuggestionKey.of("players"), (sender, context) -> plugin.getServer().getOnlinePlayers().stream().map(Player::getName).toList());

        commandManager.registerSuggestion(SuggestionKey.of("numbers"), (sender, context) -> {
            final List<String> numbers = new ArrayList<>();

            for (int i = 1; i <= 64; i++) numbers.add(String.valueOf(i));

            return numbers;
        });

        // default
        commandManager.registerArgument(PlayerBuilder.class, (sender, context) -> new PlayerBuilder(context));

        List.of(
                new BaseCommand(),

                new CommandReply(),
                new CommandMsg(),

                new CommandRadius(),
                new CommandToggle(),

                new CommandHelp(),
                new CommandRules(),
                new CommandMotd(),

                new CommandStaffChat(),
                new CommandClearChat(),
                new CommandReload(),

                new CommandSpy()
        ).forEach(commandManager::registerCommand);
    }

    public static @NotNull BukkitCommandManager<CommandSender> getCommandManager() {
        return commandManager;
    }
}