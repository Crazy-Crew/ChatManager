package com.ryderbelserion.chatmanager.commands;

import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.enums.chat.SpyState;
import com.ryderbelserion.chatmanager.commands.relations.ArgumentRelations;
import com.ryderbelserion.chatmanager.commands.subs.misc.CommandMotd;
import com.ryderbelserion.chatmanager.commands.subs.misc.CommandRules;
import com.ryderbelserion.chatmanager.commands.subs.staff.CommandReload;
import com.ryderbelserion.chatmanager.commands.subs.staff.CommandSpy;
import com.ryderbelserion.chatmanager.commands.subs.staff.chat.CommandClearChat;
import com.ryderbelserion.chatmanager.commands.subs.staff.chat.CommandStaffChat;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
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

        commandManager.registerSuggestion(SuggestionKey.of("states"), (sender, context) -> Arrays.stream(SpyState.values()).map(SpyState::getSpyState).toList());

        // default
        commandManager.registerSuggestion(SuggestionKey.of("players"), (sender, context) -> plugin.getServer().getOnlinePlayers().stream().map(Player::getName).toList());

        commandManager.registerSuggestion(SuggestionKey.of("numbers"), (sender, context) -> {
            final List<String> numbers = new ArrayList<>();

            for (int i = 1; i <= 64; i++) numbers.add(String.valueOf(i));

            return numbers;
        });

        commandManager.registerArgument(PlayerBuilder.class, (sender, context) -> new PlayerBuilder(context));

        List.of(
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