package com.ryderbelserion.chatmanager.commands.subs;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.chatmanager.ChatManager;
import com.ryderbelserion.chatmanager.api.cache.UserManager;
import com.ryderbelserion.chatmanager.api.enums.chat.FilterType;
import com.ryderbelserion.chatmanager.api.enums.other.Messages;
import com.ryderbelserion.chatmanager.configs.ConfigManager;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.CommandsConfig;
import com.ryderbelserion.chatmanager.configs.persist.blacklist.WordsConfig;
import com.ryderbelserion.chatmanager.utils.MsgUtils;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Suggestion;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Command(value = "chatmanager", alias = "cm")
public class BaseCommand {

    protected final ChatManager plugin = ChatManager.get();
    protected final Server server = this.plugin.getServer();
    protected final UserManager userManager = this.plugin.getUserManager();

    protected final SettingsManager config = ConfigManager.getConfig();
    protected final SettingsManager messages = ConfigManager.getMessages();

    @Command
    @Permission(value = "chatmanager.use", def = PermissionDefault.TRUE, description = "Access to /chatmanager")
    public void root(final CommandSender sender) {
        final Map<Integer, List<String>> help = ConfigManager.getHelp();

        help.get(1).forEach(line -> MsgUtils.sendMessage(sender, line, "{max}", String.valueOf(help.size())));
    }

    @Command("filter")
    @Permission(value = "chatmanager.filter.use", def = PermissionDefault.OP, description = "Access to /chatmanager filter")
    public static class CommandFilter {

        @Command("add")
        @Permission(value = "add", def = PermissionDefault.OP, description = "Access to /chatmanager filter add <type> <word>")
        public void add(final CommandSender sender, @Suggestion("filter_type") final String key, final String value) {
            final FilterType type = FilterType.valueOf(key);

            switch (type) {
                case banned_commands -> {
                    final List<String> commands = CommandsConfig.banned_commands;

                    if (commands.contains(value)) {
                        Messages.filter_value_exists.sendMessage(sender, new HashMap<>() {{
                            put("{value}", value);
                            put("{type}", type.getPrettyName());
                        }});

                        return;
                    }

                    commands.add(value);

                    ConfigManager.getCommandsConfig().save();
                }

                case banned_words -> {
                    final List<String> words = WordsConfig.banned_words;

                    if (words.contains(value)) {
                        Messages.filter_value_exists.sendMessage(sender, new HashMap<>() {{
                            put("{value}", value);
                            put("{type}", type.getPrettyName());
                        }});

                        return;
                    }

                    words.add(value);

                    ConfigManager.getWordsConfig().save();
                }

                case allowed_words -> {
                    final List<String> words = WordsConfig.allowed_words;

                    if (words.contains(value)) {
                        Messages.filter_value_exists.sendMessage(sender, new HashMap<>() {{
                            put("{value}", value);
                            put("{type}", type.getPrettyName());
                        }});

                        return;
                    }

                    words.add(value);

                    ConfigManager.getWordsConfig().save();
                }
            }
        }

        @Command("remove-whitelist")
        @Permission(value = "remove-whitelist", def = PermissionDefault.OP, description = "Access to /chatmanager filter remove-whitelist <word>")
        public void removeWhitelist(final CommandSender sender, @Suggestion("whitelisted_words") final String value) {
            final List<String> allowed_words = WordsConfig.allowed_words;

            if (!allowed_words.contains(value)) {
                Messages.filter_value_not_found.sendMessage(sender, new HashMap<>() {{
                    put("{value}", value);
                    put("{type}", FilterType.allowed_words.getPrettyName());
                }});

                return;
            }

            allowed_words.remove(value);

            ConfigManager.getWordsConfig().save();
        }

        @Command("remove-command")
        @Permission(value = "remove-command", def = PermissionDefault.OP, description = "Access to /chatmanager filter remove-command <command>")
        public void removeCommand(final CommandSender sender, @Suggestion("blacklisted_commands") final String value) {
            final List<String> commands = CommandsConfig.banned_commands;

            if (!commands.contains(value)) {
                Messages.filter_value_not_found.sendMessage(sender, new HashMap<>() {{
                    put("{value}", value);
                    put("{type}", FilterType.banned_commands.getPrettyName());
                }});

                return;
            }

            commands.remove(value);

            ConfigManager.getCommandsConfig().save();
        }

        @Command("remove-word")
        @Permission(value = "remove-word", def = PermissionDefault.OP, description = "Access to /chatmanager filter remove-word <command>")
        public void removeWord(final CommandSender sender, @Suggestion("blacklisted_words") final String value) {
            final List<String> words = WordsConfig.banned_words;

            if (!words.contains(value)) {
                Messages.filter_value_not_found.sendMessage(sender, new HashMap<>() {{
                    put("{value}", value);
                    put("{type}", FilterType.banned_words.getPrettyName());
                }});

                return;
            }

            words.remove(value);

            ConfigManager.getWordsConfig().save();
        }

        @Command("help")
        @Permission(value = "help", def = PermissionDefault.OP, description = "Access to /chatmanager filter help")
        public void help(final CommandSender sender) {
            Messages.filter_command_help.sendMessage(sender);
        }
    }
}